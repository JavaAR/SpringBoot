package com.deepblue.punchcard.service.serviceImpl;

import com.deepblue.punchcard.dao.UserInfoMapper;
import com.deepblue.punchcard.entity.UserInfo;
import com.deepblue.punchcard.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectUserInfoById(Integer id) {
        return userInfoMapper.selectUserInfoById(id);
    }
}
