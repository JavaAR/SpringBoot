package com.deepblue.punchcard.service;
import com.deepblue.punchcard.entity.UserRole;
import java.util.List;
import java.util.Map;

/**
*@Description: UserRoleService接口
* @author 向一
*@date 2019/10/30 22:04
*/
public interface UserRoleService {

    public UserRole getUserRoleById(Long id)throws Exception;

    public List<UserRole>	getUserRoleListByMap(Map<String,Object> param)throws Exception;

    public Integer getUserRoleCountByMap(Map<String,Object> param)throws Exception;

    public Integer insertUserRole(UserRole userRole)throws Exception;

    public Integer updateUserRole(UserRole userRole)throws Exception;

    public Integer deleteUserRoleById(Long id)throws Exception;

    //根据用户id查询该用户的所有角色
    public List<String> getUserRoleByUserId(Integer id) throws Exception;


}
