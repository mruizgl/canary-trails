package es.iespuertodelacruz.mp.canarytrails.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

    @InjectMocks
    private MailService mailService;

    @Mock
    private JavaMailSender sender;

    private final String remitente = "noreply@canarytrails.com";

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(mailService, "mailfrom", remitente);
    }

    @Test
    public void sendEmail_EnvioCorrectoTest() {
        String[] destinatarios = {"usuario@correo.com"};
        String asunto = "Asunto de prueba";
        String contenido = "Este es el contenido del correo.";

        mailService.send(destinatarios, asunto, contenido);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender, times(1)).send(captor.capture());

        SimpleMailMessage enviado = captor.getValue();
        assertArrayEquals(destinatarios, enviado.getTo());
        assertEquals(asunto, enviado.getSubject());
        assertEquals(contenido, enviado.getText());
        assertEquals(remitente, enviado.getFrom());
    }
}
