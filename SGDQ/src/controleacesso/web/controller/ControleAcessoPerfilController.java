package controleacesso.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import controleacesso.web.dao.ControleAcessoPerfilDao;
import controleacesso.web.modelo.ControleAcessoPerfil;


@ManagedBean
@SessionScoped
public class ControleAcessoPerfilController implements Serializable {

	private ControleAcessoPerfil perfil = new ControleAcessoPerfil();
	private DataModel<ControleAcessoPerfil> lstPerfil;

	@PostConstruct
	public void init() {
		System.out.println(" Bean Perfil! ");
	}

	public String getMessage() {
		return "Perfil";
	}

	public ControleAcessoPerfil getPerfil() {
		return perfil;
	}

	public void setPerfil(ControleAcessoPerfil perfil) {
		this.perfil = perfil;
	}

	public DataModel<ControleAcessoPerfil> getListarPerfil() {
		List<ControleAcessoPerfil> listaPerfil = new ControleAcessoPerfilDao().list();
		lstPerfil = new ListDataModel<ControleAcessoPerfil>(listaPerfil);
		return lstPerfil;
	}

	public void prepararAdicionarPerfil(ActionEvent actionEvent) {
		this.perfil = new ControleAcessoPerfil();
		System.out.println("Preparar Adicionar Perfil");
	}

	public void prepararAlterarPerfil(ActionEvent actionEvent) {
		this.perfil = null;
		this.perfil = (ControleAcessoPerfil) (lstPerfil.getRowData());
		System.out.println("Preparar Alterar Perfil");

	}

	public void adicionarPerfil(ActionEvent actionEvent) {
		ControleAcessoPerfilDao dao = new ControleAcessoPerfilDao();
		dao.save(perfil);
	}

	public void alterarPerfil(ActionEvent actionEvent) {
		// validacaoCampos();
		System.out.println("Alterando Perfil na Base de dados");
		ControleAcessoPerfilDao dao = new ControleAcessoPerfilDao();
		dao.update(perfil);
	}

	public void validacaoCampos() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação",
						"Campo Obrigatório"));
	}

}
