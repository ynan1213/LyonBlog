/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.ref;

import java.security.PrivilegedAction;
import java.security.AccessController;
import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;
import sun.misc.VM;

final class Finalizer extends FinalReference<Object> { /* Package-private; must be in
                                                          same package as the Reference
                                                          class */

    private static ReferenceQueue<Object> queue = new ReferenceQueue<>();
    private static Finalizer unfinalized = null;
    private static final Object lock = new Object();

    private Finalizer next = null, prev = null;

    private boolean hasBeenFinalized() {
        return (next == this);
    }

    // unfinalized是类属性，所以不同的Finalizer调用add方法，都会追加到unfinalized后面形成一个双向链表
    //
    // 注意：unfinalized 是static修饰，会存在方法区中，根据gc roots定义，unfinalized也属于一个gc roots，
    // 所以执行add方法后，Finalizer就不会被回收了。除非主动断开。
    private void add() {
        synchronized (lock) {
            if (unfinalized != null) {
                this.next = unfinalized;
                unfinalized.prev = this;
            }
            unfinalized = this;
        }
    }

    private void remove() {
        synchronized (lock) {
            if (unfinalized == this) {
                if (this.next != null) {
                    unfinalized = this.next;
                } else {
                    unfinalized = this.prev;
                }
            }
            if (this.next != null) {
                this.next.prev = this.prev;
            }
            if (this.prev != null) {
                this.prev.next = this.next;
            }
            this.next = this;   /* Indicates that this has been finalized */
            this.prev = this;
        }
    }

    private Finalizer(Object finalizee) {
        super(finalizee, queue);
        add();
    }

    /* Invoked by VM */
    static void register(Object finalizee) {
        new Finalizer(finalizee);
    }

    private void runFinalizer(JavaLangAccess jla) {
        synchronized (this) {
            if (hasBeenFinalized()) return;
            remove();
        }
        try {
            Object finalizee = this.get();
            if (finalizee != null && !(finalizee instanceof java.lang.Enum)) {
                jla.invokeFinalize(finalizee);

                /* Clear stack slot containing this variable, to decrease
                   the chances of false retention with a conservative GC */
                finalizee = null;
            }
        } catch (Throwable x) { }
        super.clear();
    }

    /* Create a privileged secondary finalizer thread in the system thread
       group for the given Runnable, and wait for it to complete.

       This method is used by both runFinalization and runFinalizersOnExit.
       The former method invokes all pending finalizers, while the latter
       invokes all uninvoked finalizers if on-exit finalization has been
       enabled.

       These two methods could have been implemented by offloading their work
       to the regular finalizer thread and waiting for that thread to finish.
       The advantage of creating a fresh thread, however, is that it insulates
       invokers of these methods from a stalled or deadlocked finalizer thread.
     */
    private static void forkSecondaryFinalizer(final Runnable proc) {
        AccessController.doPrivileged(
            new PrivilegedAction<Void>() {
                public Void run() {
                ThreadGroup tg = Thread.currentThread().getThreadGroup();
                for (ThreadGroup tgn = tg;
                     tgn != null;
                     tg = tgn, tgn = tg.getParent());
                Thread sft = new Thread(tg, proc, "Secondary finalizer");
                sft.start();
                try {
                    sft.join();
                } catch (InterruptedException x) {
                    /* Ignore */
                }
                return null;
                }});
    }

    /* Called by Runtime.runFinalization() */
    static void runFinalization() {
        if (!VM.isBooted()) {
            return;
        }

        forkSecondaryFinalizer(new Runnable() {
            private volatile boolean running;
            public void run() {
                if (running)
                    return;
                final JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
                running = true;
                for (;;) {
                    Finalizer f = (Finalizer)queue.poll();
                    if (f == null) break;
                    f.runFinalizer(jla);
                }
            }
        });
    }

    /* Invoked by java.lang.Shutdown */
    static void runAllFinalizers() {
        if (!VM.isBooted()) {
            return;
        }

        forkSecondaryFinalizer(new Runnable() {
            private volatile boolean running;
            public void run() {
                if (running)
                    return;
                final JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
                running = true;
                for (;;) {
                    Finalizer f;
                    synchronized (lock) {
                        f = unfinalized;
                        if (f == null) break;
                        unfinalized = f.next;
                    }
                    f.runFinalizer(jla);
                }}});
    }

    private static class FinalizerThread extends Thread {
        private volatile boolean running;
        FinalizerThread(ThreadGroup g) {
            super(g, "Finalizer");
        }
        public void run() {
            if (running)
                return;

            // Finalizer thread starts before System.initializeSystemClass
            // is called.  Wait until JavaLangAccess is available
            while (!VM.isBooted()) {
                // delay until VM completes initialization
                try {
                    VM.awaitBooted();
                } catch (InterruptedException x) {
                    // ignore and continue
                }
            }
            final JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
            running = true;
            for (;;) {
                try {
                    Finalizer f = (Finalizer)queue.remove();
                    f.runFinalizer(jla);
                } catch (InterruptedException x) {
                    // ignore and continue
                }
            }
        }
    }

    static {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        for (ThreadGroup tgn = tg; tgn != null; tg = tgn, tgn = tg.getParent());
        Thread finalizer = new FinalizerThread(tg);
        // 线程默认值优先级为5，这个线程的优先级为8，比默认的高
        finalizer.setPriority(Thread.MAX_PRIORITY - 2);
        finalizer.setDaemon(true);
        finalizer.start();
    }

}
