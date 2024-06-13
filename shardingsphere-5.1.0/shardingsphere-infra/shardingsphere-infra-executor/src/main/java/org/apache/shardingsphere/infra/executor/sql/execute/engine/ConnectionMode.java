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

package org.apache.shardingsphere.infra.executor.sql.execute.engine;

/**
 * Connection Mode.
 *
 * 详情见 https://ost.51cto.com/posts/17612
 */
public enum ConnectionMode {

    /**
     * MEMORY_STRICTLY 代表内存限制模式，当采用内存限制模式时，对于同一个数据源，如果逻辑表对应了 10 个真实表，
     * 那么 SQL 执行引擎会创建 10 个连接并行地执行，由于每个分片的结果集都有对应的连接进行持有，因此无需将结果集提前加载到内存中，从而有效地降低了内存占用；
     */
    MEMORY_STRICTLY,


    /**
     * CONNECTION_STRICTLY 代表连接限制模式，当采用连接限制模式时，SQL 执行引擎只会在同一个数据源上创建一个连接，
     * 严格控制对数据库连接资源的消耗，在真实 SQL 执行之后立即将结果集加载至内存，因此会占用部分内存空间。
     */
    CONNECTION_STRICTLY
}
