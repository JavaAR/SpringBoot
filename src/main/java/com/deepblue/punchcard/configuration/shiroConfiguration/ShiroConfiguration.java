package com.deepblue.punchcard.configuration.shiroConfiguration;

import com.deepblue.punchcard.entity.SysPermissionInit;
import com.deepblue.punchcard.service.SysPermissionInitService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * shiro配置（shiro自带的拦截器，shiro SecurityManager ，自定义的Realm，盐加密，等）
 */
@Configuration
public class ShiroConfiguration {

    private Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Resource
    private SysPermissionInitService sysPermissionInitService;

    /**
     * 配置Shiro自带的拦截器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) throws Exception {
        logger.info("------------------start filter-------------------------");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置未认证发送的请求
        shiroFilterFactoryBean.setLoginUrl("/CustomShiroNotPass/noLogin");
        //配置没有权限发送的请求
        shiroFilterFactoryBean.setUnauthorizedUrl("/CustomShiroNotPass/noAuthorize");
        HashMap<String, String> filterMap = new HashMap<>();
        //方便管理将请求,将请求地址与认证规则放入数据库中
        List<SysPermissionInit> sysPermissionInitList = sysPermissionInitService.getSysPermissionInitListByMap(new HashMap<>());
        for (int i = 0;i<sysPermissionInitList.size();i++){
            SysPermissionInit sysPermissionInit = sysPermissionInitList.get(i);
            filterMap.put(sysPermissionInit.getUrl(),sysPermissionInit.getPermissionInit());
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        logger.info("-------Filter Inject Success----------");
        return shiroFilterFactoryBean;
    }
    /**
     * 配置SecurityManager
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("customRealm") CustomRealm customRealm){
        logger.info("------------------Start SecurityManager------------------");
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        return defaultSecurityManager;
    }
    /**
     * 注入自定义的Realm
     * @return
     */
    @Bean("customRealm")
    public CustomRealm customRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customRealm;
    }
    /**
     * 配置加密
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置使用加密算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        /*hashedCredentialsMatcher.setHashIterations(1024);*/
        return hashedCredentialsMatcher;
    }

}
