package webiste.lhc.heron.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.sun.tracing.ProbeName;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.config
 * @ClassName: Shiro
 * @Author: lhc
 * @Description: shiro配置类
 * @Date: 2020/8/16 上午 10:59
 */
@Configuration
public class ShiroConfig {
    @Bean
    public UserInfoRealm userInfoRealm() {
        UserInfoRealm realm = new UserInfoRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }



    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier(value = "userInfoRealm") UserInfoRealm userInfoRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userInfoRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier(value = "defaultWebSecurityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new LinkedHashMap<>(1);
        filterMap.put("authc", authentFilter());
        filterFactoryBean.setFilters(filterMap);
        Map<String, String> map = new LinkedHashMap<>();
//        map.put("/**", "anon");
        map.put("/static/**", "anon");
        map.put("/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setUnauthorizedUrl("/403");
        filterFactoryBean.setSuccessUrl("/");

        return filterFactoryBean;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("sha-1");
        credentialsMatcher.setHashIterations(3);
        return credentialsMatcher;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthentFilter authentFilter() {
        return new AuthentFilter();
    }

//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
//        return authorizationAttributeSourceAdvisor;
//    }


}
