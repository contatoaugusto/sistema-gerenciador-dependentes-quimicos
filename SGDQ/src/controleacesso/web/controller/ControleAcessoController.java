package controleacesso.web.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import controleacesso.web.modelo.ControleAcessoUsuario;
import controleacesso.web.process.ControleAcessoProcess;


/**
 * @author Antonio Augusto
 *
 */
@ManagedBean
@SessionScoped
public class ControleAcessoController {

	private String nmLogin;
	public String getNmLogin() {
		return nmLogin;
	}

	public void setNmLogin(String nmLogin) {
		this.nmLogin = nmLogin;
	}

	public String getNmPessoa() {
		return nmPessoa;
	}

	public void setNmPessoa(String nmPessoa) {
		this.nmPessoa = nmPessoa;
	}

	public ControleAcessoUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(ControleAcessoUsuario usuario) {
		this.usuario = usuario;
	}

	private String nmPessoa;
	private ControleAcessoUsuario usuario;
	
	
	@PostConstruct
    public void init() {
		loadUsuarioSessao();
		nmLogin = usuario.getNmLogin();
		nmPessoa = usuario.getTbpessoa().getNmPessoa();
		
	}	
	
	private void loadUsuarioSessao() {
		// Recuperar usuário logado na sessão Que foi criada na classe do componeten controleAcesso.jar
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();  
		HttpSession httpSession = request.getSession(false);  
		usuario = (ControleAcessoUsuario) httpSession.getAttribute("usuario");  
	}
}