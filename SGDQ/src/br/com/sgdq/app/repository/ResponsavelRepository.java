package br.com.sgdq.app.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.sgdq.app.entity.Responsavel;

@Repository
public class ResponsavelRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public void incluir(Responsavel responsavel) {
		entityManager.persist(responsavel);
	}
	
}
