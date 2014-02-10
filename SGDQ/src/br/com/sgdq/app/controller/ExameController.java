package br.com.sgdq.app.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.controller.util.PaginationHelper;
import br.com.sgdq.app.entity.Exame;
import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.entity.Tratamento;
import br.com.sgdq.app.facade.ExameFacade;
import br.com.sgdq.app.facade.TratamentoFacade;

@Named("exameController")
@ViewScoped
@ManagedBean
public class ExameController implements Serializable {

    private Exame current;
    private DataModel items = null;
    @EJB
    private br.com.sgdq.app.facade.ExameFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Integer idTratamento;
    
    private List<Exame> exameList;

    @PostConstruct
    public void init() {
    	getExameByTratamento();
    }
    
    public List<Exame> getExameList() {
		return exameList;
	}

	public void setExameList(List<Exame> exameList) {
		this.exameList = exameList;
	}

	public ExameController() {
    }

    public Exame getSelected() {
        if (current == null) {
            current = new Exame();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ExameFacade getFacade() {
        if (ejbFacade == null)
        	ejbFacade = new ExameFacade();
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
        current = (Exame) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Exame();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExameCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Exame) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExameUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Exame) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExameDeleted"));
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

    public void onEdit(CellEditEvent event) {  
    	
    	Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
      
        if(newValue != null && !newValue.equals(oldValue)) {  

            DataTable s = (DataTable) event.getSource();
            Exame exame = (Exame) s.getRowData();

            getFacade().edit(exame);
            
            JsfUtil.addSuccessMessage("Exame atualizado com sucesso");
            
        }	
        //Exame exame = (Exame) event.getObject();  
    }
    
    public void onBlur(AjaxBehaviorEvent actionEvent) {
    	
    	FacesContext context = FacesContext.getCurrentInstance();
    	Exame exame = (Exame) context.getApplication().evaluateExpressionGet(context, "#{exame}", Exame.class);
    	
    	
    	UIInput input = (UIInput) actionEvent.getComponent();
    	
    	if (input.getId().equalsIgnoreCase("input_exame_nome"))
    		exame.setNmexame((String) input.getValue());
    	else if (input.getId().equalsIgnoreCase("input_exame_data"))
    		exame.setDtexame((Date) input.getValue());
    	else if (input.getId().equalsIgnoreCase("input_tratamento_resultado_exames"))
    		exame.setDsresultado((String) input.getValue());
    	
        getFacade().edit(exame);
        
        getExameByTratamento();
        
        //JsfUtil.addSuccessMessage("Exame atualizado com sucesso");
    }
    
    
    /**
     * 
     * @param idTratamento
     * @return
     */
    public List<Exame> getExameByTratamento() {
    	
    	FacesContext facesContext = FacesContext.getCurrentInstance(); 
    	TratamentoController tratamentoController = (TratamentoController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "tratamentoController");
    	
    	if (this.idTratamento == null || this.idTratamento == 0)
    		this.idTratamento = tratamentoController.getSelected().getIdtratamento();
    	
    	if (this.idTratamento == null){
    		exameList = null;
    	}else{	
    		TratamentoFacade tratamentoFacade = new TratamentoFacade();
	    	Tratamento tratamento = tratamentoFacade.find(this.idTratamento);
	    	exameList = getFacade().findByTratamento(tratamento);
	    	if (exameList.size() == 0){
	    		addExame(tratamento);
	    		exameList = getFacade().findByTratamento(tratamento);
	    	}
    	}
    		
    	return exameList;
    }
    
    
    /**
     * Criar os 6 exames predefinidos
	 * Foi definido dessa forma pelo fato da documentão não preve um requisito incluir exame, segundo os componentes do grupo    
     */
	public void addExame(Tratamento tratamento) {
		int i = 1;
		while (i <= 6){
			Exame exame = new Exame();
			exame.setNmexame("Exame " + i);
			exame.setIdtratamento(tratamento);
			getFacade().create(exame);
			i++;
		}
	}
    
    
    public List<SelectItem> getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getFacade().findAll(), false);
    }

    public List<SelectItem> getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getFacade().findAll(), true);
    }

    public Exame getExame(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Exame.class)
    public static class ExameControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExameController controller = (ExameController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "exameController");
            return controller.getExame(getKey(value));
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
            if (object instanceof Exame) {
                Exame o = (Exame) object;
                return getStringKey(o.getIdexame());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Exame.class.getName());
            }
        }

    }

}
