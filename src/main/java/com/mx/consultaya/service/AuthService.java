package com.mx.consultaya.service;

import java.io.UnsupportedEncodingException;

import com.mx.consultaya.exception.CustomException;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.model.Usuario;

import jakarta.mail.MessagingException;

public interface AuthService {

    /**
     * Da acceso al usuario para loggearse
     * @param encryptedData los datos de acceso 
     *        para loggearse
     * @throws CustomException si se produce un error 
     */
    public void signIn(EncryptedData encryptedData) throws CustomException;

    /**
     * Registra un nuevo usuario en la base de datos a partir de los datos
     * encriptados recibidos y envía un correo electrónico
     * de verificación de cuenta al usuario registrado.
     *
     * @param encryptedData los datos del usuario encriptados en formato JSON.
     * @throws CustomException si ocurre algún error al descifrar los datos, al
     *                         registrar el usuario o al enviar el correo de
     *                         verificación.
     */
    public void signUp(EncryptedData encryptedData) throws CustomException;

    /**
     * 
     * Envía un correo electrónico de verificación al usuario después de
     * registrarse.
     * 
     * @param user el objeto Usuario que contiene la información del usuario
     *             registrado
     * @throws MessagingException           si ocurre un error al enviar el correo
     *                                      electrónico
     * @throws UnsupportedEncodingException si se produce un error de codificación
     *                                      de caracteres
     */
    public void sendMailVerifyAccount(Usuario user) throws MessagingException, UnsupportedEncodingException;

    /**
     * Verifica si el correo electrónico del usuario está registrado en la base de
     * datos.
     * 
     * @param jwt Token JWT codificado del usuario.
     * @throws CustomException Si el correo electrónico del usuario no está
     *                         registrado en la base de datos.
     */
    public void verifyMail(String jwt) throws CustomException;
    
    public void saveToken(Usuario user);

    public void refreshToken(String id) throws CustomException, UnsupportedEncodingException, MessagingException;
}
