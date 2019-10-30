package com.deepblue.punchcard.configuration.shiroConfiguration;

import com.deepblue.punchcard.entity.SysUser;
import com.deepblue.punchcard.service.RolePermService;
import com.deepblue.punchcard.service.SysUserService;
import com.deepblue.punchcard.service.UserRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * 自定义的Realm
 */
public class CustomRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    @Resource
    private SysUserService sysUserService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermService rolePermService;

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(principalCollection==null){
            throw new AuthorizationException("PrincipalCollection method argument cannot be null");
        }
        //获取用户信息
        SysUser sysUser = (SysUser)getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(sysUser.getSysRoles());
        simpleAuthorizationInfo.setStringPermissions(sysUser.getSysPerms());
        return simpleAuthorizationInfo;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //1.获取用户名（唯一）
       String userName = (String) authenticationToken.getPrincipal();
        logger.info("---------userName is----------"+userName);
       //2.根据用户名获取对象
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName",userName);
        List<SysUser> userList = null;
        SimpleAuthenticationInfo simpleAuthorizationInfo = null;
        try {
            userList = sysUserService.getSysUserListByMap(map);
            SysUser sysUser = userList.get(0);
                //获取用户的权限与角色并且赋值到用户对象中 SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
            List<String> userRoles = userRoleService.getUserRoleByUserId(sysUser.getId());
            List<String> userPrems = rolePermService.getUserPresByUserId(sysUser.getId());
            Set<String> roleSet = new HashSet<>(userRoles);
            Set<String> premsSet = new HashSet<>(userPrems);
            sysUser.setSysRoles(roleSet);
            sysUser.setSysPerms(premsSet);
            //3.组织返回对象
             simpleAuthorizationInfo = new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),getName());
            //4.设置盐
            simpleAuthorizationInfo.setCredentialsSalt(ByteSource.Util.bytes(sysUser.getSalt()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //5.返回对象
        return simpleAuthorizationInfo;
    }
}
