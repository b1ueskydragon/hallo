package tools;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailSendUtility {
  /**
   * ユーザを認証し、メールを送信する
   *
   * @param subject 送信するメールのタイトル
   * @param content 送信するメールの本文
   * @param toUser  受信者のメールアドレス { MailSendProperties.TO }
   */
  public static void send(String subject, String content, String toUser) {

    final String to = toUser;
    final String from = MailSendProperties.FROM;

    // Google account mail address
    final String username = MailSendProperties.USER_NAME;
    // Google App password
    final String password = MailSendProperties.PASSWORD;

    //final String charset = "ISO-2022-JP";
    final String charset = "UTF-8";

    final String encoding = "base64";

    // for gmail
    String host = "smtp.gmail.com";
    String port = "587"; // stmp (送信サーバ) のポート
    String starttls = "true";

    // for local
    //String host = "localhost";
    //String port = "2525";
    //String starttls = "false";

    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", starttls);

    props.put("mail.smtp.connectiontimeout", "10000");
    props.put("mail.smtp.timeout", "10000");

    props.put("mail.debug", "true");

    Session session = Session.getInstance(props,
        new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    try {
      MimeMessage message = new MimeMessage(session);

      // Set From:
      message.setFrom(new InternetAddress(from, MailSendProperties.PERSONAL));
      // Set ReplyTo:
      message.setReplyTo(new Address[]{new InternetAddress(from)});
      // Set To:
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

      message.setSubject(subject, charset);
      message.setText(content, charset);

      message.setHeader("Content-Transfer-Encoding", encoding);

      Transport.send(message);

    } catch (MessagingException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
