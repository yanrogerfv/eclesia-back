package imdl.eclesia.service.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class AppMailSender implements MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = SimpleMailMessageBuilder
            .from(fromAddress).to(to).subject(subject).text(text).build();
        mailSender.send(message);
    }

    public void passwordResetEmail(String to, String resetLink) {
        String subject = "Recuperação de Senha";
        String text = "Para resetar sua senha, clique no link abaixo:\n" + resetLink +
                      "\n\nSe você não solicitou a recuperação de senha, por favor ignore este email.";
        sendSimpleMessage(to, subject, text);
    }

    public void welcomeEmail(String to, String userName) {
        String subject = "Eclesia te dá boas vindas!";
        String text = "Olá " + userName + ",\n\nSeja Bem-vindo ao Eclesia! Estamos felizes em tê-lo conosco!";
        sendSimpleMessage(to, subject, text);
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        MailSender.super.send(simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for (SimpleMailMessage message : simpleMessages) {
            mailSender.send(message);
        }
    }
}
