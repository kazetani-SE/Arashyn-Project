package com.arashi.edu.arashynbe.features.email.service.impl;

import com.arashi.edu.arashynbe.config.properties.MailProperties;
import com.arashi.edu.arashynbe.features.email.dto.request.SendSingleMailRequest;
import com.arashi.edu.arashynbe.features.email.service.EmailService;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;
  private final MailProperties mailProperties;

  @Override
  @Async
  public void sendSingleMail(SendSingleMailRequest sendSingleMailRequest) {
    Context context = new Context();

    context.setVariable("subject", sendSingleMailRequest.subject());
    context.setVariable("body", sendSingleMailRequest.body());

    sendHtmlEmail(
            sendSingleMailRequest.toEmail(),
            sendSingleMailRequest.subject(),
            "normal-mail",
            context
    );
  }

  @Override
  @Async
  public void sendVerificationEmail(String to, String otpCode) {
    Context context = new Context();
    context.setVariable("otpCode", otpCode);

    sendHtmlEmail(
            to,
            "Account Verification Email",
            "verify-account",
            context
    );
  }

  private void sendHtmlEmail(
          String to,
          String subject,
          String templateName,
          Context context
  ) {
    String htmlContent;

    try {
      htmlContent = templateEngine.process(templateName, context);
    } catch (Exception exception) {
      log.error("Failed to process email template for {}", to, exception);
      throw new ApiException(ErrorCode.EMAIL_TEMPLATE_PROCESSING_FAILED);
    }

    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(
              message,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name()
      );

      helper.setFrom(
              mailProperties.getUsername(),
              mailProperties.getUsername()
      );
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlContent, true);

    } catch (MessagingException | UnsupportedEncodingException exception) {
      log.error("Failed to build verification email to {}", to, exception);
      throw new ApiException(ErrorCode.EMAIL_TEMPLATE_PROCESSING_FAILED);
    }

    try {
      mailSender.send(message);
    } catch (MailException exception) {
      log.error("Failed to send verification email to {}", to, exception);
      throw new ApiException(ErrorCode.VERIFICATION_EMAIL_SEND_FAILED);
    }
  }
}