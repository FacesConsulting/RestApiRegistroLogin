package com.mx.consultaya.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mx.consultaya.exception.CustomException;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.model.JWTGenerator;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.AuthRepository;
import com.mx.consultaya.service.AuthService;
import com.mx.consultaya.utils.Utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImp implements AuthService {

    private JavaMailSender mailSender;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public void signIn(EncryptedData encryptedData) throws CustomException {

        String data = encryptedData.getData();
        String key = encryptedData.getKey();
        String iv = encryptedData.getIv();

        String dataDecrypt = Utils.decryptData(data, key, iv);

        Gson g = new Gson();

        Usuario us = g.fromJson(dataDecrypt, Usuario.class);

        Usuario userLogin = authRepository.login(us.getCorreoElectronico().toLowerCase(), us.getPassword());

        if (userLogin == null) {
            throw new CustomException("Usuario no existente", null);
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        final boolean isPasswordMatches = bcrypt.matches(us.getPassword(), userLogin.getPassword());

        if (isPasswordMatches) { // correct password
            if (Boolean.FALSE.equals(us.getVerificado())) {
                throw new CustomException("Usuario no verificado", null);
            }
        } else {
            throw new CustomException("Credenciales incorrectas", null);
        }
    }

    @Override
    public void signUp(EncryptedData encryptedData) throws CustomException {

        String data = encryptedData.getData();
        String key = encryptedData.getKey();
        String iv = encryptedData.getIv();

        String dataDecrypt = Utils.decryptData(data, key, iv);

        Gson g = new Gson();

        Usuario user = g.fromJson(dataDecrypt, Usuario.class);

        Usuario createdUser = authRepository.join(user);

        try {
            Usuario us2 = authRepository.saveToken(createdUser);
            sendMailVerifyAccount(us2);

        } catch (UnsupportedEncodingException | MessagingException e) {

            e.printStackTrace();
        }
    }

    public void sendMailVerifyAccount(Usuario user) throws MessagingException, UnsupportedEncodingException {
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

        String verifyURL = siteURL + "/verification?code=" + user.getCodigoVerificacion() + "&id=" + user.getId();

        content = content.replace("[[URL]]", verifyURL);

        try {
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

    @Override
    public void verifyMail(String jwt) throws CustomException {
        Gson g = new Gson();
        JsonObject jwtString = g.fromJson(jwt, JsonObject.class);
        String token = jwtString.get("jwt").getAsString();
        DecodedJWT decoder = new JWTGenerator().decriptJWT(token);
        String email = decoder.getClaim("correoElectronico").asString();
        authRepository.checkEmail(email);
    }

    @Override
    public void refreshToken(String id) throws CustomException, UnsupportedEncodingException, MessagingException {
        Gson g = new Gson();
        JsonObject jwtString = g.fromJson(id, JsonObject.class);
        String idUser = jwtString.get("id").getAsString();
        log.info("id_user {}",idUser);
        
        Usuario user = authRepository.getUserById(idUser);
        log.info("user{}", user);
        String newToken = new JWTGenerator().refreshJWT(idUser, user.getCorreoElectronico());
        Usuario userNewToken = authRepository.actToken(idUser, newToken);
        log.info("newToken"+newToken);
        sendMailVerifyAccount(userNewToken);
    }

    @Override
    public void saveToken(Usuario user) {
        authRepository.saveToken(user);
    }
    
    
}
