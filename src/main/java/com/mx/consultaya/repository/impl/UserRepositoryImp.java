package com.mx.consultaya.repository.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepositoryImp implements UserRepository {

    private MongoTemplate mongoTemplate;
    private JavaMailSender mailSender;
    private static final String CORREO_ELECTRONICO = "correoElectronico";

    @Override
    public Usuario saveUsuario(Usuario user) {
        mongoTemplate.insert(user);
        return user;
    }

    @Override
    public Boolean findByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(email));
        Usuario user = mongoTemplate.findOne(query, Usuario.class);
        if (user != null)
            log.info("ID {}", user.getId());
        return user != null;
    }

    @Override
    public void enviarCorreoVerificacion(Usuario user) throws UnsupportedEncodingException, MessagingException {
        String siteURL = "http://localhost:3000/auth";
        String toAddress = user.getCorreoElectronico();
        String fromAddress = "consultayaadmin@consulta-ya.com.mx";
        String senderName = "Consulta Ya";
        String subject = "Por favor verifique su registro";
        String content = "Estimad@ [[name]],<br>"
                + "Por favor de clic en el link de abajo para verificar su registro:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFICAR</a></h3>"
                + "¡Gracias!,<br>"
                + "Consulta Ya.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String fullname = user.getNombre() + " " + user.getApellidos();

        content = content.replace("[[name]]", fullname);
        String verifyURL = siteURL + "/verification?code=" + user.getCodigoVerificacion();

        content = content.replace("[[URL]]", verifyURL);

        try {
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

    @Override
    public Boolean actualizarVerificacion(String email) {

        Query query = new Query(Criteria.where(CORREO_ELECTRONICO).is(email));
        Update update = new Update();
        update.set("verificado", true);
        update.set("codigoVerificacion", " ");

        return mongoTemplate.updateMulti(query, update, Usuario.class).wasAcknowledged();

    }
}
