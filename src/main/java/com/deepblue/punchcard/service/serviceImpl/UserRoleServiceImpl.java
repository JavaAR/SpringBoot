package com.deepblue.punchcard.service.serviceImpl;
import com.deepblue.punchcard.dao.UserRoleMapper;
import com.deepblue.punchcard.entity.UserRole;
import com.deepblue.punchcard.service.UserRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
*@Description: UserRoleService接口
* @author 向一
*@date2019/10/23 23:29
*/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    public UserRole getUserRoleById(Long id)throws Exception{
        return userRoleMapper.getUserRoleById(id);
    }
    @Override
    public List<UserRole>	getUserRoleListByMap(Map<String,Object> param)throws Exception{
        return userRoleMapper.getUserRoleListByMap(param);
    }
    @Override
    public Integer getUserRoleCountByMap(Map<String,Object> param)throws Exception{
        return userRoleMapper.getUserRoleCountByMap(param);
    }
    @Override
    public Integer insertUserRole(UserRole userRole)throws Exception{
            return userRoleMapper.insertUserRole(userRole);
    }
    @Override
    public Integer updateUserRole(UserRole userRole)throws Exception{
        return userRoleMapper.updateUserRole(userRole);
    }
    @Override
    public Integer deleteUserRoleById(Long id)throws Exception{
        return userRoleMapper.deleteUserRoleById(id);
    }

    @Override
    public List<String> getUserRoleByUserId(Integer id) throws Exception {
        return userRoleMapper.getRolesByUserId(id);
    }


}
