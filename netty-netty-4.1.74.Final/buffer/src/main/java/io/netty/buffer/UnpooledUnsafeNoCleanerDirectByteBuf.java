/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

import java.nio.ByteBuffer;

/**
 * 看名字就知道是没有Cleaner，那么是如何释放内存的呢？
 * 其实创建内存内部是反射调用 DirectByteBuffer 的私有构造DirectByteBuffer(long addr, int cap) ，这个构造内部不会创建cleaner
 * 只会记住其地址，释放内存也是不通过cleaner。
 *
 * 要比他的父类都要简洁多了，直接申请和释放内存，不需要清除器帮助，性能能提高不少。不过如果你忘记是释放内存的话，那就很尴尬了，不会有清除器为你释放内存了。
 */
class UnpooledUnsafeNoCleanerDirectByteBuf extends UnpooledUnsafeDirectByteBuf {

    UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        super(alloc, initialCapacity, maxCapacity);
    }

    @Override
    protected ByteBuffer allocateDirect(int initialCapacity) {
        return PlatformDependent.allocateDirectNoCleaner(initialCapacity);
    }

    ByteBuffer reallocateDirect(ByteBuffer oldBuffer, int initialCapacity) {
        return PlatformDependent.reallocateDirectNoCleaner(oldBuffer, initialCapacity);
    }

    @Override
    protected void freeDirect(ByteBuffer buffer) {
        PlatformDependent.freeDirectNoCleaner(buffer);
    }

    @Override
    public ByteBuf capacity(int newCapacity) {
        checkNewCapacity(newCapacity);

        int oldCapacity = capacity();
        if (newCapacity == oldCapacity) {
            return this;
        }

        trimIndicesToCapacity(newCapacity);
        setByteBuffer(reallocateDirect(buffer, newCapacity), false);
        return this;
    }
}
