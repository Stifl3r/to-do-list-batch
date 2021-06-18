package za.co.learnings.todolistbatch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Component
public class EmailService {

   @Value("${spring.mail.emailFrom}")
   private String EMAIL_FROM;
   @Value("${spring.mail.password}")
   private String PASSWORD;
   @Value("${spring.mail.host}")
   private String HOST;

   public boolean sendEmail(String address, String subject, String body, String fileAttachment) {
      var success = true;
   
      //Don't send email if the address is set to none
      if (address != null && address.toLowerCase().trim().equals("none")) {
         return success;
      }
      try {

         File attachment = null;

         if (fileAttachment != null) {
            if (!fileAttachment.isEmpty()) {
               attachment = new File(fileAttachment);
            }
         }
         var message = prepareEmailPayload(HOST, address, subject, body, attachment);

         // Send the message
         Transport.send(message);
      } catch (Exception e) {
         e.printStackTrace();
         success = false;
      }
      return success;
   }

   public boolean sendEmail(String address, String subject, String body, File fileAttachment) {
      boolean success = true;

      //Don't send email if the address is set to none
      if (address != null && address.toLowerCase().trim().equals("none")) {
         return success;
      }

      try {
         var message = prepareEmailPayload(HOST, address, subject, body, fileAttachment);

         // Send the message
         Transport.send(message);
      } catch (Exception e) {
         e.printStackTrace();
         success = false;
      }
      return success;
   }

   private MimeMessage prepareEmailPayload(String smtp, String address, String subject, String body, File fileAttachment) throws MessagingException, IOException {

      var props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", smtp);
      props.put("mail.smtp.port", "25");

      var session = Session.getInstance(props, new Authenticator() {
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
         }
      });
      var message = new MimeMessage(session);
      message.setFrom(new InternetAddress(EMAIL_FROM));
      var theAddresses = InternetAddress.parse(address);
      message.addRecipients(Message.RecipientType.TO, theAddresses);
      message.setSubject(subject);

      var messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(body);

      var multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      if (fileAttachment != null) {
         var attBodyPart = new MimeBodyPart();
         attBodyPart.attachFile(fileAttachment);
         multipart.addBodyPart(attBodyPart);
      }

      // Put parts in message
      message.setContent(multipart);
      return message;
   }
}
