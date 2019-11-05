package com.deepblue.punchcard.service;

import com.deepblue.punchcard.entity.RolePerm;

import java.util.List;
import java.util.Map;

/**
 * @author 向一
 * @Description: RolePermService接口
 * @date 2019/10/30 22:04
 */
public interface RolePermService {

    public RolePerm getRolePermById(Long id) throws Exception;

    public List<RolePerm> getRolePermListByMap(Map<String, Object> param) throws Exception;

    public Integer getRolePermCountByMap(Map<String, Object> param) throws Exception;

    public Integer insertRolePerm(RolePerm rolePerm) throws Exception;

    public Integer updateRolePerm(RolePerm rolePerm) throws Exception;

    public Integer deleteRolePermById(Long id) throws Exception;

    //获取用户所有权限
    public List<String> getUserPresByUserId(Integer id) throws Exception;


}
