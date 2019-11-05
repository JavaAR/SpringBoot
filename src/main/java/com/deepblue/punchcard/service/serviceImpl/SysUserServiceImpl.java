package com.deepblue.punchcard.service.serviceImpl;

import com.deepblue.punchcard.dao.SysUserMapper;
import com.deepblue.punchcard.entity.SysUser;
import com.deepblue.punchcard.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author 向一
 * @Description: SysUserService接口
 * @date2019/10/30 22:04
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getSysUserById(Long id) throws Exception {
        return sysUserMapper.getSysUserById(id);
    }

    @Override
    public List<SysUser> getSysUserListByMap(Map<String, Object> param) throws Exception {
        return sysUserMapper.getSysUserListByMap(param);
    }

    @Override
    public Integer getSysUserCountByMap(Map<String, Object> param) throws Exception {
        return sysUserMapper.getSysUserCountByMap(param);
    }

    @Override
    public Integer insertSysUser(SysUser sysUser) throws Exception {
        return sysUserMapper.insertSysUser(sysUser);
    }

    @Override
    public Integer updateSysUser(SysUser sysUser) throws Exception {
        return sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public Integer deleteSysUserById(Long id) throws Exception {
        return sysUserMapper.deleteSysUserById(id);
    }


}
