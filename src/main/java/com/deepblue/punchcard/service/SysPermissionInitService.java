package com.deepblue.punchcard.service;
import com.deepblue.punchcard.entity.SysPermissionInit;
import java.util.List;
import java.util.Map;

/**
*@Description: SysPermissionInitService接口
* @author 向一
*@date 2019/10/30 22:04
*/
public interface SysPermissionInitService {

    public SysPermissionInit getSysPermissionInitById(Long id)throws Exception;

    public List<SysPermissionInit>	getSysPermissionInitListByMap(Map<String,Object> param)throws Exception;

    public Integer getSysPermissionInitCountByMap(Map<String,Object> param)throws Exception;

    public Integer insertSysPermissionInit(SysPermissionInit sysPermissionInit)throws Exception;

    public Integer updateSysPermissionInit(SysPermissionInit sysPermissionInit)throws Exception;

    public Integer deleteSysPermissionInitById(Long id)throws Exception;


}
