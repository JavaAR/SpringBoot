package com.deepblue.punchcard.constant;

/**
 * 项目常量类
 */
public class ProjectConstants {
    /**
     * 错误码
     */
    public static class ErrorCode{
        //系统异常错误码
        public final static String UNKNOW ="20000";
         //404异常错误码
        public final static String NOHANDLERFOUND ="20404";
        //自定义异常类错误码
        public final static String SERVICEEXCEPTIONCODE ="20001";
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
     * 添加其他常量
     */




}
