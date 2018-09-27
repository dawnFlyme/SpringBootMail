package com.flyme.mail.service;

import com.flyme.mail.entity.MailBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    private String [] to={"xxxxxx@qq.com","xxxxx@163.com"};


    @Resource
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;  //模板引擎

    @Test
    public void sendSimpleMail() {
        MailBean mailBean=new MailBean();
        mailBean.setTo(to);
        mailBean.setSubject("Test");
        mailBean.setContent("阿西吧");
        mailService.sendSimpleMail(mailBean);
    }

    @Test
    public void sendHtmlMessage(){
        MailBean mailBean=new MailBean();
        mailBean.setTo(to);
        mailBean.setSubject("TestHtml");
        String content="<html>\n"+
                        "<body>\n"+
                          "<h3>This is Html Message.</h3>\n"+
                        "</body>\n"+
                        "</html>\n";
        mailBean.setContent(content);
        mailService.sendHtmlMessage(mailBean);
    }

    @Test
    public void sendAttachMentMessage() {
        MailBean mailBean=new MailBean();
        mailBean.setTo(to);
        String content="<html>\n"+
                "<body>\n"+
                "<h3>This is Attachment Message.</h3>\n"+
                "</body>\n"+
                "</html>\n";
        mailBean.setContent(content);
        mailBean.setSubject("TestAttachMent");
        List<String> paths=new ArrayList<>();
        paths.add("C:\\Users\\Flyme\\Desktop\\log1.txt");
        mailBean.setAttachmentPath(paths);
        mailService.sendAttachMentMessage(mailBean);
    }

    @Test
    public void sendImageMessage() {
        MailBean mailBean=new MailBean();
        mailBean.setTo(to);
        String rscId="imgae1";
        mailBean.setRscId(rscId);
        String content="<html>\n"+
                "<body>\n"+
                "<h3>This is Image Message.</h3>\n"+
                "<img src=\'cid:"+rscId+"\'>"+
                "</body>\n"+
                "</html>\n";
        mailBean.setContent(content);
        mailBean.setSubject("TestImage");
        mailBean.setImagePath("G:\\漫威\\1.jpg");
        mailService.sendImageMessage(mailBean);
    }

    @Test
    public void sendTemplateHtmlMessage(){
        MailBean mailBean=new MailBean();
        Context context=new Context();
        context.setVariable("id","1001");
        //使用指定的模板
        String content =templateEngine.process("register",context);
        mailBean.setTo(to);
        mailBean.setSubject("TestTemplateHtml");
        mailBean.setContent(content);
        mailService.sendHtmlMessage(mailBean);
    }
}