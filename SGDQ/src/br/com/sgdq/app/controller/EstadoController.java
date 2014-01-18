package br.com.sgdq.app.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
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

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.controller.util.PaginationHelper;
import br.com.sgdq.app.entity.Estado;
import br.com.sgdq.app.entity.FaseTratamento;
import br.com.sgdq.app.facade.EstadoFacade;

@Named("estadoController")
@SessionScoped
@ManagedBean
public class EstadoController implements Serializable {

    private Estado current;
    private DataModel items = null;
    @EJB
    private br.com.sgdq.app.facade.EstadoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private List<Estado> estadoList;

    public EstadoController() {
    }
    
    @PostConstruct
    public void init() {
		
    	estadoList = getFacade().findAll();
	}

    public Estado getSelected() {
        if (current == null) {
            current = new Estado();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EstadoFacade getFacade() {
        if (ejbFacade == null)
        	ejbFacade = new EstadoFacade();
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
        current = (Estado) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Estado();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstadoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Estado) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstadoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Estado) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstadoDeleted"));
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

    public List<SelectItem>  getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getFacade().findAll(), false);
    }

    public List<SelectItem> getItemsAvailableSelectOne() {
    	//return JsfUtil.getSelectItems(estadoService.list(), true);
    	return JsfUtil.getSelectItems(getFacade().findAll(), true);
    }

    public Estado getEstado(java.lang.Integer id) {
        //return estadoService.getObjDao(id);
        return getFacade().find(id);
    }

    
    public List<Estado> autocomplete(String query) {    
        List<Estado> sugestao = new ArrayList<Estado>();    
        Estado estado;  
          
        for(int i=0;i<estadoList.size();i++){  
        	estado = estadoList.get(i);  
            if(estado.getNmEstado().toLowerCase().startsWith(query.toLowerCase()))  
                sugestao.add(estado);  
        }  
        return sugestao;    
    } 
    
    
    @FacesConverter(forClass = Estado.class, value="estadoControllerConverter")
    public static class EstadoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstadoController controller = (EstadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estadoController");
            return controller.getEstado(getKey(value));
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
            if (object instanceof Estado) {
                Estado o = (Estado) object;
                return getStringKey(o.getIdEstado());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Estado.class.getName());
            }
        }

    }

}
