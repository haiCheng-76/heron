package webiste.lhc.heron.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        Map<String, String> map = new LinkedHashMap<>();
        map.put("/**", "anon");
//        map.put("/static/**", "anon");
//        map.put("/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setUnauthorizedUrl("/403");
        return filterFactoryBean;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        credentialsMatcher.setHashAlgorithmName("sha-1");
        credentialsMatcher.setHashIterations(3);
        return credentialsMatcher;
    }
}
