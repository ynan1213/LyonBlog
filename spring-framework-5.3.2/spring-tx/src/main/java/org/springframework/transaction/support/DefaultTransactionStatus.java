/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction.support;

import org.springframework.lang.Nullable;
import org.springframework.transaction.NestedTransactionNotSupportedException;
import org.springframework.transaction.SavepointManager;
import org.springframework.util.Assert;

/**
 * Default implementation of the {@link org.springframework.transaction.TransactionStatus}
 * interface, used by {@link AbstractPlatformTransactionManager}. Based on the concept
 * of an underlying "transaction object".
 *
 * <p>Holds all status information that {@link AbstractPlatformTransactionManager}
 * needs internally, including a generic transaction object determined by the
 * concrete transaction manager implementation.
 *
 * <p>Supports delegating savepoint-related methods to a transaction object
 * that implements the {@link SavepointManager} interface.
 *
 * <p><b>NOTE:</b> This is <i>not</i> intended for use with other PlatformTransactionManager
 * implementations, in particular not for mock transaction managers in testing environments.
 * Use the alternative {@link SimpleTransactionStatus} class or a mock for the plain
 * {@link org.springframework.transaction.TransactionStatus} interface instead.
 *
 * @author Juergen Hoeller
 * @since 19.01.2004
 * @see AbstractPlatformTransactionManager
 * @see org.springframework.transaction.SavepointManager
 * @see #getTransaction
 * @see #createSavepoint
 * @see #rollbackToSavepoint
 * @see #releaseSavepoint
 * @see SimpleTransactionStatus
 */
public class DefaultTransactionStatus extends AbstractTransactionStatus {

	/**
	 * 事务切面对于尝试提交会判断是否到了最外层事务(某个事务边界)，当退栈到方法的事务切面时，如果发现没有到事务最外层，是不会有真正的物理提交。
	 * Spring是怎么判断这所谓“最外层事务”的呢。
	 * 		1、TxStatus中有个属性叫 newTransaction 用于标记是否是新建事务(根据事务传播行为得出，比如加入已有事务则会是false)
	 * 		2、以及一个名为transaction的Object用于表示物理事务对象(由具体TxMgr子类负责给出）。
	 * Spring会根据每一层事务切面创建的TxStatus内部是否持有transaction对象以及newTransaction标志位判断是否属于外层事务。
	 *
	 * 类似的，Spring对于回滚事务也是会在最外层事务方法对应的切面中进行物理回滚。而在非最外层事务的时候会由具体txMgr子类
	 * 给对应的事务打个的标记用于标识这个事务该回滚，这样的话在所有同一物理事务方法退栈过程中在事务切面中都能读取到事务被
	 * 打了应该回滚的标记。可以说这是同一物理事务方法之间进行通信的机制。
	 */
	@Nullable
	private final Object transaction;
	private final boolean newTransaction;

	private final boolean newSynchronization;

	private final boolean readOnly;

	private final boolean debug;

	@Nullable
	private final Object suspendedResources;


	/**
	 * Create a new {@code DefaultTransactionStatus} instance.
	 * @param transaction underlying transaction object that can hold state
	 * for the internal transaction implementation
	 * @param newTransaction if the transaction is new, otherwise participating
	 * in an existing transaction
	 * @param newSynchronization if a new transaction synchronization has been
	 * opened for the given transaction
	 * @param readOnly whether the transaction is marked as read-only
	 * @param debug should debug logging be enabled for the handling of this transaction?
	 * Caching it in here can prevent repeated calls to ask the logging system whether
	 * debug logging should be enabled.
	 * @param suspendedResources a holder for resources that have been suspended
	 * for this transaction, if any
	 */
	public DefaultTransactionStatus(
			@Nullable Object transaction,
			boolean newTransaction,
			boolean newSynchronization,
			boolean readOnly,
			boolean debug,
			@Nullable Object suspendedResources) {
		this.transaction = transaction;
		this.newTransaction = newTransaction;
		this.newSynchronization = newSynchronization;
		this.readOnly = readOnly;
		this.debug = debug;
		this.suspendedResources = suspendedResources;
	}


	/**
	 * Return the underlying transaction object.
	 * @throws IllegalStateException if no transaction is active
	 */
	public Object getTransaction() {
		Assert.state(this.transaction != null, "No transaction active");
		return this.transaction;
	}

	/**
	 * Return whether there is an actual transaction active.
	 */
	public boolean hasTransaction() {
		return (this.transaction != null);
	}

	@Override
	public boolean isNewTransaction() {
		return (hasTransaction() && this.newTransaction);
	}

	/**
	 * Return if a new transaction synchronization has been opened
	 * for this transaction.
	 */
	public boolean isNewSynchronization() {
		return this.newSynchronization;
	}

	/**
	 * Return if this transaction is defined as read-only transaction.
	 */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	/**
	 * Return whether the progress of this transaction is debugged. This is used by
	 * {@link AbstractPlatformTransactionManager} as an optimization, to prevent repeated
	 * calls to {@code logger.isDebugEnabled()}. Not really intended for client code.
	 */
	public boolean isDebug() {
		return this.debug;
	}

	/**
	 * Return the holder for resources that have been suspended for this transaction,
	 * if any.
	 */
	@Nullable
	public Object getSuspendedResources() {
		return this.suspendedResources;
	}


	//---------------------------------------------------------------------
	// Enable functionality through underlying transaction object
	//---------------------------------------------------------------------

	/**
	 * Determine the rollback-only flag via checking the transaction object, provided
	 * that the latter implements the {@link SmartTransactionObject} interface.
	 * <p>Will return {@code true} if the global transaction itself has been marked
	 * rollback-only by the transaction coordinator, for example in case of a timeout.
	 * @see SmartTransactionObject#isRollbackOnly()
	 */
	@Override
	public boolean isGlobalRollbackOnly() {
		return ((this.transaction instanceof SmartTransactionObject) && ((SmartTransactionObject) this.transaction).isRollbackOnly());
	}

	/**
	 * This implementation exposes the {@link SavepointManager} interface
	 * of the underlying transaction object, if any.
	 * @throws NestedTransactionNotSupportedException if savepoints are not supported
	 * @see #isTransactionSavepointManager()
	 */
	@Override
	protected SavepointManager getSavepointManager() {
		Object transaction = this.transaction;
		if (!(transaction instanceof SavepointManager)) {
			throw new NestedTransactionNotSupportedException(
					"Transaction object [" + this.transaction + "] does not support savepoints");
		}
		return (SavepointManager) transaction;
	}

	/**
	 * Return whether the underlying transaction implements the {@link SavepointManager}
	 * interface and therefore supports savepoints.
	 * @see #getTransaction()
	 * @see #getSavepointManager()
	 */
	public boolean isTransactionSavepointManager() {
		return (this.transaction instanceof SavepointManager);
	}

	/**
	 * Delegate the flushing to the transaction object, provided that the latter
	 * implements the {@link SmartTransactionObject} interface.
	 * @see SmartTransactionObject#flush()
	 */
	@Override
	public void flush() {
		if (this.transaction instanceof SmartTransactionObject) {
			((SmartTransactionObject) this.transaction).flush();
		}
	}

}
