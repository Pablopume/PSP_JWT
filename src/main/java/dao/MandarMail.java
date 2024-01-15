package dao;

import configuration.Configuration;
import dao.impl.DaoConstantes;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;


/**
 *
 * @author oscar
 */
@Log4j2
public class MandarMail {


    private final Configuration config;
    @Inject
    public MandarMail(Configuration config) {
        this.config = config;
    }

    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        // Step1

        mailServerProperties = System.getProperties();
        mailServerProperties.put(DaoConstantes.MAIL_SMTP_PORT, Integer.parseInt(DaoConstantes.NUMBER));
        mailServerProperties.put(DaoConstantes.MAIL_SMTP_AUTH, DaoConstantes.TRUE);
        mailServerProperties.put(DaoConstantes.MAIL_SMTP_SSL_TRUST, DaoConstantes.SMTP_GMAIL_COM);
        mailServerProperties.put(DaoConstantes.MAIL_SMTP_STARTTLS_ENABLE, DaoConstantes.TRUE);

        // Step2

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(msg, DaoConstantes.TEXT_HTML);


        // Step3

        Transport transport = getMailSession.getTransport(DaoConstantes.SMTP);

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(DaoConstantes.SMTP_GMAIL_COM,
                config.getProperty(DaoConstantes.MAIL),
                config.getProperty(DaoConstantes.PASSWORD_MAIL) );

        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
