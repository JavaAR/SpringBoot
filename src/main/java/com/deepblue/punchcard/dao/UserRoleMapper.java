package com.deepblue.punchcard.dao;

import com.deepblue.punchcard.entity.UserRole;

import java.util.List;
import java.util.Map;

/**
 * @author 向一
 * @Description: UserRoledao接口
 * @date 2019/10/23 23:29
 */
public interface UserRoleMapper {

    public UserRole getUserRoleById(Long id) throws Exception;

    public List<UserRole> getUserRoleListByMap(Map<String, Object> param) throws Exception;

    public Integer getUserRoleCountByMap(Map<String, Object> param) throws Exception;

    public Integer insertUserRole(UserRole userRole) throws Exception;

    public Integer updateUserRole(UserRole userRole) throws Exception;

    public Integer deleteUserRoleById(Long id) throws Exception;

    //根据用户id查询该用户的所有角色
    public List<String> getRolesByUserId(Integer id) throws Exception;
}
