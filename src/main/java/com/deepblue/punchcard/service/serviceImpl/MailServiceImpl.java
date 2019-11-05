package com.deepblue.punchcard.service.serviceImpl;

import com.deepblue.punchcard.constant.ProjectConstants;
import com.deepblue.punchcard.entity.Mail;
import com.deepblue.punchcard.service.MailService;
import com.deepblue.punchcard.utils.UploadActionUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 邮件服务接口实现类
 */
@Service
public class MailServiceImpl implements MailService {

    private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);


    @Resource
    @Qualifier("javaMailSender")
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private FreeMarkerConfig freeMarkerConfig;

    /**
     * 简单邮件发送
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendSimpleMail(Mail mail) throws Exception {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getSendTo());
        simpleMailMessage.setFrom(this.from);
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getText());
        mailSender.send(simpleMailMessage);
    }

    /**
     * 附件发送
     * @param mail
     * @param request
     * @throws Exception
     */
    @Override
    public void sendAttachmentsMail(Mail mail, HttpServletRequest request) throws Exception {
        //组织发送的对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(mail.getSendTo());
        mimeMessageHelper.setText(mail.getText());
        mimeMessageHelper.setSubject(mail.getSubject());
        //获取附件路径
        List<String> filePaths = UploadActionUtil.uploadFiled(request);
        for (int i = 0; i < filePaths.size(); i++) {
            String fileName = filePaths.get(i);
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            FileSystemResource systemResource = new FileSystemResource(new File(fileName));
            mimeMessageHelper.addAttachment("附件"+(i+1)+fileName,systemResource);
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 图片发送
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendInlineMail(Mail mail,HttpServletRequest request) throws Exception {
        String uid = UUID.randomUUID().toString();
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);
        helper.setTo(mail.getSendTo());
        helper.setSubject(mail.getSubject());
        helper.setFrom(from);
        helper.setText("<html><body><img src=\"cid:"+uid +"\" ></body></html>",true);
        List<String> strings = UploadActionUtil.uploadFiled(request);
        for (int i = 0; i < strings.size(); i++) {
            String fileName = strings.get(i);
            FileSystemResource systemResource = new FileSystemResource(new File(fileName));
            helper.addInline(uid,systemResource);
        }
        mailSender.send(mailMessage);
    }

    /**
     * 发送模板邮件
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendTemplateMail(Mail mail) throws Exception {
        MimeMessage mimeMessage =  mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(mail.getSubject());
        mimeMessageHelper.setTo(mail.getSendTo());
        //读取模板
        Configuration configuration = getConfiguration();
        Template template = configuration.getTemplate(mail.getTemplateName() + ".ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getTemplateModel());
        mimeMessageHelper.setText(html,true);
        mailSender.send(mimeMessage);
    }

    private Configuration getConfiguration() throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        try {
            configuration.setDirectoryForTemplateLoading(new File(ProjectConstants.MailConstant.TMPLATE_PATH));
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IOException("文件未找到");
        }
        return configuration;
    }

}
