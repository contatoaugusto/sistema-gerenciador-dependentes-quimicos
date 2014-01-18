
package br.com.sgdq.app.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.sgdq.app.entity.Cidade;
import br.com.sgdq.app.entity.Endereco;
import br.com.sgdq.app.entity.Estado;
import br.com.sgdq.app.entity.Paciente;
import br.com.sgdq.app.entity.Pessoa;
import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.entity.Responsavel;

/**
 * @author Antonio Augusto
 *
 */
public class HibernateUtil {
	 
	private static SessionFactory sessionFactory;
 
	private HibernateUtil() {
	
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				AnnotationConfiguration ac = new AnnotationConfiguration();
				
				ac.addAnnotatedClass(Estado.class);
				ac.addAnnotatedClass(Cidade.class);
				ac.addAnnotatedClass(Endereco.class);
				ac.addAnnotatedClass(Pessoa.class);
				ac.addAnnotatedClass(Responsavel.class);
				ac.addAnnotatedClass(Paciente.class);
				ac.addAnnotatedClass(Prontuario.class);
				
				sessionFactory = ac.configure().buildSessionFactory();
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
			return sessionFactory;
		
		} else {
		
			return sessionFactory;
		}
	}
}