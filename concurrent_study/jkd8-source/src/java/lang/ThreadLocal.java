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

package java.lang;
import java.lang.ref.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * This class provides thread-local variables.  These variables differ from
 * their normal counterparts in that each thread that accesses one (via its
 * {@code get} or {@code set} method) has its own, independently initialized
 * copy of the variable.  {@code ThreadLocal} instances are typically private
 * static fields in classes that wish to associate state with a thread (e.g.,
 * a user ID or Transaction ID).
 *
 * <p>For example, the class below generates unique identifiers local to each
 * thread.
 * A thread's id is assigned the first time it invokes {@code ThreadId.get()}
 * and remains unchanged on subsequent calls.
 * <pre>
 * import java.util.concurrent.atomic.AtomicInteger;
 *
 * public class ThreadId {
 *     // Atomic integer containing the next thread ID to be assigned
 *     private static final AtomicInteger nextId = new AtomicInteger(0);
 *
 *     // Thread local variable containing each thread's ID
 *     private static final ThreadLocal&lt;Integer&gt; threadId =
 *         new ThreadLocal&lt;Integer&gt;() {
 *             &#64;Override protected Integer initialValue() {
 *                 return nextId.getAndIncrement();
 *         }
 *     };
 *
 *     // Returns the current thread's unique ID, assigning it if necessary
 *     public static int get() {
 *         return threadId.get();
 *     }
 * }
 * </pre>
 * <p>Each thread holds an implicit reference to its copy of a thread-local
 * variable as long as the thread is alive and the {@code ThreadLocal}
 * instance is accessible; after a thread goes away, all of its copies of
 * thread-local instances are subject to garbage collection (unless other
 * references to these copies exist).
 *
 * @author  Josh Bloch and Doug Lea
 * @since   1.2
 */
public class ThreadLocal<T> {
    /**
     * ThreadLocals rely on per-thread linear-probe hash maps attached
     * to each thread (Thread.threadLocals and
     * inheritableThreadLocals).  The ThreadLocal objects act as keys,
     * searched via threadLocalHashCode.  This is a custom hash code
     * (useful only within ThreadLocalMaps) that eliminates collisions
     * in the common case where consecutively constructed ThreadLocals
     * are used by the same threads, while remaining well-behaved in
     * less common cases.
     *
     * threadLocalHashCode 是 TheadLocal的成员变量
     * 每new一个TheadLocal，threadLocalHashCode都会加一次HASH_INCREMENT = 0x61c88647
     */
    private final int threadLocalHashCode = nextHashCode();

    /**
     * The next hash code to be given out. Updated atomically. Starts at
     * zero.
     */
    private static AtomicInteger nextHashCode = new AtomicInteger();

    /**
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * Returns the next hash code.
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    /**
     * Returns the current thread's "initial value" for this
     * thread-local variable.  This method will be invoked the first
     * time a thread accesses the variable with the {@link #get}
     * method, unless the thread previously invoked the {@link #set}
     * method, in which case the {@code initialValue} method will not
     * be invoked for the thread.  Normally, this method is invoked at
     * most once per thread, but it may be invoked again in case of
     * subsequent invocations of {@link #remove} followed by {@link #get}.
     *
     * <p>This implementation simply returns {@code null}; if the
     * programmer desires thread-local variables to have an initial
     * value other than {@code null}, {@code ThreadLocal} must be
     * subclassed, and this method overridden.  Typically, an
     * anonymous inner class will be used.
     *
     * @return the initial value for this thread-local
     */
    protected T initialValue() {
        return null;
    }

    /**
     * Creates a thread local variable. The initial value of the variable is
     * determined by invoking the {@code get} method on the {@code Supplier}.
     *
     * @param <S> the type of the thread local's value
     * @param supplier the supplier to be used to determine the initial value
     * @return a new thread local variable
     * @throws NullPointerException if the specified supplier is null
     * @since 1.8
     */
    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<>(supplier);
    }

    /**
     * Creates a thread local variable.
     * @see #withInitial(java.util.function.Supplier)
     */
    public ThreadLocal() {
    }

    /**
     * Returns the value in the current thread's copy of this
     * thread-local variable.  If the variable has no value for the
     * current thread, it is first initialized to the value returned
     * by an invocation of the {@link #initialValue} method.
     *
     * @return the current thread's value of this thread-local
     */
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }

    /**
     * Variant of set() to establish initialValue. Used instead
     * of set() in case user has overridden the set() method.
     *
     * @return the initial value
     */
    private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }

    /**
     * Sets the current thread's copy of this thread-local variable
     * to the specified value.  Most subclasses will have no need to
     * override this method, relying solely on the {@link #initialValue}
     * method to set the values of thread-locals.
     *
     * @param value the value to be stored in the current thread's copy of
     *        this thread-local.
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }

    /**
     * Removes the current thread's value for this thread-local
     * variable.  If this thread-local variable is subsequently
     * {@linkplain #get read} by the current thread, its value will be
     * reinitialized by invoking its {@link #initialValue} method,
     * unless its value is {@linkplain #set set} by the current thread
     * in the interim.  This may result in multiple invocations of the
     * {@code initialValue} method in the current thread.
     *
     * @since 1.5
     */
     public void remove() {
         ThreadLocalMap m = getMap(Thread.currentThread());
         if (m != null)
             m.remove(this);
     }

    /**
     * Get the map associated with a ThreadLocal. Overridden in
     * InheritableThreadLocal.
     *
     * @param  t the current thread
     * @return the map
     */
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }

    /**
     * Create the map associated with a ThreadLocal. Overridden in
     * InheritableThreadLocal.
     *
     * @param t the current thread
     * @param firstValue value for the initial entry of the map
     */
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    /**
     * Factory method to create map of inherited thread locals.
     * Designed to be called only from Thread constructor.
     *
     * @param  parentMap the map associated with parent thread
     * @return a map containing the parent's inheritable bindings
     */
    static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
        return new ThreadLocalMap(parentMap);
    }

    /**
     * Method childValue is visibly defined in subclass
     * InheritableThreadLocal, but is internally defined here for the
     * sake of providing createInheritedMap factory method without
     * needing to subclass the map class in InheritableThreadLocal.
     * This technique is preferable to the alternative of embedding
     * instanceof tests in methods.
     */
    T childValue(T parentValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * An extension of ThreadLocal that obtains its initial value from
     * the specified {@code Supplier}.
     */
    static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {

        private final Supplier<? extends T> supplier;

        SuppliedThreadLocal(Supplier<? extends T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }

        @Override
        protected T initialValue() {
            return supplier.get();
        }
    }

    /**
     * ThreadLocalMap is a customized hash map suitable only for
     * maintaining thread local values. No operations are exported
     * outside of the ThreadLocal class. The class is package private to
     * allow declaration of fields in class Thread.  To help deal with
     * very large and long-lived usages, the hash table entries use
     * WeakReferences for keys. However, since reference queues are not
     * used, stale entries are guaranteed to be removed only when
     * the table starts running out of space.
     *
     * 包访问权限
     */
    static class ThreadLocalMap {

        /**
         * The entries in this hash map extend WeakReference, using
         * its main ref field as the key (which is always a
         * ThreadLocal object).  Note that null keys (i.e. entry.get()
         * == null) mean that the key is no longer referenced, so the
         * entry can be expunged from table.  Such entries are referred to
         * as "stale entries" in the code that follows.
         */
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }

        /**
         * The initial capacity -- MUST be a power of two.
         */
        private static final int INITIAL_CAPACITY = 16;

        /**
         * The table, resized as necessary.
         * table.length MUST always be a power of two.
         *
         * ThreadLocalMap维护了一个Entry表或者说Entry数组，并且要求表的大小必须为2的幂
         */
        private Entry[] table;

        /**
         * The number of entries in the table.
         */
        private int size = 0;

        /**
         * The next size value at which to resize.
         */
        private int threshold; // Default to 0

        /**
         * Set the resize threshold to maintain at worst a 2/3 load factor.
         * 这里其实可以转成位运算
         */
        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }

        /**
         * Increment i modulo len.
         */
        private static int nextIndex(int i, int len) {
            return ((i + 1 < len) ? i + 1 : 0);
        }

        /**
         * Decrement i modulo len.
         */
        private static int prevIndex(int i, int len) {
            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        /**
         * Construct a new map initially containing (firstKey, firstValue).
         * ThreadLocalMaps are constructed lazily, so we only create
         * one when we have at least one entry to put in it.
         */
        ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            // 初始大小16
            table = new Entry[INITIAL_CAPACITY];
            // 用firstKey的threadLocalHashCode与初始大小 16-1 取模得到哈希值，这里和hashMap一样，将取模改成了位运算
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            // 初始化该节点
            table[i] = new Entry(firstKey, firstValue);
            // 设置节点表大小为1
            size = 1;
            // 设定扩容阈值
            setThreshold(INITIAL_CAPACITY);
        }

        /**
         * Construct a new map including all Inheritable ThreadLocals
         * from given parent map. Called only by createInheritedMap.
         *
         * @param parentMap the map associated with parent thread.
         */
        private ThreadLocalMap(ThreadLocalMap parentMap) {
            Entry[] parentTable = parentMap.table;
            int len = parentTable.length;
            setThreshold(len);
            table = new Entry[len];

            for (int j = 0; j < len; j++) {
                Entry e = parentTable[j];
                if (e != null) {
                    @SuppressWarnings("unchecked")
                    ThreadLocal<Object> key = (ThreadLocal<Object>) e.get();
                    if (key != null) {
                        Object value = key.childValue(e.value);
                        Entry c = new Entry(key, value);
                        int h = key.threadLocalHashCode & (len - 1);
                        while (table[h] != null)
                            h = nextIndex(h, len);
                        table[h] = c;
                        size++;
                    }
                }
            }
        }

        /**
         * Get the entry associated with key.  This method
         * itself handles only the fast path: a direct hit of existing
         * key. It otherwise relays to getEntryAfterMiss.  This is
         * designed to maximize performance for direct hits, in part
         * by making this method readily inlinable.
         *
         * @param  key the thread local object
         * @return the entry associated with key, or null if no such
         *
         * 该方法被threadLocal.get实例方法调用，所以进入到该方法的key肯定不为null
         * <p>
         * 总结：
         *  1. 计算出key对象的下标，如果当前下标命中，直接返回；
         *  2. 如果未命中，向后遍历；
         *  3. 如果向后遍历过程命中，返回；
         *  4. 如果向后遍历过程找到无效的Entry，会将无效Entry后面的都清理一遍；
         *  5. 如果遍历到一个空slot，则返回null
         * </p>
         */
        private Entry getEntry(ThreadLocal<?> key) {
            // hash函数计算hash值
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry e = table[i];

            if (e != null && e.get() == key) {
                // 命中返回，这里和hashMap有一点不一样：直接使用 == ，hashMap里是 == || equals
                return e;
            }
            else
                // 到else这里的情况：
                //   1、entry为空；getEntryAfterMiss的逻辑是直接返回null
                //   2、entry不为空，但是key不相等，说明有了hash冲突；getEntryAfterMiss的逻辑是往后找，找到就返回，找不到的话碰到一个空slot就返回null
                return getEntryAfterMiss(key, i, e);
        }

        /**
         * Version of getEntry method for use when key is not found in
         * its direct hash slot.
         *
         * @param  key the thread local object
         * @param  i the table index for key's hash code
         * @param  e the entry at table[i]
         * @return the entry associated with key, or null if no such
         *
         * 调用getEntry未直接命中的时候调用此方法，并且该方法唯一被调用的地方就在getEntry方法中
         *   1、entry为空；
         *   2、entry不为空，但是key不相等，说明有了hash冲突，往后找
         */
        private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;

            while (e != null) {
                // 弱引用
                ThreadLocal<?> k = e.get();

                // 找到目标
                if (k == key)
                    return e;

                if (k == null)
                    // entry!=null但是key==null说明该entry对应的ThreadLocal已经被回收，entry无效，调用expungeStaleEntry清理
                    // 关键点在里面，除了会将当前entry和value置为null外，还会进行一次rehash
                    // expunge：删除
                    // stale：失效的
                    expungeStaleEntry(i);
                else
                    // 找下一个
                    i = nextIndex(i, len);
                e = tab[i];
            }

            // 来到这里有两种情况：e一进来就是空 或者 在while循环内找到第一个空slot
            // 一开始有个疑问，如果当前slot为null，但是后面的slot也许和当前key相等呢？
            //   其实是不会出现这种情况的，因为就在 expungeStaleEntry 方法里
            return null;
        }

        /**
         * Set the value associated with key.
         *
         * @param key the thread local object
         * @param value the value to be set
         */
        private void set(ThreadLocal<?> key, Object value) {

            // We don't use a fast path as with get() because it is at
            // least as common to use set() to create new entries as
            // it is to replace existing ones, in which case, a fast
            // path would fail more often than not.

            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len-1);

            // 从下标i往后遍历，碰到的entry分三种情况：
            //    ①：entry的key等于需要存入值的key，覆盖；
            //    ②：entry的key为null，替换，重点分析；
            //    ③：entry为空，创建新的entry；
            for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();

                // ①：找到了相同ThreadLocal对象，覆盖，并返回，这里key不可能为null
                if (k == key) {
                    e.value = value;
                    return;
                }

                // ②：entry的key为null，说明弱引用被回收了，替换之，并删除其它无效的entry
                if (k == null) {
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }

            // 走到这里说明碰到了下标i的slot为null
            // ③：创建entry
            tab[i] = new Entry(key, value);
            int sz = ++size;

            /**
             * ①和②是替换，③是新增，只有③才会走到这里，新增完要先进行cleanSomeSlots清除失效的entry
             * 如果没有清除任何entry，并且当前使用量达到了负载因子所定义(长度的2/3)，那么进行rehash()
             */
            if (!cleanSomeSlots(i, sz) && sz >= threshold)
                rehash();
        }

        /**
         * Remove the entry for key.
         */
        private void remove(ThreadLocal<?> key) {
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len-1);
            for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
                if (e.get() == key) {
                    // 弱引用清空
                    e.clear();
                    // 当前i为null，则清理
                    expungeStaleEntry(i);
                    return;
                }
            }
        }

        /**
         * Replace a stale entry encountered during a set operation
         * with an entry for the specified key.  The value passed in
         * the value parameter is stored in the entry, whether or not
         * an entry already exists for the specified key.
         *
         * As a side effect, this method expunges all stale entries in the
         * "run" containing the stale entry.  (A run is a sequence of entries
         * between two null slots.)
         *
         * 能进入该方法，说明下标 staleSlot 的 entry 的key为null
         * 但是该方法并不是立即替换，而是往后查找看有没有匹配的key，但是最后该staleSlot下标都会被填充该key-value
         *
         * 总结：
         *  1. 从staleSlot往前遍历，找到最后一个无效的entry，记录下下标 slotToExpunge，有可能不存在，slotToExpunge 就等于 staleSlot
         *  2. 从staleSlot往后遍历，如果找到当前key相等的entry，先覆盖，然后将entry和staleSlot下标的值兑换，清理后返回；
         *  3. 如果经过slotToExpunge还是等于staleSlot就说明staleSlot前面没有无效entry，在2的过程中如果找到无效entry，则会将下标赋给slotToExpunge，否则，slotToExpunge仍等于staleSlot
         *  4. 如果2中没有找到相同的key，则会走完for循环，创建个Entry插入在后面的null槽
         *  5. 然后根据slotToExpunge是否等于staleSlot就可以判断出前面过程有没有碰到无效entry，有的话就清理cleanSomeSlots(expungeStaleEntry(slotToExpunge), len)
         *
         */
        private void replaceStaleEntry(ThreadLocal<?> key, Object value, int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            Entry e;

            int slotToExpunge = staleSlot;

            // 从当前的staleSlot位置向前遍历，直到遍历到第一个空slot就停止，此时的slotToExpunge为空slot后第一个失效entry的下标
            for (int i = prevIndex(staleSlot, len); (e = tab[i]) != null; i = prevIndex(i, len)){
                // 此时的e.get()返回的是key
                if (e.get() == null)
                    slotToExpunge = i;
            }

            // 从当前的staleSlot位置向后遍历（不包含staleSlot），找到了k==key的slot则进行替换，未找到的情况下到entry=null停止
            // 往后遍历的过程中，只会出现 key==null 和 key != null 的情况，这里只找k == key的值然后和staleSlot的slot对换
            for (int i = nextIndex(staleSlot, len); (e = tab[i]) != null; i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                // 找到了key，将其与无效的slot交换
                if (k == key) {
                    // 覆盖
                    e.value = value;
                    // 交换值，之后staleSlot是有效的值，i是无效的值
                    tab[i] = tab[staleSlot];
                    tab[staleSlot] = e;

                    // slotToExpunge 代表的是需要清理的起点，如果向前查找没有找到无效entry，则说明staleSlot前面没有需要清理的entry
                    // 又因为i和staleSlot位置的元素进行了交换，更新slotToExpunge为当前值i，因为此时i的所在的entry的key为null
                    if (slotToExpunge == staleSlot)
                        slotToExpunge = i;

                    // expungeStaleEntry：从slotToExpunge 位置开始清除无效entry，并返回往后遍历碰到的第一个null slot的下标，也就是说返回值前面的段没有无效entry
                    cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                    return;
                }

                // If we didn't find stale entry on backward scan, the
                // first stale entry seen while scanning for key is the
                // first still present in the run.
                // slotToExpunge == staleSlot 说明前面没有找到无效entry，这里置为后面的第一个下标，也就是说，slotToExpunge 至始至终指向这个区间的第一件无效entry
                if (k == null && slotToExpunge == staleSlot)
                    slotToExpunge = i;
            }

            // staleSlot 后面没有找到k == key的entry，则替换 staleSlot 下标的 slot
            tab[staleSlot].value = null;
            tab[staleSlot] = new Entry(key, value);

            // If there are any other stale entries in run, expunge them
            // 不相等说明有key=null的entry，因为slotToExpunge初始化赋值等于staleSlot
            if (slotToExpunge != staleSlot)
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
        }

        /**
         * Expunge a stale entry by rehashing any possibly colliding entries
         * lying between staleSlot and the next null slot.  This also expunges
         * any other stale entries encountered before the trailing null.  See
         * Knuth, Section 6.4
         *
         * @param staleSlot index of slot known to have null key
         * @return the index of the next null slot after staleSlot
         * (all between staleSlot and this slot will have been checked
         * for expunging).
         *
         *  expunge：删除
         *  Stale：  过期的、失效的
         *
         * 方法总结：
         *  1、将staleSlot下标的slot置为null，并从staleSlot往后遍历，碰到slot为null就退出，并将 下标 i 返回；
         *  2、从staleSlot往后遍历，删除entry的key为null的entry；
         *  3、从staleSlot往后遍历，如果entry的key不为null，做一次rehash，保证该entry到实际的index中间没有null。
         *
         *  经过该方法后，staleSlot下标到null下标中间没有无效的entry，并且每一个entry到实际的index中间没有null
         */
        private int expungeStaleEntry(int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;

            // expunge entry at staleSlot
            tab[staleSlot].value = null;// 将value引用置为null，此时的entry还存在引用
            tab[staleSlot] = null;// 将entry引用置为null
            size--;

            Entry e;
            int i;
            // 从staleSlot往后遍历，碰到slot为null就退出，并将 下标 i 返回
            for (i = nextIndex(staleSlot, len); (e = tab[i]) != null; i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                if (k == null) {
                    //key为null说明被清理了,直接将value置为null，继续往后遍历
                    e.value = null;
                    tab[i] = null;
                    size--;
                } else {
                    // 对于还没有被回收的情况，需要做一次rehash。这样可以保证和实际hash值h之间没有空的slot
                    // 当前下标i所在的entry计算出来的下标是h，如果i==h，说明当前entry没有发生冲突，没有必要移位，如果h!=i，说明存储之前发生了冲突
                    int h = k.threadLocalHashCode & (len - 1);
                    if (h != i) {
                        // 如果当前entry的下标h不等于i，说明存储之前发生了冲突
                        tab[i] = null;

                        // 但为什么是往后遍历呢？不是往前遍历吗？
                        // 是我看错了，h是计算出来的下标，i是当前的下标，所以是从h往后遍历，找到第一个空slot，有可能回到现在的位置
                        while (tab[h] != null)
                            h = nextIndex(h, len);
                        tab[h] = e;
                    }
                }
            }

            // 返回值 i 代表后面遍历到的第一个null slot
            return i;
        }

        /**
         * Heuristically scan some cells looking for stale entries.
         * This is invoked when either a new element is added, or
         * another stale one has been expunged. It performs a
         * logarithmic number of scans, as a balance between no
         * scanning (fast but retains garbage) and a number of scans
         * proportional to number of elements, that would find all
         * garbage but would cause some insertions to take O(n) time.
         *
         * @param i a position known NOT to hold a stale entry. The
         * scan starts at the element after i.
         *
         * @param n scan control: {@code log2(n)} cells are scanned,
         * unless a stale entry is found, in which case
         * {@code log2(table.length)-1} additional cells are scanned.
         * When called from insertions, this parameter is the number
         * of elements, but when from replaceStaleEntry, it is the
         * table length. (Note: all this could be changed to be either
         * more or less aggressive by weighting n instead of just
         * using straight log n. But this version is simple, fast, and
         * seems to work well.)
         *
         * @return true if any stale entries have been removed.
         *
         * 启发式的扫描清除，扫描次数由传入的参数n决定
         * 从i向后开始扫描（不包括i，因为索引为i的Slot肯定不为null）
         * n 控制扫描次数，正常情况下为 log2(n) ，如果找到了无效entry，会将n重置为table的长度len，进行段清除。
         *
         * map.set()调用的时候传入的是元素个数，replaceStaleEntry()调用的时候传入的是table的长度le
         *
         * 这个清理的过程只是覆盖了一段范围，并不是全部区间。
         */
        private boolean cleanSomeSlots(int i, int n) {
            boolean removed = false;
            Entry[] tab = table;
            int len = tab.length;
            do {
                i = nextIndex(i, len);
                Entry e = tab[i];
                if (e != null && e.get() == null) {
                    n = len;
                    removed = true;
                    i = expungeStaleEntry(i);
                }
            } while ( (n >>>= 1) != 0);// 对数控制循环
            return removed;
        }

        /**
         * Re-pack and/or re-size the table. First scan the entire
         * table removing stale entries. If this doesn't sufficiently
         * shrink the size of the table, double the table size.
         */
        private void rehash() {
            // 清理一次陈旧数据
            expungeStaleEntries();

            // Use lower threshold for doubling to avoid hysteresis
            // 清理完陈旧数据，如果 >= 3/4阀值，就执行扩容，避免迟滞
            if (size >= threshold - threshold / 4)
                resize();
        }

        /**
         * Double the capacity of the table.
         * 把table扩容2倍，并把老数据重新哈希散列进新table
         */
        private void resize() {
            Entry[] oldTab = table;
            int oldLen = oldTab.length;
            int newLen = oldLen * 2;
            Entry[] newTab = new Entry[newLen];
            int count = 0;

            // 遍历Entry[]数组
            for (int j = 0; j < oldLen; ++j) {
                Entry e = oldTab[j];
                if (e != null) {
                    ThreadLocal<?> k = e.get();
                    if (k == null) {
                        // 如果key=null，将value也置为null，有助于GC回收
                        e.value = null; // Help the GC
                    } else {
                        // 计算出扩容后的所对应的下标
                        int h = k.threadLocalHashCode & (newLen - 1);
                        while (newTab[h] != null)
                            // 如果这个位置已使用，线性往后查询，直到找到一个没有使用的位置,h递增
                            h = nextIndex(h, newLen);
                        //在找到的第一个空节点上塞入Entry e
                        newTab[h] = e;
                        count++;
                    }
                }
            }
            // 设置新的阈值（实际set方法用了2/3的newLen作为阈值）
            setThreshold(newLen);
            size = count;
            table = newTab;
        }

        /**
         * Expunge all stale entries in the table.
         */
        private void expungeStaleEntries() {
            Entry[] tab = table;
            int len = tab.length;
            for (int j = 0; j < len; j++) {
                Entry e = tab[j];
                if (e != null && e.get() == null)
                    expungeStaleEntry(j);
            }
        }
    }
}
