package com.mx.consultaya.repository;

import org.springframework.stereotype.Repository;

import com.mx.consultaya.model.Usuario;

@Repository
public interface AuthRepository {

    /**
     * Busca un usuario en la base de datos con el correo electrónico y contraseña
     * especificados y devuelve el objeto Usuario resultante.
     *
     * @param email    el correo electrónico del usuario que se va a buscar.
     * @param password la contraseña del usuario que se va a buscar.
     * @return el objeto Usuario que representa al usuario buscado o null si no se
     *         encuentra ningún usuario con el correo electrónico especificado.
     */
    public Usuario login(String email, String password);

    /**
     * Registra un nuevo usuario en la base de datos y devuelve el objeto Usuario
     * resultante.
     *
     * @param user el objeto Usuario que representa al usuario que se va a
     *             registrar.
     * @return el objeto Usuario que representa al usuario registrado.
     * @throws RequestException si el correo electrónico proporcionado ya ha sido
     *                          dado de alta anteriormente.
     */
    public Usuario join(Usuario usuario);

    /**
     * Verifica que el correo electrónico proporcionado esté registrado en la base
     * de datos y actualiza el campo de
     * verificación de ese usuario a true.
     *
     * @param email el correo electrónico que se desea verificar.
     * @throws RequestException si el correo electrónico proporcionado no existe en
     *                          la base de datos.
     */
    public void checkEmail(String email);
}
