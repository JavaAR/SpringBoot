package com.deepblue.punchcard.service.serviceImpl;

import com.deepblue.punchcard.entity.SysPermissionInit;
import com.deepblue.punchcard.service.ShiroService;
import com.deepblue.punchcard.service.SysPermissionInitService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ShiroServiceImpl implements ShiroService {

    private Logger logger = LoggerFactory.getLogger(ShiroServiceImpl.class);


    @Resource
    private SysPermissionInitService sysPermissionInitService;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;


    //加载数据库的访问路径与权限设置
    @Override
    public Map<String, String> loadDBUrlAndResults() throws Exception {
        //参数
        HashMap<String, Object> paramMap = new HashMap<>();
        //返回结果
        HashMap<String, String> resultMap = new HashMap<>();
        List<SysPermissionInit> resultList = sysPermissionInitService.getSysPermissionInitListByMap(paramMap);
        for (SysPermissionInit sysPermissionInit : resultList) {
            resultMap.put(sysPermissionInit.getUrl(), sysPermissionInit.getPermissionInit());
        }
        return resultMap;
    }

    //跟新到shiro配置
    @Override
    public void updateShiroPermission() throws Exception {
        synchronized (shiroFilterFactoryBean) {

            AbstractShiroFilter abstractShiroFilter = null;
            try {
                abstractShiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                logger.error("获取Shiro拦截器实例失败");
            }
            //获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) abstractShiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            //清空之前的权限配置
            filterChainManager.getFilterChains().clear();
            //清空拦截工厂的权限
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            //重新添加拦截url与认证规则
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadDBUrlAndResults());
            //重新构建过滤拦截器的规则
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            Iterator iterator = chains.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,String> entry =(Map.Entry)iterator.next();
                String url = (String)entry.getKey();
                String  chainDefinition =(String)entry.getValue();
                filterChainManager.createChain(url,chainDefinition);
            }
            logger.info("跟新权限成功");
        }
    }
}
