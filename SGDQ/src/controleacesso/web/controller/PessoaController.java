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

import controleacesso.web.dao.PessoaDao;
import controleacesso.web.modelo.Pessoa;


@ManagedBean
@SessionScoped
public class PessoaController implements Serializable {

	private Pessoa pessoa = new Pessoa();
	private DataModel<Pessoa> lstPessoa;
	
	@PostConstruct
	 public void init(){
	        System.out.println(" Bean Pessoa! ");
	 }
	 public String getMessage(){
	        return "Pessoa";
	 }
	public DataModel<Pessoa> getListarPessoa() {
		List<Pessoa> listaPessoa = new PessoaDao().list();
		lstPessoa = new ListDataModel<Pessoa>(listaPessoa);
		return lstPessoa;
	}
	
	public void prepararAdicionarPessoa(ActionEvent actionEvent){
		this.pessoa = new Pessoa();
		System.out.println("Preparar Adicionar Pessoa");
	}
	public void prepararAlterarPessoa(ActionEvent actionEvent){
		this.pessoa= null;
		this.pessoa = (Pessoa)(lstPessoa.getRowData());
		System.out.println("Preparar Alterar Pessoa");

	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public void setLstPessoa(DataModel<Pessoa> lstPessoa) {
		this.lstPessoa = lstPessoa;
	}
	public String excluirPessoa(){
		Pessoa pessoa = (Pessoa)(lstPessoa.getRowData());
		PessoaDao dao = new PessoaDao();
		dao.remove(pessoa);
		return "index";
	}
	public void adicionarPessoa(ActionEvent actionEvent){
		PessoaDao dao = new PessoaDao();
		//pessoa.setIdPessoa(lstPessoa.getRowCount() + 1);
		//dao.persist(pessoa);
		dao.save(pessoa);
	}
	public void alterarPessoa(ActionEvent actionEvent){
		//validacaoCampos();
		System.out.println("Alterando Pessoa na Base de dados");
		PessoaDao dao = new PessoaDao();
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
