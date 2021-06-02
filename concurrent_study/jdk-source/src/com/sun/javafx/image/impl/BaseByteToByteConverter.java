/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.javafx.image.impl;

import com.sun.javafx.image.BytePixelAccessor;
import com.sun.javafx.image.BytePixelGetter;
import com.sun.javafx.image.BytePixelSetter;
import com.sun.javafx.image.ByteToBytePixelConverter;
import java.nio.ByteBuffer;

abstract class BaseByteToByteConverter
    implements ByteToBytePixelConverter
{
    protected final BytePixelGetter getter;
    protected final BytePixelSetter setter;
    protected final int nSrcElems;
    protected final int nDstElems;

    BaseByteToByteConverter(BytePixelGetter getter, BytePixelSetter setter) {
        this.getter = getter;
        this.setter = setter;
        this.nSrcElems = getter.getNumElements();
        this.nDstElems = setter.getNumElements();
    }

    @Override
    public final BytePixelGetter getGetter() {
        return getter;
    }

    @Override
    public final BytePixelSetter getSetter() {
        return setter;
    }

    abstract void doConvert(byte srcarr[], int srcoff, int srcscanbytes,
                            byte dstarr[], int dstoff, int dstscanbytes,
                            int w, int h);

    abstract void doConvert(ByteBuffer srcbuf, int srcoff, int srcscanbytes,
                            ByteBuffer dstbuf, int dstoff, int dstscanbytes,
                            int w, int h);

    @Override
    public final void convert(byte srcarr[], int srcoff, int srcscanbytes,
                              byte dstarr[], int dstoff, int dstscanbytes,
                              int w, int h)
    {
        if (w <= 0 || h <= 0) return;
        if (srcscanbytes == w * nSrcElems &&
            dstscanbytes == w * nDstElems)
        {
            w *= h;
            h = 1;
        }
        doConvert(srcarr, srcoff, srcscanbytes,
                  dstarr, dstoff, dstscanbytes,
                  w, h);
    }

    @Override
    public final void convert(ByteBuffer srcbuf, int srcoff, int srcscanbytes,
                              ByteBuffer dstbuf, int dstoff, int dstscanbytes,
                              int w, int h)
    {
        if (w <= 0 || h <= 0) return;
        if (srcscanbytes == w * nSrcElems &&
            dstscanbytes == w * nDstElems)
        {
            w *= h;
            h = 1;
        }
        if (srcbuf.hasArray() && dstbuf.hasArray()) {
            srcoff += srcbuf.arrayOffset();
            dstoff += dstbuf.arrayOffset();
            doConvert(srcbuf.array(), srcoff, srcscanbytes,
                      dstbuf.array(), dstoff, dstscanbytes,
                      w, h);
        } else {
            doConvert(srcbuf, srcoff, srcscanbytes,
                      dstbuf, dstoff, dstscanbytes,
                      w, h);
        }
    }

    @Override
    public final void convert(ByteBuffer srcbuf,   int srcoff, int srcscanbytes,
                              byte       dstarr[], int dstoff, int dstscanbytes,
                              int w, int h)
    {
        if (w <= 0 || h <= 0) return;
        if (srcscanbytes == w * nSrcElems &&
            dstscanbytes == w * nDstElems)
        {
            w *= h;
            h = 1;
        }
        if (srcbuf.hasArray()) {
            byte srcarr[] = srcbuf.array();
            srcoff += srcbuf.arrayOffset();
            doConvert(srcarr, srcoff, srcscanbytes,
                      dstarr, dstoff, dstscanbytes,
                      w, h);
        } else {
            ByteBuffer dstbuf = ByteBuffer.wrap(dstarr);
            doConvert(srcbuf, srcoff, srcscanbytes,
                      dstbuf, dstoff, dstscanbytes,
                      w, h);
        }
    }

    @Override
    public final void convert(byte       srcarr[], int srcoff, int srcscanbytes,
                              ByteBuffer dstbuf,   int dstoff, int dstscanbytes,
                              int w, int h)
    {
        if (w <= 0 || h <= 0) return;
        if (srcscanbytes == w * nSrcElems &&
            dstscanbytes == w * nDstElems)
        {
            w *= h;
            h = 1;
        }
        if (dstbuf.hasArray()) {
            byte dstarr[] = dstbuf.array();
            dstoff += dstbuf.arrayOffset();
            doConvert(srcarr, srcoff, srcscanbytes,
                      dstarr, dstoff, dstscanbytes,
                      w, h);
        } else {
            ByteBuffer srcbuf = ByteBuffer.wrap(srcarr);
            doConvert(srcbuf, srcoff, srcscanbytes,
                      dstbuf, dstoff, dstscanbytes,
                      w, h);
        }
    }

    static ByteToBytePixelConverter create(BytePixelAccessor fmt) {
        return new ByteAnyToSameConverter(fmt);
    }

    static class ByteAnyToSameConverter extends BaseByteToByteConverter {
        ByteAnyToSameConverter(BytePixelAccessor fmt) {
            super(fmt, fmt);
        }

        @Override
        void doConvert(byte srcarr[], int srcoff, int srcscanbytes,
                       byte dstarr[], int dstoff, int dstscanbytes,
                       int w, int h)
        {
            while (--h >= 0) {
                System.arraycopy(srcarr, srcoff, dstarr, dstoff, w * nSrcElems);
                srcoff += srcscanbytes;
                dstoff += dstscanbytes;
            }
        }

        @Override
        void doConvert(ByteBuffer srcbuf, int srcoff, int srcscanbytes,
                       ByteBuffer dstbuf, int dstoff, int dstscanbytes,
                       int w, int h)
        {
            int srclimit = srcbuf.limit();
            int origsrcpos = srcbuf.position();
            int origdstpos = dstbuf.position();
            try {
                while (--h >= 0) {
                    int newlimit = srcoff + w * nSrcElems;
                    if (newlimit > srclimit) {
                        throw new IndexOutOfBoundsException("" + srclimit);
                    }
                    srcbuf.limit(newlimit);
                    srcbuf.position(srcoff);
                    dstbuf.position(dstoff);
                    dstbuf.put(srcbuf);
                    srcoff += srcscanbytes;
                    dstoff += dstscanbytes;
                }
            } finally {
                srcbuf.limit(srclimit);
                srcbuf.position(origsrcpos);
                dstbuf.position(origdstpos);
            }
        }
    }

    public static ByteToBytePixelConverter
        createReorderer(BytePixelGetter getter, BytePixelSetter setter,
                        int c0, int c1, int c2, int c3)
    {
        return new FourByteReorderer(getter, setter, c0, c1, c2, c3);
    }

    static class FourByteReorderer extends BaseByteToByteConverter {
        private final int c0, c1, c2, c3;

        FourByteReorderer(BytePixelGetter getter, BytePixelSetter setter,
                          int c0, int c1, int c2, int c3)
        {
            super(getter, setter);
            this.c0 = c0;
            this.c1 = c1;
            this.c2 = c2;
            this.c3 = c3;
        }

        @Override
        void doConvert(byte srcarr[], int srcoff, int srcscanbytes,
                       byte dstarr[], int dstoff, int dstscanbytes,
                       int w, int h)
        {
            srcscanbytes -= w * 4;
            dstscanbytes -= w * 4;
            while (--h >= 0) {
                for (int x = 0; x < w; x++) {
                    // load all then store in case the buffers point to
                    // the same memory
                    byte b0 = srcarr[srcoff + c0];
                    byte b1 = srcarr[srcoff + c1];
                    byte b2 = srcarr[srcoff + c2];
                    byte b3 = srcarr[srcoff + c3];
                    dstarr[dstoff++] = b0;
                    dstarr[dstoff++] = b1;
                    dstarr[dstoff++] = b2;
                    dstarr[dstoff++] = b3;
                    srcoff += 4;
                }
                srcoff += srcscanbytes;
                dstoff += dstscanbytes;
            }
        }

        @Override
        void doConvert(ByteBuffer srcbuf, int srcoff, int srcscanbytes,
                       ByteBuffer dstbuf, int dstoff, int dstscanbytes,
                       int w, int h)
        {
            srcscanbytes -= w * 4;
            dstscanbytes -= w * 4;
            while (--h >= 0) {
                for (int x = 0; x < w; x++) {
                    // load all then store in case the buffers point to
                    // the same memory
                    byte b0 = srcbuf.get(srcoff + c0);
                    byte b1 = srcbuf.get(srcoff + c1);
                    byte b2 = srcbuf.get(srcoff + c2);
                    byte b3 = srcbuf.get(srcoff + c3);
                    dstbuf.put(dstoff    , b0);
                    dstbuf.put(dstoff + 1, b1);
                    dstbuf.put(dstoff + 2, b2);
                    dstbuf.put(dstoff + 3, b3);
                    srcoff += 4;
                    dstoff += 4;
                }
                srcoff += srcscanbytes;
                dstoff += dstscanbytes;
            }
        }
    }
}
