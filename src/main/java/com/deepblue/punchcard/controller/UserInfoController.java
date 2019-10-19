package com.deepblue.punchcard.controller;


import com.deepblue.punchcard.customException.ServiceException;
import com.deepblue.punchcard.dto.Dto;
import com.deepblue.punchcard.dto.DtoUtils;
import com.deepblue.punchcard.entity.UserInfo;
import com.deepblue.punchcard.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@Api(tags = "测试Swagger接口",description ="Swagger测试接口" )
@RestController
@RequestMapping("api")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 测试mybatis以及Druid 的接口
     * @param id
     * @return
     */
    @GetMapping("/userLogOut")
    public Dto getUserInfoById(Integer id){
        UserInfo userInfo = userInfoService.selectUserInfoById(id);
        if(userInfo!=null){
            return DtoUtils.returnDataSuccess(userInfo);
        }
        return DtoUtils.returnFail("没有找到","20001");
    }

    /**
     * 测试异常接口
     * @return
     */
    @GetMapping("/testException")
    public Dto testException(){
        throw new ServiceException("服务器宕机了");
    }

    /**
     * 测试Swagger接口
     * @param id
     * @return
     */

    @ApiOperation(value = "Swagger测试查询用户",notes = "根据id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",required = true,paramType = "query",dataType = "Integer")
    })
    @GetMapping("/testSwagger")
    public Dto testSwagger(@RequestParam Integer id){
        UserInfo userInfo = userInfoService.selectUserInfoById(id);
        if(userInfo!=null){
            return DtoUtils.returnDataSuccess(userInfo);
        }
        return DtoUtils.returnFail("没有找到","20001");
    }

}
