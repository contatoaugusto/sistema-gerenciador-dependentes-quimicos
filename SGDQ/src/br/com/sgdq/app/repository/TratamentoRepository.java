package br.com.sgdq.app.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.sgdq.app.entity.Tratamento;

@Repository
public class TratamentoRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public void incluir(Tratamento tratamento) {
		entityManager.persist(tratamento);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tratamento> consultarQuantidadeDeTratamentosIniciadosPorMes(Date dataInicial, Date dataFinal) {
		return entityManager.createNamedQuery("tratamento.consultarQuantidadeDeTratamentosIniciadosPorMes").setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tratamento> consultarQuantidadeDeTratamentosFinalizadosPorMes(Date dataInicial, Date dataFinal) {
		return entityManager.createNamedQuery("tratamento.consultarQuantidadeDeTratamentosFinalizadosPorMes").setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal).getResultList();
	}
	
}
