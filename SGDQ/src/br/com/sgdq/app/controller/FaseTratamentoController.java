package br.com.sgdq.app.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import controleacesso.web.modelo.Usuario;

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.controller.util.PaginationHelper;
import br.com.sgdq.app.entity.FaseTratamento;
import br.com.sgdq.app.facade.FaseTratamentoFacade;

//@Named("faseTratamentoController")
@ManagedBean (name = "faseTratamentoController")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@SessionScoped
public class FaseTratamentoController implements Serializable {

    private FaseTratamento current;
    private DataModel items = null;
    @EJB
    private br.com.sgdq.app.facade.FaseTratamentoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Usuario usuario;
    private Integer idTratamento;
    private String dsmotivofasetratamento;
    
    public Integer getIdTratamento() {
		return idTratamento;
	}

	public void setIdTratamento(Integer idTratamento) {
		this.idTratamento = idTratamento;
	}

	public String getDsmotivofasetratamento() {
		return dsmotivofasetratamento;
	}

	public void setDsmotivofasetratamento(String dsmotivofasetratamento) {
		this.dsmotivofasetratamento = dsmotivofasetratamento;
	}

	private List<FaseTratamento> faseTratamentoList;

    public FaseTratamentoController() {
    }

    public FaseTratamento getSelected() {
        if (current == null) {
            current = new FaseTratamento();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FaseTratamentoFacade getFacade() {
        if (ejbFacade == null)
        	ejbFacade = new FaseTratamentoFacade ();
    	return ejbFacade;
    }

    @PostConstruct
    public void init() { 
    	loadUsuarioSessao();
    }
    
    public void addFaseTratamento (){
    	
    	if (dsmotivofasetratamento == null || dsmotivofasetratamento.isEmpty())
    		dsmotivofasetratamento = "Criado automaticamente por incremento a partir da tela manter tratamento";
    	
    	int nuFase = 1;
    	
    	faseTratamentoList = getFacade().findByTratamento(idTratamento);
    	
    	// Encerrar Tratamento anterior
    	if (!faseTratamentoList.isEmpty()){
    		current =  faseTratamentoList.get(faseTratamentoList.size()-1);
    		
    		if (current.getDtfasetratamentofim() == null){
        		// atualizar a data de vencimento da fase anterior
    			current.setDtfasetratamentofim(new Date());
    			getFacade().edit(current);
    			JsfUtil.addSuccessMessage("Fase " + current.getIdfase() + " finalizada com sucesso");
    		}
    		nuFase = current.getIdfase() + 1;
    	}
//    	else
//    		dsFaseTratamento = "Criado automaticamente na inclusão de tratamento. Inicio de tratamento por internação";
    	
    	// Sistema idealizado prater apenas 3 fases
    	if (nuFase <= 3){
	    	current = new FaseTratamento();
	    	current.setIdtratamento(idTratamento); // Tratamento que foi criado nesse instante
	    	current.setIdfase(nuFase);
	    	current.setDsmotivofasetratamento(dsmotivofasetratamento);
	    	current.setIdusuario(usuario.getIdUsuario());
	    	current.setDtfasetratamentonicio(new Date());
			
			FaseTratamentoFacade ejbFacadeTratamentoFase = new FaseTratamentoFacade();
			ejbFacadeTratamentoFase.create(current);
			
	        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/message").getString("fasetratamento.inclusao"));
    	}else
    		JsfUtil.addSuccessMessage("Tratamento ja atingiu o número máximo de fases igual a "+ nuFase);
    		
    }
    
	private void loadUsuarioSessao() {
		// Recuperar usuário logado na sessão Que foi criada na classe do componeten controleAcesso.jar
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();  
		HttpSession httpSession = request.getSession(false);  
		usuario = (Usuario) httpSession.getAttribute("usuario");  
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (FaseTratamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new FaseTratamento();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FaseTratamentoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (FaseTratamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FaseTratamentoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (FaseTratamento) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FaseTratamentoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
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

    public FaseTratamento getFaseTratamento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = FaseTratamento.class)
    public static class FaseTratamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FaseTratamentoController controller = (FaseTratamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "faseTratamentoController");
            return controller.getFaseTratamento(getKey(value));
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
            if (object instanceof FaseTratamento) {
                FaseTratamento o = (FaseTratamento) object;
                return getStringKey(o.getIdfasetratamento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FaseTratamento.class.getName());
            }
        }

    }

}
