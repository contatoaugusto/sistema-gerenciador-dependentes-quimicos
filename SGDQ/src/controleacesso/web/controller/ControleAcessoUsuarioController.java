package controleacesso.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import controleacesso.web.controller.util.JsfUtil;
import controleacesso.web.dao.ControleAcessoPerfilDao;
import controleacesso.web.dao.ControleAcessoUsuarioDao;
import controleacesso.web.modelo.ControleAcessoPerfil;
import controleacesso.web.modelo.ControleAcessoUsuario;


@ManagedBean
@RequestScoped
public class ControleAcessoUsuarioController implements Serializable {

	private ControleAcessoUsuario usuario = new ControleAcessoUsuario();
	private DataModel<ControleAcessoUsuario> lstUsuario;
	
	private DualListModel<ControleAcessoPerfil> perfis;
	
	private String senhaAntiga;
	
	public ControleAcessoUsuarioController (){
		carregaDualListModel(false);
	}
	
	@PostConstruct
	 public void init(){
	        System.out.println(" Bean Usuario! ");
	 }
	 public String getMessage(){
	        return "Usuario";
	 }
	public DataModel<ControleAcessoUsuario> getListarUsuario() {
		ControleAcessoUsuarioDao dao = new ControleAcessoUsuarioDao();
		List<ControleAcessoUsuario> listaUsuario = dao.list();
		lstUsuario = new ListDataModel<ControleAcessoUsuario>(listaUsuario);
		return lstUsuario;
	}
	
	public void prepararAdicionarUsuario(ActionEvent actionEvent){
		this.usuario = new ControleAcessoUsuario();
		carregaDualListModel(false);
		System.out.println("Preparar Adicionar Usuario");
	}
	public void prepararAlterarUsuario(){
		this.usuario= null;
		this.usuario = (ControleAcessoUsuario)(lstUsuario.getRowData());
		
		senhaAntiga = usuario.getDsSenha();
		
		carregaDualListModel(true);
		System.out.println("Preparar Alterar Usuario");

	}
	public ControleAcessoUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(ControleAcessoUsuario usuario) {
		this.usuario = usuario;
	}
	
	public void setLstUsuario(DataModel<ControleAcessoUsuario> lstUsuario) {
		this.lstUsuario = lstUsuario;
	}
	public void excluirUsuario(){
		ControleAcessoUsuario usuario = (ControleAcessoUsuario)(lstUsuario.getRowData());
		ControleAcessoUsuarioDao dao = new ControleAcessoUsuarioDao();
		dao.remove(usuario);
	}

	public void adicionarUsuario(ActionEvent actionEvent){
		System.out.println("Adicionar Usuario");
		
		ControleAcessoUsuarioDao dao = new ControleAcessoUsuarioDao();
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
		ControleAcessoUsuarioDao dao = new ControleAcessoUsuarioDao();
		dao.update(usuario, criptografar);
	}
	public void validacaoCampos(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Validação", "Campo Obrigatório"));   
    }  
	
	public List<SelectItem> getItemsPessoa(){  
		ControleAcessoUsuarioDao dao = new ControleAcessoUsuarioDao();
		return JsfUtil.getSelectItems(dao.getItemsPessoa(), true);
	 } 
	
	public DualListModel<ControleAcessoPerfil> getPerfis() {  
		return perfis;  
	}  
	public void setPerfis(DualListModel<ControleAcessoPerfil> perfis) {  
		this.perfis = perfis;  
	} 

	 public void onTransfer(TransferEvent event) {  
	     StringBuilder builder = new StringBuilder();  
	     for(Object item : event.getItems()) {  
	         builder.append(((ControleAcessoPerfil) item).getDsPerfil()).append("<br />");  
	     }  
	       
	     FacesMessage msg = new FacesMessage();  
	     msg.setSeverity(FacesMessage.SEVERITY_INFO);  
	     msg.setSummary("Items Transferidos");  
	     msg.setDetail(builder.toString());  
	       
	     FacesContext.getCurrentInstance().addMessage(null, msg);  
	 }
	
	private void carregaDualListModel(boolean isCarregarPerfilComAcesso){
		List<ControleAcessoPerfil> source =  new ArrayList<ControleAcessoPerfil>();  
		List<ControleAcessoPerfil> target = new ArrayList<ControleAcessoPerfil>();
		
		ControleAcessoPerfilDao dao = new ControleAcessoPerfilDao();
		source = dao.list();
		
		if (isCarregarPerfilComAcesso){
			target = new ArrayList<ControleAcessoPerfil>(usuario.getTbperfils());
			source.removeAll(usuario.getTbperfils());
		}
		
		perfis = new DualListModel<ControleAcessoPerfil>(source, target);
	}
	
	private void setDualListModelPerfiToSet(){
		for (ControleAcessoPerfil perfil : perfis.getTarget())
			usuario.getTbperfils().add(perfil);
	}
	
	public List<SelectItem> getListarPerfil() {
		ControleAcessoPerfilDao dao = new ControleAcessoPerfilDao();
		//ArrayList<ControleAcessoPerfil> listaPerfil = (ArrayList)dao.list();
		return JsfUtil.getSelectItems(dao.list(), true);
	}
}
