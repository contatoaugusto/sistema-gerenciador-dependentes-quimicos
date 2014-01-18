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

import br.com.sgdq.app.entity.FaseTratamento;

/**
 *
 * @author Antonio Augusto
 */
@Stateless
public class FaseTratamentoFacade extends AbstractFacade<FaseTratamento> {
    
	//@PersistenceContext(unitName = "SGDQPU")
    private EntityManager em;

	public void setEngityManager(EntityManager entityManager) {
        this.em = entityManager;
    }
    
	
    //@Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }


    public FaseTratamentoFacade() {
        super(FaseTratamento.class);
    }
    
    /**
     * 
     * @param idTratamento
     * @return List<FaseTratamento>
     */
    public List<FaseTratamento> findByTratamento(Integer idTratamento) {
    	try{
	    	getEntityManager();
	    	return em.createNamedQuery("FaseTratamento.findByTratamento")
	    			.setParameter("idtratamento", idTratamento)
	    			.getResultList();
	    } catch(NoResultException e) {
	        return null;
	    }
    }
}
