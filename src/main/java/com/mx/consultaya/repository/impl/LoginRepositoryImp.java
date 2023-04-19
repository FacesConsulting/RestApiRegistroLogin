package com.mx.consultaya.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.LoginRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class LoginRepositoryImp implements LoginRepository {
	private MongoOperations mongoOperations;

	@Override
	public List<Usuario> findAll() {
		return this.mongoOperations.find(new Query(), Usuario.class);
	}

	@Override
	public Usuario login(String email, String password) {
		Query orQuery = new Query();
		Criteria orCriteria = new Criteria();
		List<Criteria> orExpression = new ArrayList<>();
		Criteria emailCriteria = new Criteria();
		emailCriteria.and("email");
		emailCriteria.is(email);
		Criteria passwordCriteria = new Criteria();
		passwordCriteria.and("password");
		passwordCriteria.is(password);
		orExpression.add(emailCriteria);
		orExpression.add(passwordCriteria);
		orQuery.addCriteria(orCriteria.orOperator(orExpression.toArray(new Criteria[orExpression.size()])));

		return mongoOperations.find(orQuery, Usuario.class).stream().findAny().get();
	}

	@Override
	public Usuario saveLogin(Usuario loginInput) {
		return this.mongoOperations.save(loginInput);
	}
}
