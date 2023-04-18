package com.mx.conectasalud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mx.conectasalud.model.Clinica;

@Repository
public interface ClinicaRepository extends MongoRepository<Clinica, String> {

	public long count();
}
