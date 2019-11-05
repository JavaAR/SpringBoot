package com.deepblue.punchcard.service;
import com.deepblue.punchcard.entity.SysRole;
import java.util.List;
import java.util.Map;

/**
*@Description: SysRoleService接口
* @author 向一
*@date 2019/10/30 22:04
*/
public interface SysRoleService {

    public SysRole getSysRoleById(Long id)throws Exception;

    public List<SysRole>	getSysRoleListByMap(Map<String,Object> param)throws Exception;

    public Integer getSysRoleCountByMap(Map<String,Object> param)throws Exception;

    public Integer insertSysRole(SysRole sysRole)throws Exception;

    public Integer updateSysRole(SysRole sysRole)throws Exception;

    public Integer deleteSysRoleById(Long id)throws Exception;


}
