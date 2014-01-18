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

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.controller.util.PaginationHelper;
import br.com.sgdq.app.entity.Cidade;
import br.com.sgdq.app.entity.Estado;
import br.com.sgdq.app.facade.CidadeFacade;

@Named("cidadeController")
@SessionScoped
@ManagedBean
public class CidadeController implements Serializable {

    private Cidade current;
    private DataModel items = null;
    @EJB
    private br.com.sgdq.app.facade.CidadeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Cidade> cidadeList;

    public CidadeController() {
    }
    
    @PostConstruct
    public void init() {
		cidadeList = getFacade().findAll();
	}

    public Cidade getSelected() {
        if (current == null) {
            current = new Cidade();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CidadeFacade getFacade() {
    	if (ejbFacade == null)
        	ejbFacade = new CidadeFacade();
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
        current = (Cidade) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cidade();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CidadeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cidade) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CidadeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cidade) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CidadeDeleted"));
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

    public List<SelectItem>  getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getFacade().findAll(), true);
    }

    public Cidade getCidade(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Cidade> autocomplete(String query) {    
        List<Cidade> sugestao = new ArrayList<Cidade>();    
        Cidade cidade;  
          
        for(int i=0;i<cidadeList.size();i++){  
        	cidade = cidadeList.get(i);  
            if(cidade.getNmCidade().toLowerCase().startsWith(query.toLowerCase()))  
                sugestao.add(cidade);  
        }  
        return sugestao;    
    }
    
    //@FacesConverter(forClass = Cidade.class)
    @FacesConverter(forClass = Estado.class, value="cidadeControllerConverter")
    public static class CidadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CidadeController controller = (CidadeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cidadeController");
            return controller.getCidade(getKey(value));
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
            if (object instanceof Cidade) {
                Cidade o = (Cidade) object;
                return getStringKey(o.getIdCidade());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cidade.class.getName());
            }
        }

    }

}
