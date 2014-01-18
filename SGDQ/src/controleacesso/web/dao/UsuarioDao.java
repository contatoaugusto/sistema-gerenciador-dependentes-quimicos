package controleacesso.web.dao;

// Generated 18/12/2012 20:57:51 by Hibernate Tools 3.4.0.CR1

//import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import controleacesso.web.criptografia.EncripDecriptUtil;
import controleacesso.web.modelo.Perfil;
import controleacesso.web.modelo.Pessoa;
import controleacesso.web.modelo.Recurso;
import controleacesso.web.modelo.Usuario;
import controleacesso.web.util.Constantes;
import controleacesso.web.util.HibernateUtil;


/**
 * Home object for domain model class Usuario.
 * 
 * @see teste.Usuario
 * @author Antonio Augusto
 */
// @Stateless
public class UsuarioDao {

	private static final Log log = LogFactory.getLog(UsuarioDao.class);

	public void save(Usuario objDao) {
		log.debug("Salvando instância de Usuario");
		
		EncripDecriptUtil criptografia = new EncripDecriptUtil();
		objDao.setDsSenha(criptografia.encripta(objDao.getDsSenha(), Constantes.CRIPTOGRAFIA_TIPO_ALGORITMO));
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(objDao);
		t.commit();
		session.close();
	}

	public void remove(Usuario objDao) {
		log.debug("Removendo instância de Usuario");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		SessionFactoryImplementor sfi = (SessionFactoryImplementor) HibernateUtil.getSessionFactory();
		String dataBaseOwner = sfi.getSettings().getDefaultSchemaName();

		// Remover item na tabela de relacionamento tbusuario_perfil
		for (Perfil perfil : objDao.getTbperfils()) {
			Query query = session.createSQLQuery("delete from " + dataBaseOwner
					+ ".tbusuario_perfil where id_usuario = "
					+ objDao.getIdUsuario() + " and id_perfil = "
					+ perfil.getIdPerfil());
			query.executeUpdate();
		}

		objDao.setTbperfils(null);
		session.delete(objDao);
		t.commit();
		session.close();
	}

	public void update(Usuario objDao, boolean criptografar) {
		log.debug("Atualizando instância de Usuario");
		
		if (criptografar){
			EncripDecriptUtil criptografia = new EncripDecriptUtil();
			objDao.setDsSenha(criptografia.encripta(objDao.getDsSenha(), Constantes.CRIPTOGRAFIA_TIPO_ALGORITMO));
		
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(objDao);
		t.commit();
		session.close();
	}

	public Usuario getObjDao(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Usuario pessoa = (Usuario) session.load(Usuario.class, id);
		// session.close();
		return pessoa;
	}

	public List<Usuario> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Usuario order by nmLogin").list();
		t.commit();
		return lista;
	}

	public Usuario findUsuarioByName(String nmLogin) {
		log.debug("Obtendo Usuario com o nome: " + nmLogin);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Usuario usuario = (Usuario) session.createQuery("from Usuario where nmLogin = ? and fgAtivo = true")
				.setString(0, nmLogin).uniqueResult();

		t.commit();
		return usuario;
	}

	public List<SelectItem> getItemsPessoa() {
		PessoaDao pessoaDAO = new PessoaDao();
		List<SelectItem> statusItem = new ArrayList<SelectItem>();
		List<Pessoa> listaSA = pessoaDAO.list();
		for (Pessoa cbx : listaSA) {
			SelectItem s = new SelectItem();
			s.setValue(cbx.getIdPessoa());
			s.setLabel(cbx.getNmPessoa());
			statusItem.add(s);
		}
		return statusItem;
	}

	/***
	 * Verifica se o usuário existe e se este possui perfil de acesso definido.
	 * 
	 * @param username
	 * @return Usuario
	 */
	public Usuario searchDatabase(String username, String linkrecurso) {
		// Retrieve all users from the database
		UsuarioDao usuariohome = new UsuarioDao();
		Usuario usuario = usuariohome.findUsuarioByName(username);

		if (usuario != null) {

			log.debug("Usuário encontrado.");

			// Verifica se o usuário tem perfil definifo e se sim, verifica se
			// este possui o recurso requisitado
			String recursoEntity = "";
			linkrecurso = linkrecurso.replace('/', ' ').trim();
			linkrecurso = linkrecurso.contains(".") ? linkrecurso.replace(".",
					"::").split("::")[0] : linkrecurso;
			for (Perfil perfil : usuario.getTbperfils()) {
				for (Recurso recurso : perfil.getTbrecursos()) {
					recursoEntity = recurso.getLkLink().contains(".") ? recurso
							.getLkLink().replace(".", "::").split("::")[0]
							: recurso.getLkLink();
					if (recursoEntity.equals(linkrecurso))
						return usuario;
				}
				log.error("Usuário sem acesso ao recurso: " + linkrecurso);
				throw new RuntimeException("Usuário sem acesso ao recurso: "
						+ linkrecurso);
			}
			log.error("Usuário sem perfil de acesso definido!");
			throw new RuntimeException("Usuário sem perfil de acesso definido!");
		}

		log.error("Usuário " + username + " não existe!");
		throw new RuntimeException("Usuário " + username + " não existe!");
	}

}
