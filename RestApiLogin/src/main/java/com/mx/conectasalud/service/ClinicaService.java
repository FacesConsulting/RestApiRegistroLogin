package com.mx.conectasalud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.mx.conectasalud.repository.ClinicaRepository;

@Service
@EnableMongoRepositories
public class ClinicaService {

	@Autowired
	private ClinicaRepository clinicaRepository;
}
