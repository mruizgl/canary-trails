package es.iespuertodelacruz.mp.canarytrails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${mail.from}") private String mailfrom;

    public void send(String destinatarios[], String asunto, String  contenido){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailfrom);
        message.setTo(destinatarios);
        message.setSubject(asunto);
        message.setText(contenido);

        sender.send(message);

    }

}
