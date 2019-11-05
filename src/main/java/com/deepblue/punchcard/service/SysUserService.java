package com.deepblue.punchcard.service;
import com.deepblue.punchcard.entity.SysUser;
import java.util.List;
import java.util.Map;

/**
*@Description: SysUserService接口
* @author 向一
*@date 2019/10/30 22:04
*/
public interface SysUserService {

    public SysUser getSysUserById(Long id)throws Exception;

    public List<SysUser>	getSysUserListByMap(Map<String,Object> param)throws Exception;

    public Integer getSysUserCountByMap(Map<String,Object> param)throws Exception;

    public Integer insertSysUser(SysUser sysUser)throws Exception;

    public Integer updateSysUser(SysUser sysUser)throws Exception;

    public Integer deleteSysUserById(Long id)throws Exception;


}
