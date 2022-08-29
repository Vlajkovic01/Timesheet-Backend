package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.employee.request.EmployeeAddRequestDTO;
import com.example.timesheet.security.TokenUtils;
import com.example.timesheet.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String SENT_FROM = "contact.timesheet@gmail.com";
    private static final String VERIFY_API_URL = "http://localhost:3000/verify?token="; //this is the frontend page where the user will enter the new pass
    private static final String SUBJECT_VERIFICATION = "Verify account";

    private final TokenUtils tokenUtils;
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender, TokenUtils tokenUtils) {
        this.mailSender = mailSender;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(SENT_FROM);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    @Override
    public void sendVerificationEmail(EmployeeAddRequestDTO employeeDTO) {
        SimpleMailMessage message = new SimpleMailMessage();

        String text = "Please check the link bellow and create a password within the next 60 minutes.Thanks!\n" +
                "Link: " + VERIFY_API_URL + tokenUtils.generateToken(employeeDTO.getEmail(), "ROLE_" + employeeDTO.getRole().toString());

        message.setFrom(SENT_FROM);
        message.setTo(employeeDTO.getEmail());
        message.setSubject(SUBJECT_VERIFICATION);
        message.setText(text);

        mailSender.send(message);
    }
}
