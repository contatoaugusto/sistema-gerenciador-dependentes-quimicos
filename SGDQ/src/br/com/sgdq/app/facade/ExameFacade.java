/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.sgdq.app.entity.Exame;

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
    
}
