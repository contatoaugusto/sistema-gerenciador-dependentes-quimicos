package br.com.sgdq.app.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.orm.hibernate3.support.IdTransferringMergeEventListener;

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.controller.util.PaginationHelper;
import br.com.sgdq.app.entity.Exame;
import br.com.sgdq.app.entity.FaseTratamento;
import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.entity.Tratamento;
import br.com.sgdq.app.facade.ExameFacade;
import br.com.sgdq.app.facade.FaseTratamentoFacade;
import br.com.sgdq.app.facade.ProntuarioFacade;
import br.com.sgdq.app.facade.TratamentoFacade;
import br.com.sgdq.app.facade.TratamentoStatusFacade;
import controleacesso.web.modelo.Usuario;

/**
* BackBean da pagina de tratamento do paciente.
* Algumas regras de negócio estão infelizmente nessa classe, por conta do pouco tempo pra apresentação.
* Atentar pra que no futuro essas regras migrem pra uma arquitetura com uma classe "process", por exemplo.
*
* @author Antonio Augusto
*/
//@ViewScoped
//@SessionScoped
@ManagedBean (name = "tratamentoController")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@RequestScoped 
public class TratamentoController implements Serializable {

    private Tratamento current;
    private DataModel items = null;
    @EJB
    private br.com.sgdq.app.facade.TratamentoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Integer idProntuario;
    private Prontuario prontuario;
    private List<FaseTratamento> faseTratamento;
    private Integer idTratamentoStatus;
    private Integer idFaseTratamentoAtual;
    
	public Integer getIdFaseTratamentoAtual() {
		return idFaseTratamentoAtual;
	}

	public void setIdFaseTratamentoAtual(Integer idFaseTratamentoAtual) {
		this.idFaseTratamentoAtual = idFaseTratamentoAtual;
	}

	private String dsmotivofasetratamento;
    
    public String getDsmotivofasetratamento() {
		return dsmotivofasetratamento;
	}

	public void setDsmotivofasetratamento(String dsmotivofasetratamento) {
		this.dsmotivofasetratamento = dsmotivofasetratamento;
	}

	@ManagedProperty(value = "#{faseTratamentoController}")
    private FaseTratamentoController faseTratamentoController;
    
    public FaseTratamentoController getFaseTratamentoController() {
		return faseTratamentoController;
	}

	public void setFaseTratamentoController(
			FaseTratamentoController faseTratamentoController) {
		this.faseTratamentoController = faseTratamentoController;
	}

	public List<FaseTratamento> getFaseTratamento() {
		return faseTratamento;
	}

	public void setFaseTratamento(List<FaseTratamento> faseTratamento) {
		this.faseTratamento = faseTratamento;
	}

	private Usuario usuario;
	
	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}

	public Integer getIdProntuario() {
		return idProntuario;
	}

	public void setIdProntuario(Integer idProntuario) {
		this.idProntuario = idProntuario;
	}

    public Integer getIdTratamentoStatus() {
		return idTratamentoStatus;
	}

	public void setIdTratamentoStatus(Integer idTratamentoStatus) {
		this.idTratamentoStatus = idTratamentoStatus;
	}

	
	public TratamentoController() {}
	
	@PostConstruct
    public void init() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String idProntuarioParam = facesContext.getExternalContext().getRequestParameterMap().get("idProntuario");
		if (idProntuarioParam != null && !idProntuarioParam.isEmpty()){
			 
			idProntuario = new Integer(idProntuarioParam);
			
			loadProntuario(idProntuario);
			
			// Verifcar se esse prontuário está associado um tratamento não finalizado, ativo
			if ( getFacade().findByProntuarioAtivo(idProntuario) != null){
				current = getFacade().findByProntuarioAtivo(idProntuario);
				JsfUtil.addErrorMessage(ResourceBundle.getBundle("/message").getString("tratamento.prontuario.andamento.erro"));
				idProntuario = 0;
			}
		}
		
	
		// Verifica se existe tratamento e realiza procedimentos necessário
		if (getSelected().getIdtratamento() != null && getSelected().getIdtratamento() > 0){
			loadFaseTratamento(getSelected().getIdtratamento());
		}//else{
			loadUsuarioSessao();
		//}
	}


	/**
	 * 
	 */
	private void loadUsuarioSessao() {
		// Recuperar usuário logado na sessão Que foi criada na classe do componeten controleAcesso.jar
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();  
		HttpSession httpSession = request.getSession(false);  
		usuario = (Usuario) httpSession.getAttribute("usuario");  
	}

	private void loadFaseTratamento(Integer idTratamento) {
		FaseTratamentoFacade faseTratamentoFacade = new FaseTratamentoFacade();
		faseTratamento = faseTratamentoFacade.findByTratamento(idTratamento);
		idFaseTratamentoAtual = 0;
		for (FaseTratamento faseTratamentoAuxiliar : faseTratamento){
			if (faseTratamentoAuxiliar.getIdfase() > idFaseTratamentoAtual)
				idFaseTratamentoAtual = faseTratamentoAuxiliar.getIdfase();
		}
	}

	private void loadProntuario(Integer idProntuario) {
		ProntuarioFacade ejbFacadeProntuario = new ProntuarioFacade();
		getSelected().setIdprontuario(idProntuario);
		prontuario = ejbFacadeProntuario.find(idProntuario);
	}
	
	/**
	 * 
	 * @return Tratamento
	 */
    public Tratamento getSelected() {
        if (current == null) {
            current = new Tratamento();	
            selectedItemIndex = -1;
        }
        return current;
    }

    private TratamentoFacade getFacade() {
        if (ejbFacade == null)
        	ejbFacade = new TratamentoFacade();
    	return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    return new ListDataModel(getFacade().findAll());
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Tratamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Tratamento();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
        	
        	loadUsuarioSessao();
        	
        	getSelected().setDtinclusao(new Date());
        	getSelected().setIdusuario(usuario.getIdUsuario());
        	getSelected().setIcativo(new Integer(1).shortValue());
        	
        	//getSelected().setDsexame(getSelected().getDsexame() + " - Data Solicitação: " + getSelected().getDtexame()); 
        	
        	// Guardar o objeto prontuario associado a esse tratamento
        	ProntuarioFacade prontuarioFacade =  new ProntuarioFacade();
        	getSelected().setProntuario(prontuarioFacade.find(getSelected().getIdprontuario()));
        	
        	// Guardar o objeto status associado a esse tratamento
        	TratamentoStatusFacade statusFacade =  new TratamentoStatusFacade();
        	getSelected().setTratamentoStatus(statusFacade.find(getSelected().getIdtratamentostatus()));
        	
        	getFacade().create(current);
        	
        	// Após incluir o tratamento, criar os exames
        	addExame();
        	
        	// A criar tratamento do tipo Internação, deve criar automaticamente fase 1
        	if (current.getIdtratamentotipo() == 2){
        		addFaseTratamento();
        	}
        	
            JsfUtil.addSuccessMessage("Tratamento incluído com sucesso");
           // return prepareCreate();
            return "cadastrarTratamento";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao incluir Tratamento");
            return null;
        }
    }

    /**
     * Criar os 6 exames predefinidos
	 * Foi definido dessa forma pelo fato da documentão não preve um requisito incluir exame, segundo os componentes do grupo    
     */
	public void addExame() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		ExameController exameController = (ExameController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "exameController");
		
		exameController.addExame(current);
		
		exameController.setIdTratamento(current.getIdtratamento());
		exameController.getExameByTratamento();
	}

    public String prepareEdit() {
    	current = null;
    	current = (Tratamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        
        loadUsuarioSessao();
        loadProntuario(getSelected().getIdprontuario());
        loadFaseTratamento(getSelected().getIdtratamento());
        
        return "cadastrarTratamento";
    }

    public String update() {
        try {
        	
        	//current.setDsexame(current.getDsexame() + " - Data Solicitação: " + current.getDtexame());
        	
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Tratamento alterado com sucesso");
 
            loadUsuarioSessao();
            loadProntuario(getSelected().getIdprontuario());
            loadFaseTratamento(getSelected().getIdtratamento());

            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao alterar Tratamento");
            return null;
        }
    }
    
    public String incluirSolicitacaoTermino() {
        try {
        	
        	current = getFacade().find(getSelected().getIdtratamento());
        	current.setDssolicitacaotermino(dsmotivofasetratamento);
        	current.setDtsolicitacaotermino(new Date());
        	loadUsuarioSessao();
        	current.setIdusuariosolicitacaotermino(usuario.getIdUsuario());
        	update();
        	
            JsfUtil.addSuccessMessage("Solicitação de termino incluida com sucesso.");
 
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Erro ao incluir solicitação de termino.");
            return null;
        }
    }

    public String finalizaTratamento() {
        try {
        	
        	current = getFacade().find(getSelected().getIdtratamento());
        	
        	if (current.getIdtratamentotipo() == 2 && idTratamentoStatus == 3){
        		loadFaseTratamento(getSelected().getIdtratamento());
        		if (idFaseTratamentoAtual != 3){
        			JsfUtil.addErrorMessage("Não é permitido finalizar tratamento por conclusão fora da fase 3");
        			return null;
        		}	
        	}
			
        	
	    	current.setIdtratamentostatus(idTratamentoStatus);
	    	current.setIcativo(new Integer(0).shortValue());
	    	current.setDttratamentofim(new Date());
	    	update();
	    	
	        JsfUtil.addSuccessMessage("Tratamento finalizado com sucesso.");
    	    
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Erro ao finalizar tratamento.");
            return null;
        }
    }
      
    public String addFaseTratamento(){
        try {
        	
        	//FaseTratamentoController faseTratamentoController = new FaseTratamentoController();
        	faseTratamentoController.setIdTratamento(getSelected().getIdtratamento());
        	faseTratamentoController.setDsmotivofasetratamento(dsmotivofasetratamento);
        	faseTratamentoController.addFaseTratamento();
        	
        	current = getFacade().find(getSelected().getIdtratamento());
        	loadFaseTratamento(getSelected().getIdtratamento());
        	
        	loadUsuarioSessao();
            loadProntuario(getSelected().getIdprontuario());
//            visualizacoesComponentesTela();
            return "cadastrarTratamento";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/message").getString("fasetratamento.inclusao.erro"));
            return null;
        }
    }

    public String destroy() {
        current = (Tratamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/message").getString("sistema.exclusao"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/message").getString("sistema.exclusao.erro"));
        }
    }

    public void prepareConsultarProntuarios() throws IOException {
    	ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/pages/manterprontuario/consultar.xhtml");
    }
    
    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public List<SelectItem> getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getFacade().findAll(), false);
    }

    public List<SelectItem> getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getFacade().findAll(), true);
    }

    public Tratamento getTratamento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
    @FacesConverter(forClass = Tratamento.class)
    public static class TratamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TratamentoController controller = (TratamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tratamentoController");
            return controller.getTratamento(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Tratamento) {
                Tratamento o = (Tratamento) object;
                return getStringKey(o.getIdtratamento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Tratamento.class.getName());
            }
        }

    }

}
