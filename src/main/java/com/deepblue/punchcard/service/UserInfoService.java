package com.deepblue.punchcard.service;

import com.deepblue.punchcard.entity.UserInfo;

public interface UserInfoService {
    UserInfo selectUserInfoById(Integer id);
}
