/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.facade;

import br.com.sgdq.app.entity.Cidade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author prohgy
 */
@Stateless
public class CidadeFacade extends AbstractFacade<Cidade> {
    @PersistenceContext(unitName = "SGDQPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CidadeFacade() {
        super(Cidade.class);
    }
    
}
