/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.net;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;

public class Acceptor<U> implements Runnable {

    private static final Log log = LogFactory.getLog(Acceptor.class);
    private static final StringManager sm = StringManager.getManager(Acceptor.class);

    private static final int INITIAL_ERROR_DELAY = 50;
    private static final int MAX_ERROR_DELAY = 1600;

    private final AbstractEndpoint<?,U> endpoint;
    private String threadName;
    /*
     * Tracked separately rather than using endpoint.isRunning() as calls to
     * endpoint.stop() and endpoint.start() in quick succession can cause the
     * acceptor to continue running when it should terminate.
     */
    private volatile boolean stopCalled = false;
    private final CountDownLatch stopLatch = new CountDownLatch(1);
    protected volatile AcceptorState state = AcceptorState.NEW;


    public Acceptor(AbstractEndpoint<?,U> endpoint) {
        this.endpoint = endpoint;
    }


    public final AcceptorState getState() {
        return state;
    }


    final void setThreadName(final String threadName) {
        this.threadName = threadName;
    }


    final String getThreadName() {
        return threadName;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void run() {

        int errorDelay = 0;
        long pauseStart = 0;

        try {
            // Loop until we receive a shutdown command
            while (!stopCalled) {

                // Loop if endpoint is paused.
                // There are two likely scenarios here. 这里有两种可能的情况
                // The first scenario is that Tomcat is shutting down. In this
                // case - and particularly for the unit tests - we want to exit
                // this loop as quickly as possible. The second scenario is a
                // genuine pause of the connector. In this case we want to avoid
                // excessive CPU usage.
                // Therefore, we start with a tight loop but if there isn't a
                // rapid transition to stop then sleeps are introduced.
                // < 1ms       - tight loop
                // 1ms to 10ms - 1ms sleep
                // > 10ms      - 10ms sleep
                // 什么情况会进入该while循环？ tomcat停止但是Acceptor仍在运行？
                while (endpoint.isPaused() && !stopCalled) {
                    if (state != AcceptorState.PAUSED) {
                        pauseStart = System.nanoTime();
                        // Entered pause state
                        state = AcceptorState.PAUSED;
                    }
                    if ((System.nanoTime() - pauseStart) > 1_000_000) {
                        // Paused for more than 1ms
                        try {
                            if ((System.nanoTime() - pauseStart) > 10_000_000) {
                                Thread.sleep(10);
                            } else {
                                Thread.sleep(1);
                            }
                        } catch (InterruptedException e) {
                            // Ignore
                        }
                    }
                }

                if (stopCalled) {
                    break;
                }
                state = AcceptorState.RUNNING;

                try {
                    //if we have reached max connections, wait
                    // 连接数 +1，如果达到了最大值，会阻塞等待，最大连接数的配置详情见文档，默认 8 * 1024
                    // 这里连接数 +1，什么时候 -1呢？
                    // 1.获取socket抛出了异常，连接数 -1
                    // 2.成功获取到socket，然后添加到poller的队列中，什么时候 -1呢？ 最后发现是 socketWrapper.close()；
                    endpoint.countUpOrAwaitConnection();

                    // Endpoint might have been paused while waiting for latch
                    // If that is the case, don't accept new connections
                    if (endpoint.isPaused()) {
                        continue;
                    }

                    U socket = null;
                    try {
                        // Accept the next incoming connection from the server
                        // socket
                        socket = endpoint.serverSocketAccept();
                    } catch (Exception ioe) {
                        // We didn't get a socket
                        // 获取socket抛出了异常，连接数 -1
                        endpoint.countDownConnection();

                        if (endpoint.isRunning()) {
                            // Introduce delay if necessary
                            // 如果 endpoint 在运行，不会立即抛出异常
                            // 第一次sleep 0秒，第二次50ms，后面依次 *2，但是最长不会超过 1600ms
                            // 延迟什么时候解除呢？看下面
                            errorDelay = handleExceptionWithDelay(errorDelay);
                            // re-throw
                            // 是不是抛出了异常Acceptor就会跳出run循环呢？ 其实不会，注意最下面有个catch
                            throw ioe;
                        } else {
                            break;
                        }
                    }
                    // Successful accept, reset the error delay
                    // 只要有一次成功，就解除延迟
                    errorDelay = 0;

                    // Configure the socket
                    if (!stopCalled && !endpoint.isPaused()) {
                        // setSocketOptions() will hand the socket off to an appropriate processor if successful
                        // 如果成功，setSocketOptions（）将把套接字交给适当的处理器
                        if (!endpoint.setSocketOptions(socket)) {
                            endpoint.closeSocket(socket);
                        }
                    } else {
                        endpoint.destroySocket(socket);
                    }
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    String msg = sm.getString("endpoint.accept.fail");
                    // APR specific.
                    // Could push this down but not sure it is worth the trouble.
                    if (t instanceof org.apache.tomcat.jni.Error) {
                        org.apache.tomcat.jni.Error e = (org.apache.tomcat.jni.Error) t;
                        if (e.getError() == 233) {
                            // Not an error on HP-UX so log as a warning
                            // so it can be filtered out on that platform
                            // See bug 50273
                            log.warn(msg, t);
                        } else {
                            log.error(msg, t);
                        }
                    } else {
                            log.error(msg, t);
                    }
                }
            }
        } finally {
            // 当有其它线程调用Acceptor的stop方法时，会在一定时间内等待Acceptor线程跳出run循环
            stopLatch.countDown();
        }
        state = AcceptorState.ENDED;
    }


    /**
     * Signals the Acceptor to stop, waiting at most 10 seconds for the stop to
     * complete before returning. If the stop does not complete in that time a
     * warning will be logged.
     *
     * @deprecated This method will be removed in Tomcat 10.1.x onwards.
     *             Use {@link #stop(int)} instead.
     */
    @Deprecated
    public void stop() {
        stop(10);
    }


    /**
     * Signals the Acceptor to stop, optionally waiting for that stop process
     * to complete before returning. If a wait is requested and the stop does
     * not complete in that time a warning will be logged.
     *
     * @param waitSeconds The time to wait in seconds. Use a value less than
     *                    zero for no wait.
     */
    public void stop(int waitSeconds) {
        // stopCalled置为true，Acceptor线程就会跳出run循环，但是不会立即跳出，因为有可能阻塞在serverSock.accept()中
        // 当Acceptor线程跳出run循环后会调用stopLatch.countDown()，此时当前线程执行stopLatch.await才会立即返回，
        // 否则会等待 waitSeconds
        stopCalled = true;
        if (waitSeconds > 0) {
            try {
                if (!stopLatch.await(waitSeconds, TimeUnit.SECONDS)) {
                   log.warn(sm.getString("acceptor.stop.fail", getThreadName()));
                }
            } catch (InterruptedException e) {
                log.warn(sm.getString("acceptor.stop.interrupted", getThreadName()), e);
            }
        }
    }


    /**
     * Handles exceptions where a delay is required to prevent a Thread from
     * entering a tight loop which will consume CPU and may also trigger large
     * amounts of logging. For example, this can happen if the ulimit for open
     * files is reached.
     *
     * @param currentErrorDelay The current delay being applied on failure
     * @return  The delay to apply on the next failure
     */
    protected int handleExceptionWithDelay(int currentErrorDelay) {
        // Don't delay on first exception
        if (currentErrorDelay > 0) {
            try {
                Thread.sleep(currentErrorDelay);
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        // On subsequent exceptions, start the delay at 50ms, doubling the delay
        // on every subsequent exception until the delay reaches 1.6 seconds.
        if (currentErrorDelay == 0) {
            return INITIAL_ERROR_DELAY;
        } else if (currentErrorDelay < MAX_ERROR_DELAY) {
            return currentErrorDelay * 2;
        } else {
            return MAX_ERROR_DELAY;
        }
    }


    public enum AcceptorState {
        NEW, RUNNING, PAUSED, ENDED
    }
}
