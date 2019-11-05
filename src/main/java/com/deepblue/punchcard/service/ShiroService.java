package com.deepblue.punchcard.service;

import java.util.Map;

/**
 * 优化shiro框架 每次更新权限之后都要重启
 * 因为在shiro配置时都是最先加载拦截请求与认证规则
 */
public interface ShiroService {

    /**
     *     获取数据库拦截请求与认证规则
     */
    Map<String,String> loadDBUrlAndResults() throws Exception;

    /**
     *     更新shiro配置
     */
    void updateShiroPermission() throws Exception;


}
