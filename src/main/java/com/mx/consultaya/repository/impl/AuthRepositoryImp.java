package com.mx.consultaya.repository.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.mx.consultaya.exception.RequestException;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.AuthRepository;
import com.mx.consultaya.utils.EnumSeverity;
import com.mx.consultaya.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class AuthRepositoryImp implements AuthRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String CORREO_ELECTRONICO = "correoElectronico";
    private static final String VERIFICADO= "verificado";
    private static final String CODIGO_VERIFICACION = "codigoVerificacion";

    /**
     * Busca un usuario en la base de datos con el correo electrónico y contraseña
     * especificados y devuelve el objeto Usuario resultante.
     *
     * @param email    el correo electrónico del usuario que se va a buscar.
     * @param password la contraseña del usuario que se va a buscar.
     * @return el objeto Usuario que representa al usuario buscado o null si no se
     *         encuentra ningún usuario con el correo electrónico especificado.
     */
    @Override
    public Usuario login(String email, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(email));
        return mongoTemplate.findOne(query, Usuario.class);
    }

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
    @Override
    public Usuario join(Usuario user) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(user.getCorreoElectronico().toLowerCase()));
        if (mongoTemplate.exists(query, Usuario.class)) {
            log.info("Usuario existente");
            throw new RequestException(HttpStatus.CONFLICT, "Alerta", EnumSeverity.WARNING,
                    "El correo proporcionado ya ha sido dado de alta anteriormente");
        }
        log.info("Creando usuario");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        user.setRol("Paciente");
        user.setCodigoVerificacion(" ");
        user.setVerificado(false);
        user.setCreadoEn(new Date());
        return mongoTemplate.save(user);
    }

    /**
     * Verifica que el correo electrónico proporcionado esté registrado en la base
     * de datos y actualiza el campo de
     * verificación de ese usuario a true.
     *
     * @param email el correo electrónico que se desea verificar.
     * @throws RequestException si el correo electrónico proporcionado no existe en
     *                          la base de datos.
     */
    @Override
    public void checkEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(email.toLowerCase()));
        if (!mongoTemplate.exists(query, Usuario.class)) {
            throw new RequestException(HttpStatus.CONFLICT, "No existe", EnumSeverity.ERROR,
                    "El correo proporcionado no existe");
        }
        Update update = new Update();
        update.set(VERIFICADO, true);
        update.set(CODIGO_VERIFICACION, " ");

        mongoTemplate.updateMulti(query, update, Usuario.class);
    }
    /**
     * Actualiza el token del usuario,si ya expiroy lo guarda en BD
     *
     * @param id el identificador del usuario.
     * @param token el nuevo token generado
     * @return un Usuario con los datos actualizados
     */
    @Override
    public Usuario actToken(String id,String token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        if (!mongoTemplate.exists(query, Usuario.class)) {
            throw new RequestException(HttpStatus.CONFLICT, "No existe.", EnumSeverity.ERROR,
                    "El correo proporcionado para actualizar token no existe");
        }
        Update update = new Update();
        update.set(CODIGO_VERIFICACION, token);
        
       return mongoTemplate.findAndModify(
        query, update, new FindAndModifyOptions().returnNew(true), Usuario.class);
    }

    /**
     * Guarda el token generado en BD
     *
     * @param user Usuario al que se le genero el token
     * @return Usuario con datos actualizados
     */
    @Override
    public  Usuario saveToken(Usuario user) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(user.getCorreoElectronico().toLowerCase()));
        if (!mongoTemplate.exists(query, Usuario.class)) {
            throw new RequestException(HttpStatus.CONFLICT, "No existe.", EnumSeverity.ERROR,
                    "El correo proporcionado no existe");
        }
        Update update = new Update();
        update.set(CODIGO_VERIFICACION, Utils.verifyToken(user));
        return mongoTemplate.findAndModify(
            query, update, new FindAndModifyOptions().returnNew(true), Usuario.class);
    }
    /**
     * Busca un usuario en BD mediante su id
     *
     * @param id el identificador del usuario
     * @return Usuario que se busco en BD
     */
    @Override
    public Usuario getUserById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Usuario.class);
    }
}
