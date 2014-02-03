/*
 * 
To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;

import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.entity.Tratamento;

/**
 *
 * @author Antonio Augusto
 */
@Stateless
public class ProntuarioFacade extends AbstractFacade<Prontuario> {
    
//	@PersistenceUnit (unitName = "SGDQPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }

    public ProntuarioFacade() {
        super(Prontuario.class);
    }
    
    /**
     *  Pesquissa prontuario de um dado paciente por CPF
     * @param nuCPF
     * @return Prontuario
     */
    public Prontuario findBynuCPFPaciente(String nuCPF) {
    	try{
	    	getEntityManager();
	    	return (Prontuario)em.createNamedQuery("Prontuario.findBynuCPFPaciente")
	    		    .setParameter("nucpf", nuCPF)
	    		    .getSingleResult();
	    } catch(NoResultException e) {
	        return null;
	    }
    }
    
    public List<Prontuario> findByProntuarioCPFNomePaciente(Integer idProntuario, String nmPaciente, String nuCPF) {
    	try{
	    	getEntityManager();
	    	if (!nmPaciente.isEmpty())
	    		nmPaciente = "%"+nmPaciente+"%";
	    	
	    	return em.createNamedQuery("Prontuario.findByProntuarioCPFNomePaciente")
	    		    .setParameter("idProntuario", idProntuario)
	    		    .setParameter("nmPessoa", nmPaciente)
	    		    .setParameter("nucpf", nuCPF)
	    		    .getResultList();
	    } catch(NoResultException e) {
	        return null;
	    }
    } 
}


