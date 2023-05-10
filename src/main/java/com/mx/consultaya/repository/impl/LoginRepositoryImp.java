package com.mx.consultaya.repository.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.LoginRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class LoginRepositoryImp implements LoginRepository {
	private MongoTemplate mongoTemplate;
	private static final String CORREO_ELECTRONICO = "correoElectronico";

	@Override
	public Usuario findByEmail(String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(email.toLowerCase()));
		return mongoTemplate.findOne(query, Usuario.class);
	}

	@Override
	public List<Usuario> findAll() {
		return this.mongoTemplate.find(new Query(), Usuario.class);
	}

	@Override
	public Usuario login(String email, String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(email));
		return mongoTemplate.findOne(query, Usuario.class);
	}

	@Override
	public boolean existUserByEmail(String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where(CORREO_ELECTRONICO).is(email));
		return mongoTemplate.exists(query, Usuario.class);
	}

}
