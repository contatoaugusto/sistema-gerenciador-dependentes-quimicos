package controleacesso.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import controleacesso.web.dao.PerfilDao;
import controleacesso.web.dao.RecursoDao;
import controleacesso.web.file.FileHandler;
import controleacesso.web.modelo.Perfil;
import controleacesso.web.modelo.Recurso;
import controleacesso.web.util.Constantes;


/**
 * @author Antonio Augusto
 *
 */
@ManagedBean
@SessionScoped
public class RecursoController {

	private Recurso recurso = new Recurso();
	private DataModel<Recurso> lstRecurso;
	private List<Recurso> lstRecursoAtivo;
	
	private DualListModel<Perfil> perfis;
	
	private String lkRecursoAntigo;
	
	public RecursoController (){
		carregaDualListModel(false);
		// Aqui verifica toda a lista de recursos ativos, constata se consta no xml de configuraÁ„o. Se n„o existe cria.
		
	}
	
	@PostConstruct
	public void init() {
		System.out.println(" Bean Recurso! ");
	}

	public String getMessage() {
		return "Recurso";
	}

	public DataModel<Recurso> getListarRecurso() {
		RecursoDao dao = new RecursoDao();
		List<Recurso> listaRecurso = dao.list();
		lstRecurso = new ListDataModel<Recurso>(listaRecurso);
		return lstRecurso;
	}
	
	public List<Recurso> getListarRecursoAtivo() {
		RecursoDao dao = new RecursoDao();
		lstRecursoAtivo = dao.listByRecursoAtivo();
		return lstRecursoAtivo;
	}

	public ArrayList<Perfil> getListarPerfil() {
		PerfilDao dao = new PerfilDao();
		ArrayList<Perfil> listaPerfil = (ArrayList)dao.list();
		return listaPerfil;
	}
	
	
	public void prepararAdicionarRecurso(ActionEvent actionEvent) {
		this.recurso = new Recurso();
		this.recurso.setDtInclusao(Calendar.getInstance().getTime());
		this.recurso.setFgAtivo(true);
		carregaDualListModel(false);
		carregaDadosBasicos ();
		System.out.println("Preparar Adicionar Recurso");
	}

	public void prepararAlterarRecurso(ActionEvent actionEvent) {
		this.recurso = null;
		this.recurso = (Recurso) (lstRecurso.getRowData());
		carregaDualListModel(true);
		carregaDadosBasicos ();
		lkRecursoAntigo = recurso.getLkLink();
		System.out.println("Preparar Alterar Recurso");
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public void setLstRecurso(DataModel<Recurso> lstRecurso) {
		this.lstRecurso = lstRecurso;
	}

	public void excluirRecurso() {
		Recurso recurso = (Recurso) (lstRecurso.getRowData());
		RecursoDao dao = new RecursoDao();
		dao.remove(recurso);
		FileHandler fileHandler = new FileHandler();
		fileHandler.removeInterceptUrlXML(recurso.getLkLink(), recuperaDiretorioConfiguracao());
	}

	public void adicionarRecurso(ActionEvent actionEvent) {
		System.out.println("Adicionar Recurso");
		RecursoDao dao = new RecursoDao();
		setDualListModelPerfiToSet();
		dao.save(recurso);
		FileHandler fileHandler = new FileHandler();
		fileHandler.criaInterceptUrlXML(recurso.getLkLink(), recuperaDiretorioConfiguracao());
	}

	public void alterarRecurso(ActionEvent actionEvent) {
		// validacaoCampos();
		System.out.println("Alterando Recurso na Base de dados");
		RecursoDao dao = new RecursoDao();
		setDualListModelPerfiToSet();
		dao.update(recurso);
		FileHandler fileHandler = new FileHandler();
		fileHandler.atualizaInterceptUrlXML(lkRecursoAntigo, recurso.getLkLink(), recuperaDiretorioConfiguracao());
	}

	public void validacaoCampos() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valida√ß√£o",
						"Campo Obrigat√≥rio"));
	}

	public List<SelectItem> getItemsPerfil() {
		RecursoDao dao = new RecursoDao();
		return dao.getItemsPerfil();
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
	
	public DualListModel<Perfil> getPerfis() {  
		return perfis;  
	}  
	public void setPerfis(DualListModel<Perfil> perfis) {  
		this.perfis = perfis;  
	} 

	private void carregaDualListModel(boolean isCarregarPerfilComAcesso){
		List<Perfil> source =  new ArrayList<Perfil>();  
		List<Perfil> target = new ArrayList<Perfil>();
		
		source = getListarPerfil();
		
		if (isCarregarPerfilComAcesso){
			target = new ArrayList<Perfil>(recurso.getTbperfils());
			source.removeAll(recurso.getTbperfils());
		}
		
		perfis = new DualListModel<Perfil>(source, target);
	}
	
	private void setDualListModelPerfiToSet(){
		for (Perfil perfil : perfis.getTarget())
			recurso.getTbperfils().add(perfil);
		//recurso.setTbperfils(new HashSet<Perfil>(perfis.getTarget()));
		
	}
	
	private void carregaDadosBasicos (){
		if (recurso.getTbrecursoFilho() == null)
			recurso.setTbrecursoFilho(new Recurso());
		
	}
	
	public List<SelectItem> getItemsRecursoPai(){  
		RecursoDao dao = new RecursoDao();
		return dao.getItemsRecursoPai();
	 }
	
	
	private String recuperaDiretorioConfiguracao (){
		FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        request.getServletPath();
        java.net.URL r = this.getClass().getClassLoader().getResource("applicationContext.xml");
        return request.getServletContext().getRealPath(Constantes.FILE_DIRETORIO_CONFIGURACAO);	
        
 	}
}
