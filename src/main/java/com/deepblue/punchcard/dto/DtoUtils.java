package com.deepblue.punchcard.dto;

/**
 *用于返回dto的工具类
 */
public class DtoUtils {
    private  static String success = "true";

    private static String fail = "false";

    private static String errorCode="0";

    /**
     * 返回成功 不带任何数据
     * @return
     */
    public static Dto returnSuccess(){
        Dto dto = new Dto();
        dto.setSuccess(success);
        return dto;
    }

    /**
     * 返回成功 并且携带数据以及信息
     * @param msg
     * @param data
     * @return
     */
    public static  Dto returnSuccess(String msg,Object data){
        Dto dto = new Dto();
        dto.setSuccess(success);
        dto.setErrorCode(errorCode);
        dto.setMsg(msg);
        dto.setData(data);
        return dto;
    }

    /**
     * 返回成功  只携带提示信息
     * @param msg
     * @return
     */
    public static Dto returnSuccess(String msg){
        Dto dto = new Dto();
        dto.setSuccess(success);
        dto.setErrorCode(errorCode);
        dto.setMsg(msg);
        return dto;
    }

    /**
     * 返回成功  只携带数据
     * @param data
     * @return
     */
    public static  Dto returnDataSuccess(Object data){
        Dto dto = new Dto();
        dto.setSuccess(success);
        dto.setErrorCode(errorCode);
        dto.setData(data);
        return dto;
    }

    /**
     *返回错误  写待错误信息以及状态码
     * @param msg
     * @param errorCode
     * @return
     */
    public static Dto returnFail(String msg,String errorCode){
        Dto dto = new Dto();
        dto.setSuccess(fail);
        dto.setMsg(msg);
        dto.setErrorCode(errorCode);
        return dto;
    }









}
