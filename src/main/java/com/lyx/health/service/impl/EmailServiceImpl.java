package com.lyx.health.service.impl;

import com.lyx.health.service.EmailService;
import com.lyx.health.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * @author Steven0516
 * @create 2021-10-23
 */

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSenderImpl mailSender;


    @Override
    public String sendMail(String userEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = CodeUtil.generateVerifyCode(6);
        message.setSubject("【Lemon-Heart】");
        message.setText("邮箱验证码为：" + code +"。欢迎注册柠檬心里平台，祝您在柠檬心里有个愉快的体验~");
        message.setFrom("1569132595@qq.com");
        message.setTo(userEmail);
        mailSender.send(message);
        return code;
    }
}
