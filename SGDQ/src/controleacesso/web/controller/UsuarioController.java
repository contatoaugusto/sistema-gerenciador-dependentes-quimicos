package controleacesso.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import controleacesso.web.dao.PerfilDao;
import controleacesso.web.dao.UsuarioDao;
import controleacesso.web.modelo.Perfil;
import controleacesso.web.modelo.Recurso;
import controleacesso.web.modelo.Usuario;


@ManagedBean
@SessionScoped
public class UsuarioController implements Serializable {

	private Usuario usuario = new Usuario();
	private DataModel<Usuario> lstUsuario;
	
	private DualListModel<Perfil> perfis;
	
	private String senhaAntiga;
	
	public UsuarioController (){
		carregaDualListModel(false);
	}
	
	@PostConstruct
	 public void init(){
	        System.out.println(" Bean Usuario! ");
	 }
	 public String getMessage(){
	        return "Usuario";
	 }
	public DataModel<Usuario> getListarUsuario() {
		UsuarioDao dao = new UsuarioDao();
		List<Usuario> listaUsuario = dao.list();
		lstUsuario = new ListDataModel<Usuario>(listaUsuario);
		return lstUsuario;
	}
	
	public void prepararAdicionarUsuario(ActionEvent actionEvent){
		this.usuario = new Usuario();
		carregaDualListModel(false);
		System.out.println("Preparar Adicionar Usuario");
	}
	public void prepararAlterarUsuario(ActionEvent actionEvent){
		this.usuario= null;
		this.usuario = (Usuario)(lstUsuario.getRowData());
		
		senhaAntiga = usuario.getDsSenha();
		
		carregaDualListModel(true);
		System.out.println("Preparar Alterar Usuario");

	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setLstUsuario(DataModel<Usuario> lstUsuario) {
		this.lstUsuario = lstUsuario;
	}
	public void excluirUsuario(){
		Usuario usuario = (Usuario)(lstUsuario.getRowData());
		UsuarioDao dao = new UsuarioDao();
		dao.remove(usuario);
	}

	public void adicionarUsuario(ActionEvent actionEvent){
		System.out.println("Adicionar Usuario");
		
		UsuarioDao dao = new UsuarioDao();
		dao.save(usuario);
	}
	public void alterarUsuario(ActionEvent actionEvent){
		//validacaoCampos();
		System.out.println("Alterando Usuario na Base de dados");
		
		boolean criptografar = true;
		if (usuario.getDsSenha().isEmpty()){
			usuario.setDsSenha(senhaAntiga);
			criptografar = false;
		}
		UsuarioDao dao = new UsuarioDao();
		dao.update(usuario, criptografar);
	}
	public void validacaoCampos(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Validação", "Campo Obrigatório"));   
    }  
	
	public List<SelectItem> getItemsPessoa(){  
		UsuarioDao dao = new UsuarioDao();
		return dao.getItemsPessoa();
	 } 
	
	public DualListModel<Perfil> getPerfis() {  
		return perfis;  
	}  
	public void setPerfis(DualListModel<Perfil> perfis) {  
		this.perfis = perfis;  
	} 

	 public void onTransfer(TransferEvent event) {  
	     StringBuilder builder = new StringBuilder();  
	     for(Object item : event.getItems()) {  
	         builder.append(((Perfil) item).getDsPerfil()).append("<br />");  
	     }  
	       
	     FacesMessage msg = new FacesMessage();  
	     msg.setSeverity(FacesMessage.SEVERITY_INFO);  
	     msg.setSummary("Items Transferidos");  
	     msg.setDetail(builder.toString());  
	       
	     FacesContext.getCurrentInstance().addMessage(null, msg);  
	 }
	
	private void carregaDualListModel(boolean isCarregarPerfilComAcesso){
		List<Perfil> source =  new ArrayList<Perfil>();  
		List<Perfil> target = new ArrayList<Perfil>();
		
		source = getListarPerfil();
		
		if (isCarregarPerfilComAcesso){
			target = new ArrayList<Perfil>(usuario.getTbperfils());
			source.removeAll(usuario.getTbperfils());
		}
		
		perfis = new DualListModel<Perfil>(source, target);
	}
	
	private void setDualListModelPerfiToSet(){
		for (Perfil perfil : perfis.getTarget())
			usuario.getTbperfils().add(perfil);
	}
	
	public ArrayList<Perfil> getListarPerfil() {
		PerfilDao dao = new PerfilDao();
		ArrayList<Perfil> listaPerfil = (ArrayList)dao.list();
		return listaPerfil;
	}
}
