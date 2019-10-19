package com.deepblue.punchcard.dao;

import com.deepblue.punchcard.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

    UserInfo selectUserInfoById(@Param("id") Integer id);


}

