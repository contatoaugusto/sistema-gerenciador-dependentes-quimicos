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

import br.com.sgdq.app.entity.Tratamento;

/**
 *
 * @author Antonio Augusto
 */
@Stateless
public class TratamentoFacade extends AbstractFacade<Tratamento> {
   
	//@PersistenceContext(unitName = "SGDQPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }

    public TratamentoFacade() {
        super(Tratamento.class);
    }
    
    public Tratamento findByProntuario(Integer idProntuario) {
    	try{
	    	getEntityManager();
	    	return (Tratamento)em.createNamedQuery("Tratamento.findByProntuario")
	    			.setParameter("idprontuario", idProntuario)
	    			.getSingleResult();
	    } catch(NoResultException e) {
	        return null;
	    }
    }
    
    /*
     * Retorna o tratamento que não eseja finalizado que o prontuario esteja vinculado
     * @param idProntuario
     * @return Tratamento
     */
    public Tratamento findByProntuarioAtivo(Integer idProntuario) {
    	try{
	    	getEntityManager();
	    	return (Tratamento)em.createNamedQuery("Tratamento.findByProntuarioAtivo")
	    		    .setParameter("idprontuario", idProntuario)
	    		    .setParameter("icativo", 1)
	    		    .getSingleResult();
	    } catch(Exception e) {
	        return null;
	    }
    }
    
}
