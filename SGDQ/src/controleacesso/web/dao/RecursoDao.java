package controleacesso.web.dao;

// Generated 18/12/2012 20:57:51 by Hibernate Tools 3.4.0.CR1

//import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.transaction.annotation.Transactional;

import controleacesso.web.modelo.Perfil;
import controleacesso.web.modelo.Pessoa;
import controleacesso.web.modelo.Recurso;
import controleacesso.web.util.HibernateUtil;


/**
 * Home object for domain model class Recurso.
 * @see teste.Recurso
 * @author Antonio Augusto
 */
//@Stateless
public class RecursoDao {

	private static final Log log = LogFactory.getLog(RecursoDao.class);

	@Transactional
	public void save(Recurso objDao) {
		log.debug("Salvando instância de Recurso");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(objDao);
		t.commit();
		//session.close();
	}
	
	@Transactional
	public void remove(Recurso objDao) {
		log.debug("Removendo instância de Recurso");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		SessionFactoryImplementor sfi = (SessionFactoryImplementor)HibernateUtil.getSessionFactory();
		String dataBaseOwner = sfi.getSettings().getDefaultSchemaName();
		
		// Remover item na tabela de relacionamento tb_recurso_perfil
		for (Perfil perfil : objDao.getTbperfils()){
			Query query = session.createSQLQuery("delete from " + dataBaseOwner +".tb_recurso_perfil where id_recurso = " + objDao.getIdRecurso() + 
					" and id_perfil = " + perfil.getIdPerfil());
			query.executeUpdate();
		}		
		
		objDao.setTbperfils(null);
		session.delete(objDao);
		
		t.commit();	
		//session.close();
	}

	@Transactional
	public void update(Recurso objDao) {
		log.debug("Atualizando instância de Recurso");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.merge(objDao);
		t.commit();	
		//session.close();
	}

	public Recurso getObjDao(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Recurso recurso = (Recurso) session.load(Recurso.class, id);
		return recurso;
	}
	
	@Transactional
	public List<Recurso> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Recurso order by nmRecurso").list();
		t.commit();
		//session.close();
		return lista;
	}
	
	@Transactional
	public List<Recurso> listByRecursoAtivo() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Recurso order where fgAtivo = ? by nmRecurso").setBoolean(0, true).list();
		t.commit();
		//session.close();
		return lista;
	}
	
	
//	public Recurso listByRecurso(Recurso usuario) {
//		log.debug("Obtendo Recurso o usario a seguir te acesso: " + usuario.getNmRecurso());
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		Transaction t = session.beginTransaction();
//		
//		List<Recurso> recurso = session.createQuery("from Recurso where nmRecurso = ? and fgAtivo = true").setString(0, nmRecurso).uniqueResult();
//										  	
//		t.commit();
//		return usuario;
//	}
	
	 public List<SelectItem> getItemsPerfil(){  
		 PerfilDao perfilDAO = new PerfilDao();
		 List<SelectItem> statusItem = new ArrayList<SelectItem>();
		 List<Perfil> listaSA = perfilDAO.list();  
	        for  (Perfil cbx : listaSA){    
	              SelectItem  s = new SelectItem();    
	              s.setValue(cbx.getIdPerfil());    
	              s.setLabel(cbx.getDsPerfil());    
	              statusItem.add(s);    
	       }    
	       return statusItem;  
	   }
	 
	 public List<SelectItem> getItemsRecursoPai(){  
		 RecursoDao recursoDAO = new RecursoDao();
		 List<SelectItem> recursoItem = new ArrayList<SelectItem>();
		 List<Recurso> listaSA = recursoDAO.list();  
	        for  (Recurso cbx : listaSA){    
	              SelectItem  s = new SelectItem();    
	              s.setValue(cbx.getIdRecurso());    
	              s.setLabel(cbx.getNmRecurso());    
	              recursoItem.add(s);    
	       }    
	       return recursoItem;  
	   }
	 
	
}
