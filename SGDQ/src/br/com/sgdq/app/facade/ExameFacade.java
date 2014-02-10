/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.sgdq.app.entity.Exame;
import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.entity.Tratamento;

/**
 *
 * @author Antonio Augusto
 */
@Stateless
public class ExameFacade extends AbstractFacade<Exame> {
    
	//@PersistenceContext(unitName = "SGDQPU")
    private EntityManager em;
	
    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }

    public ExameFacade() {
        super(Exame.class);
    }
    
    /**
     * Retorna os exames de um dado tratamento
     * @param idTratamento
     * @return
     */
    public List<Exame> findByTratamento(Tratamento idTratamento) {
    	try{
	    	getEntityManager();
	    	return em.createNamedQuery("Exame.findByIdTratamento")
		    .setParameter("idtratamento", idTratamento)
		    .getResultList();
	    } catch(NoResultException e) {
	        return null;
	    }
    }
}
