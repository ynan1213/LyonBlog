package com.epichust.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class SpringCacheKeyGenerator implements KeyGenerator
{

    private final static int NO_PARAM_KEY = 0;
    private String keyPrefix = "jf";// key前缀，用于区分不同项目的缓存，建议每个项目单独设置

    @Override
    public Object generate(Object target, Method method, Object... params)
    {
        char sp = ':';
        StringBuilder strBuilder = new StringBuilder(30);
        strBuilder.append(keyPrefix);
        strBuilder.append(sp);
        // 类名
        strBuilder.append(target.getClass().getSimpleName());
        strBuilder.append(sp);
        // 方法名
        strBuilder.append(method.getName());
        strBuilder.append(sp);
        if (params.length > 0) {
            // 参数值
            for (Object object : params) {
                if (BeanUtils.isSimpleValueType(object.getClass())) {
                    strBuilder.append(object);
                } else {
                    strBuilder.append(JSON.toJSONString(object).hashCode());
                }
            }
        } else {
            strBuilder.append(NO_PARAM_KEY);
        }
        return strBuilder.toString();
    }
}
