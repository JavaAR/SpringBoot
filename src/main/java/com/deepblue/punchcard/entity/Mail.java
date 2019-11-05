package com.deepblue.punchcard.entity;

import lombok.Data;

import java.util.Map;

/**
 * 邮件发送实体类
 */
@Data
public class Mail {
    //发给谁
    private String[] sendTo;

    //抄送
    private String[] cc;

    //邮件标题
    private String subject;

    //邮件内容
    private String text;

    //邮件模板内容
    private Map<String,String> templateModel;

    //模板内容
    private String templateName;
}
