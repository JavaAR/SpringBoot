package com.deepblue.punchcard.configuration;

import com.deepblue.punchcard.constant.ProjectConstants;
import com.deepblue.punchcard.customException.ServiceException;
import com.deepblue.punchcard.dto.Dto;
import com.deepblue.punchcard.dto.DtoUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionConfiguration {

    private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";

    private  Logger logger = LoggerFactory.getLogger(GlobalExceptionConfiguration.class);

    /**
     * 请求接口不存在异常（404）
     */
   @ExceptionHandler(NoHandlerFoundException.class)
    public Dto noHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request){
       String requestURI = request.getRequestURI();
       Date date = new Date();
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
       String format1 = format.format(date);
       logger.error(ex.getMessage());
       return DtoUtils.returnFail(format1+"->请求的接口:"+requestURI+"不存在", ProjectConstants.ErrorCode.UNKNOW);
   }

    /**
     * 空指针异常
     * @param e
     * @return
     */
   @ExceptionHandler(NullPointerException.class)
   public Dto doNullPointerException(NullPointerException e){
       //返回结果统一格式化(参数：异常错误码,异常对象)
       return exceptionResultFormat(ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE,e);
   }

    /**
     * shiro授权没有权限
     * @param
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
   public Dto doUnauthorizedException(UnauthorizedException ue){
       return exceptionResultFormat(ProjectConstants.ErrorCode.SHIRO_PREM_NOT_PREM,ue);
   }

    /**
     * shiro用户未认证
     * @param ue
     * @return
     */
   @ExceptionHandler(UnauthenticatedException.class)
   public Dto doUnauthenticatedException(UnauthenticatedException ue){
       return exceptionResultFormat(ProjectConstants.ErrorCode.SHIRO_AUTH_USERNOLOGIN,ue);
   }

    /**
     * Shiro认证用户名为空异常
     * @param ae
     * @return
     */
   @ExceptionHandler(AccountException.class)
   public Dto doAccountException(AccountException ae){
       return exceptionResultFormat(ProjectConstants.ErrorCode.SHIRO_AUTH_USERNAMEISNULL,ae);
   }

    /**
     * Shiro认证密码错误
     * @param ue
     * @return
     */
   @ExceptionHandler(UnknownAccountException.class)
   public Dto doUnknownAccountException(UnknownAccountException ue){
       return exceptionResultFormat(ProjectConstants.ErrorCode.SHIRO_AUTH_PASSWORDISMISS,ue);
   }
    /**
     * 自定义异常
     * @param sex
     * @return
     */
   @ExceptionHandler(ServiceException.class)
   public Dto customException(ServiceException sex){
     return exceptionResultFormat(ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE,sex);
   }
    /**
     * 其他异常
     * @param e
     * @return
     */
   @ExceptionHandler(Exception.class)
   public Dto otherException(Exception e){
       return exceptionResultFormat(ProjectConstants.ErrorCode.UNKNOW,e);
   }
    /**
     * 异常信息统一返回
     */
    private <T extends Throwable> Dto exceptionResultFormat(String code,T exception){
        exception.printStackTrace();
        logger.error(String.format(logExceptionFormat, code,exception.getMessage()));
        return DtoUtils.returnFail(exception.getMessage(),code);
    }
}
