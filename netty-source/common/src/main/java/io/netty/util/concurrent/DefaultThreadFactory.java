/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.netty.util.concurrent;

import io.netty.util.internal.StringUtil;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link ThreadFactory} implementation with a simple naming rule.
 */

/**
 * 实现了java提供的ThreadFactory接口
 */
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolId = new AtomicInteger();

    private final AtomicInteger nextId = new AtomicInteger();
    private final String prefix;//线程前缀，配合nextId在每次创建线程时给线程指定唯一的线程name
    private final boolean daemon;
    private final int priority;// 线程的优先级
    protected final ThreadGroup threadGroup;

    public DefaultThreadFactory(Class<?> poolType) {
        // 线程的优先级, 使用的是jdk底层的优先级, 1 - 5 - 10
        this(poolType, false, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String poolName) {
        this(poolName, false, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(Class<?> poolType, boolean daemon) {
        this(poolType, daemon, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String poolName, boolean daemon) {
        this(poolName, daemon, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(Class<?> poolType, int priority) {
        this(poolType, false, priority);
    }

    public DefaultThreadFactory(String poolName, int priority) {
        this(poolName, false, priority);
    }

    public DefaultThreadFactory(Class<?> poolType, boolean daemon, int priority) {
        //线程  非守护线程  优先级是5
        this(toPoolName(poolType), daemon, priority);
    }

    public static String toPoolName(Class<?> poolType) {
        if (poolType == null) {
            throw new NullPointerException("poolType");
        }

        String poolName = StringUtil.simpleClassName(poolType);
        switch (poolName.length()) {
            case 0:
                return "unknown";
            case 1:
                return poolName.toLowerCase(Locale.US);
            default:
                if (Character.isUpperCase(poolName.charAt(0)) && Character.isLowerCase(poolName.charAt(1))) {
                    return Character.toLowerCase(poolName.charAt(0)) + poolName.substring(1);
                } else {
                    return poolName;
                }
        }
    }

    public DefaultThreadFactory(String poolName, boolean daemon, int priority, ThreadGroup threadGroup) {
        if (poolName == null) {
            throw new NullPointerException("poolName");
        }
        if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException(
                    "priority: " + priority + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
        }
        // 设置线程的名字
        prefix = poolName + '-' + poolId.incrementAndGet() + '-';
        this.daemon = daemon;
        this.priority = priority;
        this.threadGroup = threadGroup;
    }


    public DefaultThreadFactory(String poolName, boolean daemon, int priority) {
        this(poolName, daemon, priority,
                //线程组的概念
                System.getSecurityManager() == null ? Thread.currentThread().getThreadGroup() : System.getSecurityManager().getThreadGroup());
    }

    @Override
    public Thread newThread(Runnable r) {
        // DefaultRunnableDecorator 是DefaultThreadFactory的内部类
        // newThread()的重载方法在下面
        Thread t = newThread(new DefaultRunnableDecorator(r), prefix + nextId.incrementAndGet());
        try {
            if (t.isDaemon() != daemon) {
                t.setDaemon(daemon);
            }

            if (t.getPriority() != priority) {
                t.setPriority(priority);
            }
        } catch (Exception ignored) {
            // Doesn't matter even if failed to set.
        }
        return t;
    }

    // Netty底层使用的Thread,是包装过的Thread
    protected Thread newThread(Runnable r, String name) {
        return new FastThreadLocalThread(threadGroup, r, name);
    }


    private static final class DefaultRunnableDecorator implements Runnable {

        private final Runnable r;

        DefaultRunnableDecorator(Runnable r) {
            this.r = r;
        }

        @Override
        public void run() {
            try {
                r.run();
            } finally {
                //封装Runnable任务的主要目的是下面这句
                // todo 具体作用有待研究
                FastThreadLocal.removeAll();
            }
        }
    }
}
