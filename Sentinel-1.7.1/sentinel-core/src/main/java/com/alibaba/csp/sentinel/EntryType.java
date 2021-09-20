/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel;

/**
 * An enum marks resource invocation direction.
 *
 * @author jialiang.linjl
 *
 * EntryType.IN 代表这个是入口流量，比如我们的接口对外提供服务，那么我们通常就是控制入口流量；
 * EntryType.OUT 代表出口流量
 */
public enum EntryType {
    /**
     * Inbound traffic
     */
    IN("IN"),
    /**
     * Outbound traffic
     */
    OUT("OUT");

    private final String name;

    EntryType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return name;
    }
}
