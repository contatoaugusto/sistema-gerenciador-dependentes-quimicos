package br.com.sgdq.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.sgdq.app.entity.Prontuario;

@Repository
public class ProntuarioRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public void incluir(Prontuario prontuario) {
		entityManager.persist(prontuario);
	}
	
	@SuppressWarnings("unchecked")
	public List<Prontuario> consultarPorNome(String nome) {
		return entityManager.createNamedQuery("prontuario.consultarPorNome").setParameter("nome", "%" + nome + "%").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Prontuario> consultarPorId(int id) {
		return entityManager.createNamedQuery("prontuario.consultarPorId").setParameter("id", id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Prontuario> consultarPorCpfDoPaciente(String cpf) {
		return entityManager.createNamedQuery("prontuario.consultarPorCpfDoPaciente").setParameter("cpf", cpf).getResultList();
	}
	
}
