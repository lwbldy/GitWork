package com.lwbldy.common.config;

import com.lwbldy.common.shiro.MyRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    /**
     * 配置进行授权和认证的 Realm，要新增一个java类来实现，下面会有，class=包名.类名，init-methood是初始化的方法
     * @return
     */
    @Bean("myRealm")
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialMatcher();
        return myRealm;
    }

    /**
     * 配置緩存管理器
     * @return
     */
    @Bean("cacheManager")
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 配置 SecurityManager Bean.
     * @param myRealm
     * @param ehCacheManager
     * @return
     */
    @Bean("securityManager")
    public SecurityManager defaultWebSecurityManager(MyRealm myRealm,EhCacheManager ehCacheManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        defaultWebSecurityManager.setCacheManager(ehCacheManager);
        return defaultWebSecurityManager;
    }

    /**
     * 配置 ShiroFilter bean: 该 bean 的 id 必须和 web.xml 文件中配置的 shiro filter 的 name 一致
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //装配 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置登陆页面
        shiroFilterFactoryBean.setLoginUrl("/sys/login");
        //配置登录成功后的页面
        shiroFilterFactoryBean.setSuccessUrl("/sys/index");
        //配置未经授权的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/login");
        //具体配置需要拦截哪些 URL, 以及访问对应的 URL 时使用 Shiro 的什么 Filter 进行拦截.
        shiroFilterFactoryBean.setFilterChainDefinitions(
                "/**/*.html = anon\n"+
                "/**/*.css = anon\n"+
                "/**/*.js = anon\n"+
                "/lib/** = anon\n"+
                "/sys/login = anon\n"+
                "/sys/captcha = anon\n"+
                "/sys/checkCaptcha = anon\n"+
                "/static/** = anon\n"+
                "/** = authc"
        );

        return shiroFilterFactoryBean;
    }


    // 加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
