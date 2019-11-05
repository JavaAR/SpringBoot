package com.deepblue.punchcard.service;

import com.deepblue.punchcard.entity.Mail;

import javax.servlet.http.HttpServletRequest;

/**
 * 邮件发送业务
 */
public interface MailService {

    //发送简单的邮件
    void sendSimpleMail(Mail mail) throws Exception;

    //发送待附件的邮件
    void sendAttachmentsMail(Mail mail, HttpServletRequest request) throws Exception;

    //发送静态资源 一张照片
    void sendInlineMail(Mail mail,HttpServletRequest request)throws Exception;

    //发送模板邮件
    void sendTemplateMail(Mail mail) throws Exception;



}
