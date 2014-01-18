/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.sgdq.app.entity.TratamentoStatus;

/**
 *
 * @author Antonio Augusto
 */
@Stateless
public class TratamentoStatusFacade extends AbstractFacade<TratamentoStatus> {
    
	//@PersistenceContext(unitName = "SGDQPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }
    
    public TratamentoStatusFacade() {
        super(TratamentoStatus.class);
    }
    
}
