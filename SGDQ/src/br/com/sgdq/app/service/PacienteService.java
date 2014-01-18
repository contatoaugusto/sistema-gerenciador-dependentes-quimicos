package br.com.sgdq.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgdq.app.entity.Paciente;
import br.com.sgdq.app.repository.PacienteRepository;

@Service
@Transactional
public class PacienteService {

	@Autowired
	PacienteRepository pacienteRepository;
	
	public void incluir(Paciente paciente) {
		pacienteRepository.incluir(paciente);
	}
	
}
