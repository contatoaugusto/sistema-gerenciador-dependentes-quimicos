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

import controleacesso.web.dao.PerfilDao;
import controleacesso.web.modelo.Perfil;


@ManagedBean
@SessionScoped
public class PerfilController implements Serializable {

	private Perfil perfil = new Perfil();
	private DataModel<Perfil> lstPerfil;

	@PostConstruct
	public void init() {
		System.out.println(" Bean Perfil! ");
	}

	public String getMessage() {
		return "Perfil";
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public DataModel<Perfil> getListarPerfil() {
		List<Perfil> listaPerfil = new PerfilDao().list();
		lstPerfil = new ListDataModel<Perfil>(listaPerfil);
		return lstPerfil;
	}

	public void prepararAdicionarPerfil(ActionEvent actionEvent) {
		this.perfil = new Perfil();
		System.out.println("Preparar Adicionar Perfil");
	}

	public void prepararAlterarPerfil(ActionEvent actionEvent) {
		this.perfil = null;
		this.perfil = (Perfil) (lstPerfil.getRowData());
		System.out.println("Preparar Alterar Perfil");

	}

	public void adicionarPerfil(ActionEvent actionEvent) {
		PerfilDao dao = new PerfilDao();
		dao.save(perfil);
	}

	public void alterarPerfil(ActionEvent actionEvent) {
		// validacaoCampos();
		System.out.println("Alterando Perfil na Base de dados");
		PerfilDao dao = new PerfilDao();
		dao.update(perfil);
	}

	public void validacaoCampos() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação",
						"Campo Obrigatório"));
	}

}
