package com.epichust.shiro;

import com.epichust.filter.KickoutSessionControlFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */

@Configuration
public class ShiroConfiguration
{

    /**
     * 覆盖shiro提供的 Authorizer ，详情见 ShiroWebAutoConfiguration#securityManager方法
     */
    @Bean("authorizer")
    public AuthRealm getMyRealm()
    {
        AuthRealm authRealm = new AuthRealm();
        //授权缓存，默认是开启的
        authRealm.setAuthorizationCachingEnabled(true);
        //验证缓存，默认是关闭的
        //authRealm.setAuthenticationCachingEnabled(true);
        authRealm.setCredentialsMatcher(gerHashedCredentialsMatcher());
        return authRealm;
    }

    /**
     * 覆盖shiro提供的 ShiroFilterChainDefinition ，详情见 ShiroWebAutoConfiguration#shiroFilterChainDefinition 方法
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition()
    {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // 登录请求，如果登录设置为login，因为页面的username和password和默认的不一致，不知道如何更改
        chainDefinition.addPathDefinition("/loginUser", "anon");
        // SpringBoot下当SpringMVC没有指定handler而报404的时候，内置的tomcat会重定向到/error被 BasicErrorController 处理，详情见SpringMVC文档
        chainDefinition.addPathDefinition("/error", "anon");
        chainDefinition.addPathDefinition("/test.jsp", "roles[admin,user], perms[file:edit]");
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    //配置退出后跳转的页面
    public LogoutFilter getLogoutFilter()
    {
        LogoutFilter lf = new LogoutFilter();
        lf.setRedirectUrl("/logout");
        return lf;
    }

//    public KickoutSessionControlFilter kickoutSessionControlFilter()
//    {
//        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
//        kickoutSessionControlFilter.setCacheManager(getCacheManager());
//        kickoutSessionControlFilter.setSessionManager(getSessionManager());
//        kickoutSessionControlFilter.setKickoutAfter(false);
//        kickoutSessionControlFilter.setMaxSession(1);
//        kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");
//        return kickoutSessionControlFilter;
//    }

    //配置核心安全事务管理器
    //@Bean(name="securityManager")
//    public SecurityManager getSecurityManager()
//    {
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setSessionManager(getSessionManager());
//        manager.setRealm(getMyRealm());
//        manager.setRememberMeManager(rememberMeManager());
//        manager.setCacheManager(getCacheManager());
//        return manager;
//    }

    //@Bean
//    public SessionManager getSessionManager()
//    {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        // 设置全局session超时时间(默认30分钟)
//        sessionManager.setGlobalSessionTimeout(20 * 60 * 1000);
//        //session会话校验开关（默认为true）
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        //是否删除过期的session（默认为true）
//        sessionManager.setDeleteInvalidSessions(true);
//        //是否启用sessionIdcookie（默认为true）
//        sessionManager.setSessionIdCookieEnabled(true);
//        //自定义sessionid，默认是JSESSIONID
//        // sessionManager.setSessionIdCookie();
//        //自定义sessionDAO
//        sessionManager.setSessionDAO(getSessionDAO());
//        //session失效全局检测周期默认（60分钟）
//        sessionManager.setSessionValidationInterval(10 * 60 * 1000);
//
//        //重定向时是否在url中拼接JSESSIONID
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
//        return sessionManager;
//    }

//    public RedisSessionDAO getSessionDAO()
//    {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }


    //@Bean
    public EnterpriseCacheSessionDAO getSessionDAO()
    {
        return new EnterpriseCacheSessionDAO();
    }

    public RememberMeManager rememberMeManager()
    {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
        return cookieRememberMeManager;
    }

    //记住我的cookie
    public SimpleCookie rememberMeCookie()
    {
        SimpleCookie cookie = new SimpleCookie("rememberMeXXXXXXXXXXX");
        //如果cookie中设置了HttpOnly属性，那么通过js脚本将无法读取到cookie信息，这样能有效的防止XSS攻击
        //response.setHeader("Set-Cookie", "cookiename=httponlyTest;Path=/;Domain=domainvalue;Max-Age=seconds;HTTPOnly");
        cookie.setHttpOnly(true);
        //30天有效期
        cookie.setMaxAge(60 * 60 * 24 * 30);
        return cookie;
    }

    @Bean
    public CacheManager getCacheManager()
    {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    public HashedCredentialsMatcher gerHashedCredentialsMatcher()
    {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashIterations(1024);
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        return hashedCredentialsMatcher;
    }

//    @Bean
//    public ShiroDialect shiroDialect(){
//        return new ShiroDialect();
//    }

//    @Bean()
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//    	return new LifecycleBeanPostProcessor();
//    }
//    //开启shiro 注解模式
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
//        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(manager);
//        return advisor;
//    }

    //    @Bean
//    public RedisCacheManager getCacheManager()
//    {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        //redis中针对不同用户缓存
//        redisCacheManager.setPrincipalIdFieldName("username");
//        //用户权限信息缓存时间
//        redisCacheManager.setExpire(200000);
//        return redisCacheManager;
//    }

//    public RedisManager redisManager()
//    {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost("localhost:6379");
//        //redisManager.setExpire(1800);// 配置缓存过期时间
//        redisManager.setTimeout(0);
//        redisManager.setPassword("123456");
//        return redisManager;
//    }


    public class TestShiroFilter implements Filter
    {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException
        {
            System.out.println("init");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
        {
            System.out.println("doFilter");
        }

        @Override
        public void destroy()
        {

        }
    }

}
