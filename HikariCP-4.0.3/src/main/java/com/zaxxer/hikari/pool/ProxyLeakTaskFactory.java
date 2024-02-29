/*
 * Copyright (C) 2013, 2014 Brett Wooldridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zaxxer.hikari.pool;

import java.util.concurrent.ScheduledExecutorService;

/**
 * A factory for {@link ProxyLeakTask} Runnables that are scheduled in the future to report leaks.
 *
 * @author Brett Wooldridge
 * @author Andreas Brenk
 */
class ProxyLeakTaskFactory
{
   private ScheduledExecutorService executorService;
   private long leakDetectionThreshold;

   ProxyLeakTaskFactory(final long leakDetectionThreshold, final ScheduledExecutorService executorService)
   {
      this.executorService = executorService;
      this.leakDetectionThreshold = leakDetectionThreshold;
   }

   /**
    * 只有在leakDetectionThreshold不等于0的时候才会生成一个带有实际延时任务的ProxyLeakTask对象，否则返回无实际意义的空对象。
    * 所以要想启用连接泄漏检查，首先要把leakDetectionThreshold配置设置上，这个属性表示经过该时间后借出去的连接仍未归还，则触发连接泄漏告警。
    *
    * 跟Druid一样，HikariCP也有连接对象泄漏检查，与Druid主动回收连接相比，HikariCP实现更加简单，仅仅是在触发时打印警告日志，不会采取具体的强制回收的措施。
    */
   ProxyLeakTask schedule(final PoolEntry poolEntry)
   {
      return (leakDetectionThreshold == 0) ? ProxyLeakTask.NO_LEAK : scheduleNewTask(poolEntry);
   }

   void updateLeakDetectionThreshold(final long leakDetectionThreshold)
   {
      this.leakDetectionThreshold = leakDetectionThreshold;
   }

   private ProxyLeakTask scheduleNewTask(PoolEntry poolEntry) {
      ProxyLeakTask task = new ProxyLeakTask(poolEntry);
      task.schedule(executorService, leakDetectionThreshold);

      return task;
   }
}
