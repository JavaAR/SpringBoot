package com.deepblue.punchcard.service.serviceImpl;
import com.deepblue.punchcard.dao.RolePermMapper;
import com.deepblue.punchcard.entity.RolePerm;
import com.deepblue.punchcard.service.RolePermService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
*@Description: RolePermService接口
* @author 向一
*@date2019/10/23 23:29
*/
@Service
public class RolePermServiceImpl implements RolePermService {

    @Resource
    private RolePermMapper rolePermMapper;
    @Override
    public RolePerm getRolePermById(Long id)throws Exception{
        return rolePermMapper.getRolePermById(id);
    }
    @Override
    public List<RolePerm>	getRolePermListByMap(Map<String,Object> param)throws Exception{
        return rolePermMapper.getRolePermListByMap(param);
    }
    @Override
    public Integer getRolePermCountByMap(Map<String,Object> param)throws Exception{
        return rolePermMapper.getRolePermCountByMap(param);
    }
    @Override
    public Integer insertRolePerm(RolePerm rolePerm)throws Exception{
            return rolePermMapper.insertRolePerm(rolePerm);
    }
    @Override
    public Integer updateRolePerm(RolePerm rolePerm)throws Exception{
        return rolePermMapper.updateRolePerm(rolePerm);
    }
    @Override
    public Integer deleteRolePermById(Long id)throws Exception{
        return rolePermMapper.deleteRolePermById(id);
    }

    @Override
    public List<String> getUserPresByUserId(Integer id) throws Exception {
        return rolePermMapper.getUserPermsByUserId(id);
    }


}
