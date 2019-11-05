package com.deepblue.punchcard.constant;

/**
 * 项目常量类
 */
public class ProjectConstants {
    /**
     * 错误码
     */
    public static class ErrorCode {
        //系统异常错误码
        public final static String UNKNOW = "20000";
        //404异常错误码
        public final static String NOHANDLERFOUND = "20404";
        //自定义异常类错误码
        public final static String SERVICEEXCEPTIONCODE = "20001";
        //shiro认证用户名为空异常
        public final static String SHIRO_AUTH_USERNAMEISNULL = "20002";
        //Shiro认证密码错误异常
        public final static String SHIRO_AUTH_PASSWORDISMISS = "20003";
        //Shiro 用户未登录错误码
        public final static String SHIRO_AUTH_USERNOLOGIN = "20004";
        //shiro 用户授权没有权限
        public final static String SHIRO_PREM_NOT_PREM = "20403";
        //请求参数不能为空
        public final static String PARAMETER_IS_NOT_NULL = "20005";
        //添加其他
    }

    /**
     * redis 常量
     */
    public static class Redis_Expire {
        public static final long DEFAULT_EXPIRE = 60;
        public final static int SESSION_TIMEOUT = 2 * 60 * 60;
        //添加其他

    }

    /**
     *邮件实体类
     */
    public static class MailConstant{
        //注册模板名
        public static final String RETGISTER_TMPLATE="register";
        //添加其他模板名

        //存放模板路径
        public static final String TMPLATE_PATH="src/main/resources/templates/MailTemplate";


    }

    /**
     * 添加其他内部类常量
     */




}
