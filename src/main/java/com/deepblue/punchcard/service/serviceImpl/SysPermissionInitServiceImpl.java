package com.deepblue.punchcard.service.serviceImpl;
import com.deepblue.punchcard.dao.SysPermissionInitMapper;
import com.deepblue.punchcard.entity.SysPermissionInit;
import com.deepblue.punchcard.service.SysPermissionInitService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
*@Description: SysPermissionInitService接口
* @author 向一
*@date2019/10/30 22:04
*/
@Service
public class SysPermissionInitServiceImpl implements SysPermissionInitService {

    @Resource
    private SysPermissionInitMapper sysPermissionInitMapper;
    @Override
    public SysPermissionInit getSysPermissionInitById(Long id)throws Exception{
        return sysPermissionInitMapper.getSysPermissionInitById(id);
    }
    @Override
    public List<SysPermissionInit>	getSysPermissionInitListByMap(Map<String,Object> param)throws Exception{
        return sysPermissionInitMapper.getSysPermissionInitListByMap(param);
    }
    @Override
    public Integer getSysPermissionInitCountByMap(Map<String,Object> param)throws Exception{
        return sysPermissionInitMapper.getSysPermissionInitCountByMap(param);
    }
    @Override
    public Integer insertSysPermissionInit(SysPermissionInit sysPermissionInit)throws Exception{
            return sysPermissionInitMapper.insertSysPermissionInit(sysPermissionInit);
    }
    @Override
    public Integer updateSysPermissionInit(SysPermissionInit sysPermissionInit)throws Exception{
        return sysPermissionInitMapper.updateSysPermissionInit(sysPermissionInit);
    }
    @Override
    public Integer deleteSysPermissionInitById(Long id)throws Exception{
        return sysPermissionInitMapper.deleteSysPermissionInitById(id);
    }



}
