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
import javax.persistence.PostLoad;

import org.primefaces.context.RequestContext;
//import org.springframework.test.context.transaction.AfterTransaction;

import controleacesso.web.dao.ControleAcessoPessoaDao;
import controleacesso.web.modelo.ControleAcessoPessoa;


@ManagedBean
@SessionScoped
public class ControleAcessoPessoaController implements Serializable {

	private ControleAcessoPessoa pessoa = new ControleAcessoPessoa();
	private DataModel<ControleAcessoPessoa> lstPessoa;
	
	@PostConstruct
	 public void init(){
	        System.out.println(" Bean Pessoa! ");
	 }
	 public String getMessage(){
	        return "Pessoa";
	 }
	public DataModel<ControleAcessoPessoa> getListarPessoa() {
		List<ControleAcessoPessoa> listaPessoa = new ControleAcessoPessoaDao().list();
		lstPessoa = new ListDataModel<ControleAcessoPessoa>(listaPessoa);
		return lstPessoa;
	}
	
	public void prepararAdicionarPessoa(ActionEvent actionEvent){
		this.pessoa = new ControleAcessoPessoa();
		System.out.println("Preparar Adicionar Pessoa");
	}
	public void prepararAlterarPessoa(ActionEvent actionEvent){
		this.pessoa= null;
		this.pessoa = (ControleAcessoPessoa)(lstPessoa.getRowData());
		System.out.println("Preparar Alterar Pessoa");

	}
	public ControleAcessoPessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(ControleAcessoPessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public void setLstPessoa(DataModel<ControleAcessoPessoa> lstPessoa) {
		this.lstPessoa = lstPessoa;
	}
	public String excluirPessoa(){
		ControleAcessoPessoa pessoa = (ControleAcessoPessoa)(lstPessoa.getRowData());
		ControleAcessoPessoaDao dao = new ControleAcessoPessoaDao();
		dao.remove(pessoa);
		return "index";
	}
	public void adicionarPessoa(ActionEvent actionEvent){
		ControleAcessoPessoaDao dao = new ControleAcessoPessoaDao();
		//pessoa.setIdPessoa(lstPessoa.getRowCount() + 1);
		//dao.persist(pessoa);
		dao.save(pessoa);
	}
	public void alterarPessoa(ActionEvent actionEvent){
		//validacaoCampos();
		System.out.println("Alterando Pessoa na Base de dados");
		ControleAcessoPessoaDao dao = new ControleAcessoPessoaDao();
		dao.update(pessoa);
	}
	public void validacaoCampos(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Validação", "Campo Obrigatório"));   
    }  
	
//	@PostLoad
//	 public void pos1(){
//	        System.out.println(" Bean Pessoa. Pos 1! ");
//	 }
//	@AfterTransaction
//	 public void pos2(){
//	        System.out.println(" Bean Pessoa. Pos 2! ");
//	 }
}
