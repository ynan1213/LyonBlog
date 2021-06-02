/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.webkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class EventLoop {

    private static final Logger logger =
            Logger.getLogger(EventLoop.class.getName());

    private static EventLoop instance;


    public static void setEventLoop(EventLoop eventLoop) {
        instance = eventLoop;
    }

    private static void fwkCycle() {
        logger.log(Level.FINE, "Executing event loop cycle");
        instance.cycle();
    }

    protected abstract void cycle();
}
