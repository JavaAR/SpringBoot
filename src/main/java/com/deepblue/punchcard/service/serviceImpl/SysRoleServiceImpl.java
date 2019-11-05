package com.deepblue.punchcard.service.serviceImpl;
import com.deepblue.punchcard.dao.SysRoleMapper;
import com.deepblue.punchcard.entity.SysRole;
import com.deepblue.punchcard.service.SysRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
*@Description: SysRoleService接口
* @author 向一
*@date2019/10/30 22:04
*/
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Override
    public SysRole getSysRoleById(Long id)throws Exception{
        return sysRoleMapper.getSysRoleById(id);
    }
    @Override
    public List<SysRole>	getSysRoleListByMap(Map<String,Object> param)throws Exception{
        return sysRoleMapper.getSysRoleListByMap(param);
    }
    @Override
    public Integer getSysRoleCountByMap(Map<String,Object> param)throws Exception{
        return sysRoleMapper.getSysRoleCountByMap(param);
    }
    @Override
    public Integer insertSysRole(SysRole sysRole)throws Exception{
            return sysRoleMapper.insertSysRole(sysRole);
    }
    @Override
    public Integer updateSysRole(SysRole sysRole)throws Exception{
        return sysRoleMapper.updateSysRole(sysRole);
    }
    @Override
    public Integer deleteSysRoleById(Long id)throws Exception{
        return sysRoleMapper.deleteSysRoleById(id);
    }



}
