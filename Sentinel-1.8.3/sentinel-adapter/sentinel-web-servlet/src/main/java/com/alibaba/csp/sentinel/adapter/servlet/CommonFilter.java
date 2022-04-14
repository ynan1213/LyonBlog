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
package com.alibaba.csp.sentinel.adapter.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.ResourceTypeConstants;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.adapter.servlet.config.WebServletConfig;
import com.alibaba.csp.sentinel.adapter.servlet.util.FilterUtil;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.StringUtil;

/**
 * Servlet filter that integrates with Sentinel.
 *
 * @author youji.zj
 * @author Eric Zhao
 * @author zhaoyuguang
 */
public class CommonFilter implements Filter {

    /**
     * Specify whether the URL resource name should contain the HTTP method prefix (e.g. {@code POST:}).
     */
    public static final String HTTP_METHOD_SPECIFY = "HTTP_METHOD_SPECIFY";
    /**
     * If enabled, use the default context name, or else use the URL path as the context name,
     * {@link WebServletConfig#WEB_SERVLET_CONTEXT_NAME}. Please pay attention to the number of context (EntranceNode),
     * which may affect the memory footprint.
     *
     * @since 1.7.0
     */
    public static final String WEB_CONTEXT_UNIFY = "WEB_CONTEXT_UNIFY";

    private final static String COLON = ":";

    private boolean httpMethodSpecify = false;
    private boolean webContextUnify = true;

    @Override
    public void init(FilterConfig filterConfig) {
        httpMethodSpecify = Boolean.parseBoolean(filterConfig.getInitParameter(HTTP_METHOD_SPECIFY));
        if (filterConfig.getInitParameter(WEB_CONTEXT_UNIFY) != null) {
            webContextUnify = Boolean.parseBoolean(filterConfig.getInitParameter(WEB_CONTEXT_UNIFY));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest sRequest = (HttpServletRequest) request;
        Entry urlEntry = null;

        try {
            String target = FilterUtil.filterTarget(sRequest);
            // Clean and unify the URL.
            // For REST APIs, you have to clean the URL (e.g. `/foo/1` and `/foo/2` -> `/foo/:id`), or
            // the amount of context and resources will exceed the threshold.
            UrlCleaner urlCleaner = WebCallbackManager.getUrlCleaner();
            if (urlCleaner != null) {
                /**
                 * 对于REST风格的url，比如/hello/{id}，id可能会是任意数，这样会有无数个resourceName，而资源数量阈值（目前是 6000）多出的资源的规则将不会生效。
                 * 这种情况下需要自行实现 `UrlCleaner` 接口清洗一下资源，比如将/hello/{id}统一为/hello/*
                 */
                target = urlCleaner.clean(target);
            }

            // If you intend to exclude some URLs, you can convert the URLs to the empty string ""
            // in the UrlCleaner implementation.
            if (!StringUtil.isEmpty(target)) {
                // Parse the request origin using registered origin parser.
                // origin 解析
                String origin = parseOrigin(sRequest);
                // 解析 contextName
                String contextName = webContextUnify ? WebServletConfig.WEB_SERVLET_CONTEXT_NAME : target;
                ContextUtil.enter(contextName, origin);

                if (httpMethodSpecify) {
                    /**
                     * 默认情况下，CommonFilter 是以url为resourceName
                     * 将这个参数指定为true之后，会以Http请求 Method:url 作为resourceName
                     * 比如 GET:/hello、POST:/hello，如果没开启，这两个请求是一个resourceName，如果开启了，是两个resourceName
                     */
                    String pathWithHttpMethod = sRequest.getMethod().toUpperCase() + COLON + target;
                    urlEntry = SphU.entry(pathWithHttpMethod, ResourceTypeConstants.COMMON_WEB, EntryType.IN);
                } else {
                    urlEntry = SphU.entry(target, ResourceTypeConstants.COMMON_WEB, EntryType.IN);
                }
            }
            chain.doFilter(request, response);
        } catch (BlockException e) {
            HttpServletResponse sResponse = (HttpServletResponse) response;
            // Return the block page, or redirect to another URL.
            WebCallbackManager.getUrlBlockHandler().blocked(sRequest, sResponse, e);
        } catch (IOException | ServletException | RuntimeException e2) {
            Tracer.traceEntry(e2, urlEntry);
            throw e2;
        } finally {
            if (urlEntry != null) {
                urlEntry.exit();
            }
            ContextUtil.exit();
        }
    }

    private String parseOrigin(HttpServletRequest request) {
        RequestOriginParser originParser = WebCallbackManager.getRequestOriginParser();
        String origin = EMPTY_ORIGIN;
        if (originParser != null) {
            origin = originParser.parseOrigin(request);
            if (StringUtil.isEmpty(origin)) {
                return EMPTY_ORIGIN;
            }
        }
        return origin;
    }

    @Override
    public void destroy() {

    }

    private static final String EMPTY_ORIGIN = "";
}
