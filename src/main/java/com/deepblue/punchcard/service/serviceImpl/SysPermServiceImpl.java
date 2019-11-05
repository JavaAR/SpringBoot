package com.deepblue.punchcard.service.serviceImpl;
import com.deepblue.punchcard.dao.SysPermMapper;
import com.deepblue.punchcard.entity.SysPerm;
import com.deepblue.punchcard.service.SysPermService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
*@Description: SysPermService接口
* @author 向一
*@date2019/10/30 22:04
*/
@Service
public class SysPermServiceImpl implements SysPermService {

    @Resource
    private SysPermMapper sysPermMapper;
    @Override
    public SysPerm getSysPermById(Long id)throws Exception{
        return sysPermMapper.getSysPermById(id);
    }
    @Override
    public List<SysPerm>	getSysPermListByMap(Map<String,Object> param)throws Exception{
        return sysPermMapper.getSysPermListByMap(param);
    }
    @Override
    public Integer getSysPermCountByMap(Map<String,Object> param)throws Exception{
        return sysPermMapper.getSysPermCountByMap(param);
    }
    @Override
    public Integer insertSysPerm(SysPerm sysPerm)throws Exception{
            return sysPermMapper.insertSysPerm(sysPerm);
    }
    @Override
    public Integer updateSysPerm(SysPerm sysPerm)throws Exception{
        return sysPermMapper.updateSysPerm(sysPerm);
    }
    @Override
    public Integer deleteSysPermById(Long id)throws Exception{
        return sysPermMapper.deleteSysPermById(id);
    }



}
