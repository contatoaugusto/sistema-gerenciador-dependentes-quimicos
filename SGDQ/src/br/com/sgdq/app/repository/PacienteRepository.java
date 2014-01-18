package br.com.sgdq.app.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.sgdq.app.entity.Paciente;

@Repository
public class PacienteRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public void incluir(Paciente paciente) {
		entityManager.persist(paciente);
	}
	
}
