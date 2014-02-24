package controleacesso.web.controller;

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

import controleacesso.web.dao.ControleAcessoPerfilDao;
import controleacesso.web.dao.ControleAcessoRecursoDao;
import controleacesso.web.file.FileHandler;
import controleacesso.web.modelo.ControleAcessoPerfil;
import controleacesso.web.modelo.ControleAcessoRecurso;
import controleacesso.web.util.Constantes;


/**
 * @author Antonio Augusto
 *
 */
@ManagedBean
@SessionScoped
public class ControleAcessoRecursoController {

	private ControleAcessoRecurso recurso = new ControleAcessoRecurso();
	private DataModel<ControleAcessoRecurso> lstRecurso;
	private List<ControleAcessoRecurso> lstRecursoAtivo;
	
	private DualListModel<ControleAcessoPerfil> perfis;
	
	private String lkRecursoAntigo;
	
	public ControleAcessoRecursoController (){
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

	public DataModel<ControleAcessoRecurso> getListarRecurso() {
		ControleAcessoRecursoDao dao = new ControleAcessoRecursoDao();
		List<ControleAcessoRecurso> listaRecurso = dao.list();
		lstRecurso = new ListDataModel<ControleAcessoRecurso>(listaRecurso);
		return lstRecurso;
	}
	
	public List<ControleAcessoRecurso> getListarRecursoAtivo() {
		ControleAcessoRecursoDao dao = new ControleAcessoRecursoDao();
		lstRecursoAtivo = dao.listByRecursoAtivo();
		return lstRecursoAtivo;
	}

	public ArrayList<ControleAcessoPerfil> getListarPerfil() {
		ControleAcessoPerfilDao dao = new ControleAcessoPerfilDao();
		ArrayList<ControleAcessoPerfil> listaPerfil = (ArrayList)dao.list();
		return listaPerfil;
	}
	
	
	public void prepararAdicionarRecurso(ActionEvent actionEvent) {
		this.recurso = new ControleAcessoRecurso();
		this.recurso.setDtInclusao(Calendar.getInstance().getTime());
		this.recurso.setFgAtivo(true);
		carregaDualListModel(false);
		carregaDadosBasicos ();
		System.out.println("Preparar Adicionar Recurso");
	}

	public void prepararAlterarRecurso(ActionEvent actionEvent) {
		this.recurso = null;
		this.recurso = (ControleAcessoRecurso) (lstRecurso.getRowData());
		carregaDualListModel(true);
		carregaDadosBasicos ();
		lkRecursoAntigo = recurso.getLkLink();
		System.out.println("Preparar Alterar Recurso");
	}

	public ControleAcessoRecurso getRecurso() {
		return recurso;
	}

	public void setRecurso(ControleAcessoRecurso recurso) {
		this.recurso = recurso;
	}

	public void setLstRecurso(DataModel<ControleAcessoRecurso> lstRecurso) {
		this.lstRecurso = lstRecurso;
	}

	public void excluirRecurso() {
		ControleAcessoRecurso recurso = (ControleAcessoRecurso) (lstRecurso.getRowData());
		ControleAcessoRecursoDao dao = new ControleAcessoRecursoDao();
		dao.remove(recurso);
		FileHandler fileHandler = new FileHandler();
		fileHandler.removeInterceptUrlXML(recurso.getLkLink(), recuperaDiretorioConfiguracao());
	}

	public void adicionarRecurso(ActionEvent actionEvent) {
		System.out.println("Adicionar Recurso");
		ControleAcessoRecursoDao dao = new ControleAcessoRecursoDao();
		setDualListModelPerfiToSet();
		dao.save(recurso);
		FileHandler fileHandler = new FileHandler();
		fileHandler.criaInterceptUrlXML(recurso.getLkLink(), recuperaDiretorioConfiguracao());
	}

	public void alterarRecurso(ActionEvent actionEvent) {
		// validacaoCampos();
		System.out.println("Alterando Recurso na Base de dados");
		ControleAcessoRecursoDao dao = new ControleAcessoRecursoDao();
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
		ControleAcessoRecursoDao dao = new ControleAcessoRecursoDao();
		return dao.getItemsPerfil();
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
	
	public DualListModel<ControleAcessoPerfil> getPerfis() {  
		return perfis;  
	}  
	public void setPerfis(DualListModel<ControleAcessoPerfil> perfis) {  
		this.perfis = perfis;  
	} 

	private void carregaDualListModel(boolean isCarregarPerfilComAcesso){
		List<ControleAcessoPerfil> source =  new ArrayList<ControleAcessoPerfil>();  
		List<ControleAcessoPerfil> target = new ArrayList<ControleAcessoPerfil>();
		
		source = getListarPerfil();
		
		if (isCarregarPerfilComAcesso){
			target = new ArrayList<ControleAcessoPerfil>(recurso.getTbperfils());
			source.removeAll(recurso.getTbperfils());
		}
		
		perfis = new DualListModel<ControleAcessoPerfil>(source, target);
	}
	
	private void setDualListModelPerfiToSet(){
		for (ControleAcessoPerfil perfil : perfis.getTarget())
			recurso.getTbperfils().add(perfil);
		//recurso.setTbperfils(new HashSet<Perfil>(perfis.getTarget()));
		
	}
	
	private void carregaDadosBasicos (){
		if (recurso.getTbrecursoFilho() == null)
			recurso.setTbrecursoFilho(new ControleAcessoRecurso());
		
	}
	
	public List<SelectItem> getItemsRecursoPai(){  
		ControleAcessoRecursoDao dao = new ControleAcessoRecursoDao();
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
