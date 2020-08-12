package hzau.sa.security.Shiro;


import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //connect with SecurityManager
        bean.setSecurityManager(defaultWebSecurityManager);

        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("jwt", new JwtFilter());
        bean.setFilters(filterMap);

        //添加Shiro内置过滤器
        /*
            anon:无需认证就可以访问
            authc:必须认证了才能访问
            user:必须拥有记住我功能
            perms:拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
         */
        Map<String,String> filterRuleMap = new LinkedHashMap<>();
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/getToken*", "anon");
        filterRuleMap.put("/swagger-resources", "anon");
        filterRuleMap.put("/swagger-resources/configuration/ui", "anon");
        filterRuleMap.put("/swagger-resources/configuration/security", "anon");
        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/csrf", "anon");
        filterRuleMap.put("/**/*.html", "anon");
        filterRuleMap.put("/**/*.js", "anon");
        filterRuleMap.put("/**/*.css", "anon");
        filterRuleMap.put("/**/*.woff", "anon");
        filterRuleMap.put("/**/*.woff2", "anon");
        filterRuleMap.put("/**/*.png", "anon");
        filterRuleMap.put("/**/*.gif", "anon");
        filterRuleMap.put("/**/*.jpg", "anon");
        filterRuleMap.put("/favicon.ico", "anon");
        filterRuleMap.put("/", "anon");
        filterRuleMap.put("/**", "jwt");

        bean.setFilterChainDefinitionMap(filterRuleMap);

        return bean;
    }

    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(JwtRealm jwtRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(jwtRealm);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }


    /**
     * 以下三个bean实现细粒度的注解控制权限
     *
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
