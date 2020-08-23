package com.sqsoft.shiro;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.*;

import com.sqsoft.filter.KickoutSessionControlFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置类
 */

@Configuration
public class ShiroConfiguration {
    
	@Bean
    public ShiroFilterFactoryBean getShiroFilter() {
        
		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/test.jsp", "authc, roles[admin,user], perms[file:edit]"); 
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/registe", "anon");
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/test", "YUAN");
        filterChainDefinitionMap.put("/**", "authc,kickout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        Map<String,Filter> filterMap = new LinkedHashMap<String,Filter>();
        filterMap.put("logout", getLogoutFilter());
        filterMap.put("YUAN",new TestShiroFilter());
        filterMap.put("kickout", kickoutSessionControlFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        
        return shiroFilterFactoryBean;
    }
    
	//配置退出后跳转的页面
    public LogoutFilter getLogoutFilter() {
    	LogoutFilter lf = new LogoutFilter();
    	lf.setRedirectUrl("/logout");
    	return lf;
    }

    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(getCacheManager());
        kickoutSessionControlFilter.setSessionManager(getSessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");
        return kickoutSessionControlFilter;
    }

	//配置核心安全事务管理器
    //@Bean(name="securityManager")
    public SecurityManager getSecurityManager() {
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setSessionManager(getSessionManager());
        manager.setRealm(getMyRealm());
        manager.setRememberMeManager(rememberMeManager());
        manager.setCacheManager(getCacheManager());
        return manager;
    }
	
    //@Bean
    public SessionManager getSessionManager() {
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    	// 设置全局session超时时间(默认30分钟)
    	sessionManager.setGlobalSessionTimeout(20*60*1000);
    	//session会话校验开关（默认为true）
    	sessionManager.setSessionValidationSchedulerEnabled(true);
    	//是否删除过期的session（默认为true）
    	sessionManager.setDeleteInvalidSessions(true);
    	//是否启用sessionIdcookie（默认为true）
    	sessionManager.setSessionIdCookieEnabled(true);
    	//自定义sessionid，默认是JSESSIONID
        // sessionManager.setSessionIdCookie();
    	//自定义sessionDAO
       	sessionManager.setSessionDAO(getSessionDAO());
       	//session失效全局检测周期默认（60分钟）
       	sessionManager.setSessionValidationInterval(10*60*1000);

       	//重定向时是否在url中拼接JSESSIONID
       	sessionManager.setSessionIdUrlRewritingEnabled(false);
    	return sessionManager;
    }

    public RedisSessionDAO getSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    //@Bean
//    public EnterpriseCacheSessionDAO getSessionDAO() {
//    	return new EnterpriseCacheSessionDAO();
//    }

    public RememberMeManager rememberMeManager() {
    	CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    	cookieRememberMeManager.setCookie(rememberMeCookie());
    	cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
    	return cookieRememberMeManager;
    }

    //记住我的cookie
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMeXXXXXXXXXXX");
        //如果cookie中设置了HttpOnly属性，那么通过js脚本将无法读取到cookie信息，这样能有效的防止XSS攻击
        //response.setHeader("Set-Cookie", "cookiename=httponlyTest;Path=/;Domain=domainvalue;Max-Age=seconds;HTTPOnly");
        cookie.setHttpOnly(true);
        //30天有效期
        cookie.setMaxAge(60 * 60 * 24 * 30);
        return cookie;
    }

    //@Bean
//	public CacheManager getCacheManager() {
//    	EhCacheManager cacheManager = new EhCacheManager();
//    	cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//    	return cacheManager;
//    }

    @Bean
    public RedisCacheManager getCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("username");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost:6379");
        //redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        redisManager.setPassword("123456789");
        return redisManager;
    }


    @Bean
    public AuthRealm getMyRealm() {
        AuthRealm authRealm=new AuthRealm();
        //授权缓存，默认是开启的
        authRealm.setAuthorizationCachingEnabled(true);
        //验证缓存，默认是关闭的
        //authRealm.setAuthenticationCachingEnabled(true);
        authRealm.setCredentialsMatcher(gerHashedCredentialsMatcher());
        return authRealm;
    }
    
    public HashedCredentialsMatcher gerHashedCredentialsMatcher() {
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
