package controleacesso.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import controleacesso.web.process.ControleAcessoProcess;


/**
 * @author Antonio Augusto
 *
 */
@ManagedBean
@SessionScoped
public class PermissaoController {

	ControleAcessoProcess controleacesoProcess = null;
	String nmUsuarioLogado = null;
	
	public boolean hasPermission(String  nmRecurso){
		
		// Recupera usuário logado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		nmUsuarioLogado = authentication.getPrincipal().toString(); 
	
		if (!nmUsuarioLogado.equalsIgnoreCase("anonymousUser")){
			
			if (controleacesoProcess == null)
				controleacesoProcess = new ControleAcessoProcess();
			
			if (controleacesoProcess.verificaRecursoComAcesso(nmUsuarioLogado, nmRecurso) )
				return true;
		}
		return false;
	}
	
}
