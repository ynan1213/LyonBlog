package com.ynan.plugin;

import java.util.Properties;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2022/3/3 23:55
 */
@Component
@Intercepts(
    {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    }
)
public class MyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        System.out.println("sql === " + sql);

        Executor executor = (Executor) invocation.getTarget();
        SpringManagedTransaction originTransaction = (SpringManagedTransaction) executor.getTransaction();



        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
