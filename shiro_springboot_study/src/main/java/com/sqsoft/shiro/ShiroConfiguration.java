package com.sqsoft.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * shiro配置类
 */

@Configuration
public class ShiroConfiguration {
    
	@Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager manager) {
        
		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(manager);
        
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
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        Map<String,Filter> filterMap = new LinkedHashMap<String,Filter>();
        filterMap.put("logout", getLogoutFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        
        return shiroFilterFactoryBean;
    }
    
	//配置退出后跳转的页面
    public LogoutFilter getLogoutFilter() {
    	LogoutFilter lf = new LogoutFilter();
    	lf.setRedirectUrl("/logout");
    	return lf;
    }
	
	//配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setSessionManager(getSessionManager());
        manager.setRealm(getRealm());
        manager.setRememberMeManager(rememberMeManager());
        manager.setCacheManager(getCacheManager());
        return manager;
    }
	
    @Bean
    public SessionManager getSessionManager() {
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    	// 设置全局session超时时间(默认30分钟)
    	sessionManager.setGlobalSessionTimeout(30*60*1000);
    	//session会话校验开关（默认为true）
    	sessionManager.setSessionValidationSchedulerEnabled(true);
    	//是否删除过期的session（默认为true）
    	sessionManager.setDeleteInvalidSessions(true);
    	//是否启用sessionIdcookie（默认为true）
    	sessionManager.setSessionIdCookieEnabled(true);
    	//自定义sessionDAO
       	sessionManager.setSessionDAO(getSessionDAO());
       	//session失效全局检测周期默认（60分钟）
       	sessionManager.setSessionValidationInterval(10*60*1000);
    	return sessionManager;
    }
    
    @Bean
    public EnterpriseCacheSessionDAO getSessionDAO() {
    	return new EnterpriseCacheSessionDAO();
    }
    
    //记住我的cookie
    public SimpleCookie rememberMeCookie() {
    	SimpleCookie cookie = new SimpleCookie("rememberMe");
    	cookie.setHttpOnly(true);
    	//30天有效期
    	cookie.setMaxAge(60 * 60 * 24 * 30);
    	return cookie;
    }
    
    public RememberMeManager rememberMeManager() {
    	CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    	cookieRememberMeManager.setCookie(rememberMeCookie());
    	cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
    	return cookieRememberMeManager;
    }
    
    @Bean
	public CacheManager getCacheManager() {
    	EhCacheManager cacheManager = new EhCacheManager();
    	cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
    	return cacheManager;
    }
	
    @Bean
    public AuthRealm getRealm() {
        AuthRealm authRealm=new AuthRealm();
        authRealm.setAuthorizationCachingEnabled(true);
        authRealm.setCredentialsMatcher(gerHashedCredentialsMatcher());
        return authRealm;
    }
    
    public HashedCredentialsMatcher gerHashedCredentialsMatcher() {
    	HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    	hashedCredentialsMatcher.setHashIterations(1024);
    	hashedCredentialsMatcher.setHashAlgorithmName("MD5");
    	return hashedCredentialsMatcher;
    }
    
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
    
    @Bean()
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    	return new LifecycleBeanPostProcessor();
    }
    
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

}
