package controleacesso.web.dao;

// Generated 18/12/2012 20:57:51 by Hibernate Tools 3.4.0.CR1

//import javax.ejb.Stateless;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import controleacesso.web.modelo.Perfil;
import controleacesso.web.util.HibernateUtil;


//import cobra.sim.util.teste.Perfil;

/**
 * Home object for domain model class Perfil.
 * @see teste.Perfil
 * @author Hibernate Tools
 */
//@Stateless
public class PerfilDao {

	private static final Log log = LogFactory.getLog(PerfilDao.class);

//	@PersistenceContext
//	private EntityManager entityManager;

	public void save(Perfil objDao) {
		log.debug("Salvando instância de Recurso");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(objDao);
		t.commit();
		session.close();
	}
	
	
	public void remove(Perfil objDao) {
		log.debug("Removendo instância de Recurso");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(objDao);
		t.commit();	
		//session.close();
	}

	
	public void update(Perfil objDao) {
		log.debug("Atualizando instância de Recurso");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(objDao);
		t.commit();	
		//session.close();
	}
	
	public Perfil getObjDao(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Perfil) session.load(Perfil.class, id);
	}
	
	public List<Perfil> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Perfil").list();
		t.commit();
		return lista;
	}
	
	public List<Perfil> listByUsuario(int idUsuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("select perfil from Perfil perfil INNER JOIN FETCH  perfil.tbusuarios usuario where usuario.idUsuario = ?").setInteger(0, idUsuario).list();
										  	
		t.commit();
		return lista;
	}
}
