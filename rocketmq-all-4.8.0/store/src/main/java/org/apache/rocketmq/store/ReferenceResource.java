/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.store;

import java.util.concurrent.atomic.AtomicLong;

public abstract class ReferenceResource {
    protected final AtomicLong refCount = new AtomicLong(1);
    protected volatile boolean available = true;
    protected volatile boolean cleanupOver = false;
    private volatile long firstShutdownTimestamp = 0;

    public synchronized boolean hold() {
        if (this.isAvailable()) {
            if (this.refCount.getAndIncrement() > 0) {
                return true;
            } else {
                this.refCount.getAndDecrement();
            }
        }
        return false;
    }

    public boolean isAvailable() {
        return this.available;
    }

    // 关闭 MappedFile
    public void shutdown(final long intervalForcibly) {
        // 初次调用时 available 为true
        if (this.available) {
            this.available = false;
            // 设置初次关闭的时间戳
            this.firstShutdownTimestamp = System.currentTimeMillis();
            // 释放资源，release 只有在引用次数小于 1 的情况下才会释放资源
            this.release();
        } else if (this.getRefCount() > 0) {
            // 如果引用次数大于0，如果已经超过了其最大拒绝存活期，每执行一次，将引用次数减少1000
            if ((System.currentTimeMillis() - this.firstShutdownTimestamp) >= intervalForcibly) {
                this.refCount.set(-1000 - this.getRefCount());
                // 调用释放资源，引用数小于0才会真正释放
                this.release();
            }
        }
    }

    public void release() {
        long value = this.refCount.decrementAndGet();
        // 引用次数只有小于0才会释放
        if (value > 0)
            return;

        synchronized (this) {
            this.cleanupOver = this.cleanup(value);
        }
    }

    public long getRefCount() {
        return this.refCount.get();
    }

    public abstract boolean cleanup(final long currentRef);

    public boolean isCleanupOver() {
        return this.refCount.get() <= 0 && this.cleanupOver;
    }
}
