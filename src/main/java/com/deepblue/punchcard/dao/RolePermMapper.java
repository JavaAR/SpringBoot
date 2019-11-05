package com.deepblue.punchcard.dao;

import com.deepblue.punchcard.entity.RolePerm;

import java.util.List;
import java.util.Map;

/**
 * @author 向一
 * @Description: RolePermdao接口
 * @date 2019/10/23 23:29
 */
public interface RolePermMapper {

    public RolePerm getRolePermById(Long id) throws Exception;

    public List<RolePerm> getRolePermListByMap(Map<String, Object> param) throws Exception;

    public Integer getRolePermCountByMap(Map<String, Object> param) throws Exception;

    public Integer insertRolePerm(RolePerm rolePerm) throws Exception;

    public Integer updateRolePerm(RolePerm rolePerm) throws Exception;

    public Integer deleteRolePermById(Long id) throws Exception;

    //获取用户所有权限
    public List<String> getUserPermsByUserId(Integer id) throws Exception;

}
