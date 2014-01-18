/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import br.com.sgdq.app.entity.util.EntityManagerFactory;

/**
 *
 * @author Antonio Augusto
 */
public abstract class AbstractFacade<T>  extends EntityManagerFactory{
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
    	EntityManager em = createEntityManager();
    	em.getTransaction().begin();
    	em.persist(entity);
    	em.getTransaction().commit();
    }

    public void edit(T entity) {
    	EntityManager em = createEntityManager();
    	em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public void remove(T entity) {
    	EntityManager em = createEntityManager();
    	em.getTransaction().begin();
    	em.remove(em.merge(entity));
    	em.getTransaction().commit();
    }

    public T find(Object id) {
        return createEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
    	EntityManager em = createEntityManager();
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
        
    }
    
     public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = createEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = createEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    
    
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = createEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(createEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = createEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
