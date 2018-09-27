package com.flyme.mail.service;

import com.flyme.mail.entity.MailBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
@Slf4j
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送简单的文本邮件
     * @param mailBean
     */
    public void sendSimpleMail(MailBean mailBean){
        log.info("发送邮件开始。相关参数:{}",mailBean.toString());
        try{
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setTo(mailBean.getTo());
            simpleMailMessage.setSubject(mailBean.getSubject());
            simpleMailMessage.setText(mailBean.getContent());
            simpleMailMessage.setFrom(from);
            javaMailSender.send(simpleMailMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
           log.error("发送邮件失败:{}",e.getMessage());
        }
    }

    /**
     * 发送HTML格式文件
     * @param mailBean
     * @throws MessagingException
     */
    public void  sendHtmlMessage(MailBean mailBean) {
        log.info("发送邮件开始。相关参数:{}",mailBean.toString());
        try{
            MimeMessage mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(mailBean.getTo());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent(),true);
            mimeMessageHelper.setFrom(from);
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
            log.error("发送邮件失败:{}",e.getMessage());
        }
    }

    /**
     *发送带有附件的邮件
     * @param mailBean
     */
    public void sendAttachMentMessage(MailBean mailBean){
        log.info("发送邮件开始。相关参数:{}",mailBean.toString());
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mailBean.getTo());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent(),true);
            mimeMessageHelper.setFrom(from);
            //准备附件相关
            List<String> paths=mailBean.getAttachmentPath();
            for (String path:paths) {
                FileSystemResource file= new FileSystemResource(new File(path));
                String fileName = file.getFilename();
                mimeMessageHelper.addAttachment(fileName,file);
            }
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
            log.error("发送邮件失败:{}",e.getMessage());
        }
    }

    /**
     * 发送带有图片的邮件
     * @param mailBean
     */
    public void sendImageMessage(MailBean mailBean){
        log.info("发送邮件开始。相关参数:{}",mailBean.toString());
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mailBean.getTo());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent(),true);
            mimeMessageHelper.setFrom(from);
            //准备图片附件相关
            FileSystemResource file= new FileSystemResource(new File(mailBean.getImagePath()));
            mimeMessageHelper.addInline(mailBean.getRscId(),file);
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
            log.error("发送邮件失败:{}",e.getMessage());
        }
    }

}
