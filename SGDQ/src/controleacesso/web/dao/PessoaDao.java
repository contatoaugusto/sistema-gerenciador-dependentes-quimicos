package controleacesso.web.dao;

// Generated 24/12/2012 11:14:53 by Hibernate Tools 3.4.0.CR1

//import javax.ejb.Stateless;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import controleacesso.web.modelo.Pessoa;
import controleacesso.web.util.HibernateUtil;


/**
 * Home object for domain model class Pessoa.
 * @see controleacesso.web.modelo.Pessoa
 * @author Antonio Augusto
 */
//@Stateless
public class PessoaDao {

	private static final Log log = LogFactory.getLog(PessoaDao.class);

	public void save(Pessoa objDao) {
		log.debug("Salvando instância de Pessoa");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(objDao);
		t.commit();
		session.close();
	}
	
	
	public void remove(Pessoa objDao) {
		log.debug("Removendo instância de Pessoa");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(objDao);
		t.commit();	
		session.close();
	}

	
	public void update(Pessoa objDao) {
		log.debug("Atualizando instância de Pessoa");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(objDao);
		t.commit();	
		session.close();
	}

	public Pessoa getObjDao(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Pessoa pessoa =  (Pessoa) session.load(Pessoa.class, id);
		session.close();
		return pessoa;
	}
	
	public List<Pessoa> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Pessoa order by nmPessoa").list();
		t.commit();
		session.close();
		return lista;
	}
}
