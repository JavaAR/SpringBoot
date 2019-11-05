package com.deepblue.punchcard.controller;

import com.deepblue.punchcard.constant.ProjectConstants;
import com.deepblue.punchcard.customException.ServiceException;
import com.deepblue.punchcard.dto.Dto;
import com.deepblue.punchcard.dto.DtoUtils;
import com.deepblue.punchcard.entity.Mail;
import com.deepblue.punchcard.entity.SysPermissionInit;
import com.deepblue.punchcard.entity.SysUser;
import com.deepblue.punchcard.service.*;
import com.deepblue.punchcard.utils.EmptyUtils;
import com.deepblue.punchcard.utils.UploadActionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 测试controller
 */
@RequestMapping("api")
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private SysPermissionInitService sysPermissionInitService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermService rolePermService;

    @Resource
    private ShiroService shiroService;

    @Resource
    private MailService mailService;

    @GetMapping("/getUserRoles")
    public Dto getUserRoles(Integer id) {
        try {
            List<String> userRoleByUserId = userRoleService.getUserRoleByUserId(id);
            if (EmptyUtils.isNotEmpty(userRoleByUserId)) {
                return DtoUtils.returnDataSuccess(userRoleByUserId);
            }
            return DtoUtils.returnFail("me", ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("系统异常", ProjectConstants.ErrorCode.UNKNOW);
        }
    }


    @GetMapping("/getUserPrems")
    public Dto getUserPrems(Integer id) {
        try {
            List<String> userRoleByUserId = rolePermService.getUserPresByUserId(id);
            if (EmptyUtils.isNotEmpty(userRoleByUserId)) {
                return DtoUtils.returnDataSuccess(userRoleByUserId);
            }
            return DtoUtils.returnFail("me", ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("系统异常", ProjectConstants.ErrorCode.UNKNOW);
        }
    }

    /**
     * 模拟用户注册方法
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("userRegister")
    public Dto userRegister(SysUser user) {
        try {
            if (EmptyUtils.isNotEmpty(user.getUserName()) && EmptyUtils.isNotEmpty(user.getPassword())) {
                //校验是否存在
                HashMap<String, Object> paramMap = new HashMap<>();
                paramMap.put("userName", user.getUserName());
                List<SysUser> sysUsers = sysUserService.getSysUserListByMap(paramMap);
                if (EmptyUtils.isNotEmpty(sysUsers)) {
                    return DtoUtils.returnFail("该用户已存在，请重新填写", ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
                }
                //组织新增用户参数
                //加密密码，生成盐
                String salt = UUID.randomUUID().toString().replaceAll("-", "");
                Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt);
                SysUser sysUser = new SysUser(user.getUserName(), md5Hash.toString(), salt);
                Integer integer = sysUserService.insertSysUser(sysUser);
                if (integer > 0) {
                    return DtoUtils.returnSuccess("注册成功");
                }
                return DtoUtils.returnFail("注册失败", ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
            } else {
                return DtoUtils.returnFail("用户名密码不能为空", ProjectConstants.ErrorCode.PARAMETER_IS_NOT_NULL);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return DtoUtils.returnFail("系统异常", ProjectConstants.ErrorCode.UNKNOW);
        }
    }

    /**
     * 测试shiro认证
     *
     * @param user
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Dto getUserPrems(String user, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user, password);
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            throw new IncorrectCredentialsException("密码错误");
        } catch (AuthenticationException ae) {
            throw new AuthenticationException("用户名密码错误");
        }
        SysUser sysUser = (SysUser) subject.getPrincipal();
        return DtoUtils.returnDataSuccess(sysUser);
    }

    /**
     * 测试shiro认证
     *
     * @return
     */
    @PostMapping("selectAllUser")
    public Dto selectAllUser() {
        List<SysUser> sysUserListByMap = null;
        try {
            sysUserListByMap = sysUserService.getSysUserListByMap(new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("系统异常");

        }
        if (EmptyUtils.isNotEmpty(sysUserListByMap)) {
            return DtoUtils.returnDataSuccess(sysUserListByMap);
        }
        return DtoUtils.returnFail("没查到", ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
    }

    /**
     * 测试shiro授权
     *
     * @param id
     * @return
     */
    @PostMapping("selectUserByUserId")
    public Dto SelectUserById(Integer id) {
        try {
            SysUser sysUserById = sysUserService.getSysUserById(Long.valueOf(id));
            if (EmptyUtils.isNotEmpty(sysUserById)) {
                return DtoUtils.returnDataSuccess(sysUserById);
            }
            return DtoUtils.returnFail("未查到", ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("系统异常", ProjectConstants.ErrorCode.UNKNOW);
        }
    }

    /**
     * shiro退出
     *
     * @return
     */
    @GetMapping("logout")
    public Dto doLogOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return DtoUtils.returnSuccess("成功退出");
    }


    @PostMapping("updatePers")
    public Dto updatePers(SysPermissionInit sysPermissionInit) {
        try {
            Integer integer = sysPermissionInitService.updateSysPermissionInit(sysPermissionInit);
            if (integer > 0) {
                shiroService.updateShiroPermission();
                return DtoUtils.returnSuccess("跟新成功");
            }
            return DtoUtils.returnFail("修改失败", ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return DtoUtils.returnFail("系统异常", ProjectConstants.ErrorCode.UNKNOW);
        }
    }

    /**
     * 测试文件上传
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("testUpload")
    public Dto upload(HttpServletRequest request) throws IOException {
        List<String> strings = UploadActionUtil.uploadFiled(request);
        return DtoUtils.returnDataSuccess(strings);
    }

    /**
     * 测试邮件模板发送
     * @param mail
     * @return
     */
    @PostMapping("sendMail")
    public Dto sendMail(Mail mail){
        String replace = UUID.randomUUID().toString().replace("-", "");
        logger.info("验证码未："+replace);
        try {
            mail.setSubject("深蓝探索注册");
            mail.setTemplateName(ProjectConstants.MailConstant.RETGISTER_TMPLATE);
            Map<String, String> templParam = new HashMap<>();
            templParam.put("to",mail.getSendTo()[0]);
            templParam.put("identifyingCode",replace);
            mail.setTemplateModel(templParam);
            mailService.sendTemplateMail(mail);
            return DtoUtils.returnSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return DtoUtils.returnFail("发送失败，系统异常", ProjectConstants.ErrorCode.UNKNOW);
        }
    }

    /**
     *
     * @param mail
     * @param request
     * @return
     */
    @PostMapping("sendAccessory")
    public Dto sendAccessory(Mail mail,HttpServletRequest request){
        mail.setSubject("深蓝探索附件");
        mail.setText("附件，请查收");
        try {
            mailService.sendInlineMail(mail,request);
            return DtoUtils.returnSuccess("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("发送失败,系统异常", ProjectConstants.ErrorCode.UNKNOW);
        }
    }
}
