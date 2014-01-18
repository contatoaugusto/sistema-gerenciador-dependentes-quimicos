/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.facade;

import br.com.sgdq.app.entity.Estado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Antonio Augusto
 */
@Stateless
public class EstadoFacade extends AbstractFacade<Estado> {
    
	//@PersistenceContext(unitName = "SGDQPU")
    private EntityManager em;

	public void setEngityManager(EntityManager entityManager) {
        this.em = entityManager;
    }
    
	
    //@Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }

    public EstadoFacade() {
        super(Estado.class);
    }
    
}
