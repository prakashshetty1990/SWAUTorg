package Utilities;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class Mailing
{
  public Mailing() {}
  
  public static String[] getArrayOfEmails(String keyword)
  {
    String configProperty = GenericKeywords.getConfigProperty(keyword);
    if (configProperty.length() != 0)
    {
      String[] arr = configProperty.split(";");
      GenericKeywords.writeToLogFile("INFO", "Splitting email addresses : " + keyword);
      for (int i = 0; i < arr.length; i++)
        GenericKeywords.writeToLogFile("INFO", "Email :" + (i + 1) + " \"" + arr[i] + "\"");
      return arr;
    }
    
    GenericKeywords.writeToLogFile("INFO", "No email addresses found for : " + keyword);
    return null;
  }
  

  public static void sendMail()
  {
    String host = GenericKeywords.getConfigProperty("Smtp HostName");
    String user = GenericKeywords.getConfigProperty("SenderMailId");
    String password = "Indium@123";
    String senderName = "iSafe";
    String suiteName = GenericKeywords.getConfigProperty("SuiteName");
    String[] to = getArrayOfEmails("ToEmail");
    String[] cc = getArrayOfEmails("CcEmail");
    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.auth", "true");
    
    Session session = Session.getDefaultInstance(props, 
      new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
			return null;
         // return new PasswordAuthentication(Mailing.this, "Indium@123");
        }
      });
    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress("iSafe<" + user + ">"));
      
      for (int i = 0; i < to.length; i++)
      {
       // message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
      }
      for (int i = 0; i < cc.length; i++)
      {
       // message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
      }
      int totalExecuted = GenericKeywords.testFailureCount + GenericKeywords.testSkippedCount + GenericKeywords.testSuccessCount;
      
      message.setSubject(suiteName + " Test | " + GenericKeywords.testSuccessCount * 100 / totalExecuted + "% Pass | " + GenericKeywords.timeStamp);
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(suiteName.toUpperCase() + " TEST REPORT" + '\n' + '\n' + '\n' + GenericKeywords.testSuccessCount * 100 / totalExecuted + "% Passed" + '\n' + GenericKeywords.testFailureCount * 100 / totalExecuted + "% Failed" + '\n' + '\n' + "Total Executed-" + (GenericKeywords.testFailureCount + GenericKeywords.testSkippedCount + GenericKeywords.testSuccessCount) + '\n' + "Total Passed-" + GenericKeywords.testSuccessCount + '\n' + "Total Failed-" + GenericKeywords.testFailureCount + '\n' + "Total Skipped-" + GenericKeywords.testSkippedCount);
      
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      messageBodyPart = new MimeBodyPart();
      String filename = GenericKeywords.timeStamp + ".zip";
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(filename);
      multipart.addBodyPart(messageBodyPart);
      message.setContent(multipart);
      GenericKeywords.writeToLogFile("INFO", "<<<<<<<<<<<<<Please Wait>>>>>>>>>>>>>>>>>");
      Transport.send(message);
      GenericKeywords.writeToLogFile("INFO", "Mail sent successfully...");
    } catch (MessagingException e) { e.printStackTrace();
    }
  }
}
