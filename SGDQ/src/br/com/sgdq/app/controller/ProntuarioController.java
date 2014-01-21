package br.com.sgdq.app.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.controller.util.PaginationHelper;
import br.com.sgdq.app.entity.Cidade;
import br.com.sgdq.app.entity.Estado;
import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.facade.ProntuarioFacade;
import controleacesso.web.modelo.Usuario;
//import javax.enterprise.context.SessionScoped;

@Named(value = "prontuarioController")
@ViewScoped
@ManagedBean
public class ProntuarioController implements Serializable {

    private Prontuario prontuario;
    private DataModel items = null;
    @EJB
	private ProntuarioFacade prontuarioFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private Estado tbEstadoOrgaoEmissor, tbEstadoOrgaoEmissorResponsavel;
    private Cidade tbCidadeNascimentoPaciente, tbCidadeEnderecoPaciente, tbCidadeEnderecoResponsavel, tbCidadeNascimentoResponsavel; 
        
    public Cidade getTbCidadeNascimentoResponsavel() {
		return tbCidadeNascimentoResponsavel;
	}

	public void setTbCidadeNascimentoResponsavel(
			Cidade tbCidadeNascimentoResponsavel) {
		this.tbCidadeNascimentoResponsavel = tbCidadeNascimentoResponsavel;
	}

	public Estado getTbEstadoOrgaoEmissorResponsavel() {
		return tbEstadoOrgaoEmissorResponsavel;
	}

	public void setTbEstadoOrgaoEmissorResponsavel(
			Estado tbEstadoOrgaoEmissorResponsavel) {
		this.tbEstadoOrgaoEmissorResponsavel = tbEstadoOrgaoEmissorResponsavel;
	}

	public Cidade getTbCidadeEnderecoResponsavel() {
		return tbCidadeEnderecoResponsavel;
	}

	public void setTbCidadeEnderecoResponsavel(Cidade tbCidadeEnderecoResponsavel) {
		this.tbCidadeEnderecoResponsavel = tbCidadeEnderecoResponsavel;
	}

	public Cidade getTbCidadeEnderecoPaciente() {
		return tbCidadeEnderecoPaciente;
	}

	public void setTbCidadeEnderecoPaciente(Cidade tbCidadeEnderecoPaciente) {
		this.tbCidadeEnderecoPaciente = tbCidadeEnderecoPaciente;
	}

	public Cidade getTbCidadeNascimentoPaciente() {
		return tbCidadeNascimentoPaciente;
	}

	public void setTbCidadeNascimentoPaciente(Cidade tbCidadeNascimentoPaciente) {
		this.tbCidadeNascimentoPaciente = tbCidadeNascimentoPaciente;
	}

	public Estado getTbEstadoOrgaoEmissor() {
		return tbEstadoOrgaoEmissor;
	}

	public void setTbEstadoOrgaoEmissor(Estado tbEstadoOrgaoEmissor) {
		this.tbEstadoOrgaoEmissor = tbEstadoOrgaoEmissor;
	}

	public ProntuarioController() {
    }

    public Prontuario getSelected() {
        if (prontuario == null) {
            prontuario = new Prontuario();
            selectedItemIndex = -1;
        }
        return prontuario;
    }

    private ProntuarioFacade getFacade() {
    	if (prontuarioFacade == null)
    		prontuarioFacade = new ProntuarioFacade();
    	return prontuarioFacade;
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

    @PostConstruct
    public void init() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String idProntuarioParam = facesContext.getExternalContext().getRequestParameterMap().get("idProntuario");
		if (idProntuarioParam != null && !idProntuarioParam.isEmpty()){
			prontuario = getFacade().find(new Integer(idProntuarioParam));
		}
	}
    
    /**
     * Pesquisar prontuario de acordo com parâmetros
     * @return PaginationHelper
     */
    public DataModel pesquisarProntuario() {
        
    	if ((getSelected().getIdProntuario() == null || getSelected().getIdProntuario() == 0) && 
    			(getSelected().getIdPaciente().getIdPessoa().getNmPessoa() == null || getSelected().getIdPaciente().getIdPessoa().getNmPessoa().isEmpty()) &&
    			(getSelected().getIdPaciente().getIdPessoa().getNuCPF() == null || getSelected().getIdPaciente().getIdPessoa().getNuCPF().isEmpty())){
    		items = null;
    	}else
	    	items = new ListDataModel(getFacade().findByProntuarioCPFNomePaciente(
		    		getSelected().getIdProntuario(),
		    		getSelected().getIdPaciente().getIdPessoa().getNmPessoa(), 
		    		getSelected().getIdPaciente().getIdPessoa().getNuCPF()));
	    	
    	 
    	return items;
    }
    
    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        prontuario = (Prontuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareIncluirTratamento() throws IOException {
    	prontuario = null;
    	prontuario = (Prontuario) getItems().getRowData();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/pages/mantertratamento/cadastrarTratamento.xhtml?idProntuario="+prontuario.getIdProntuario());
    }
    
    public String prepareCreate() {
        prontuario = new Prontuario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
        	
        	Logger logger = Logger.getLogger("ProntuarioController - Criando novo prontuário");
        	
        	prontuario.setDtProntuarioInicio(new Date());
        	
        	// Recuperar usuário logado na sessão Que foi criada na classe do componeten controleAcesso.jar
        	FacesContext facesContext = FacesContext.getCurrentInstance(); 
        	HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();  
			HttpSession httpSession = request.getSession(false);  
			Usuario usuario = (Usuario) httpSession.getAttribute("usuario");  
			getSelected().setIdUsuarioCadastro(usuario.getIdUsuario());	
        	
        	getFacade().create(prontuario);
        	
        	JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/message").getString("sistema.inclusao"));
            return "consultar";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/message").getString("sistema.inclusao.erro"));
            return null;
        }
    }

    public String prepareEdit() {
        prontuario = (Prontuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/pages/manterprontuario/cadastrar.xhtml?faces-redirect=true&idProntuario="+prontuario.getIdProntuario();
    }

    public void buscaProntuarioBynuCPFPaciente(AjaxBehaviorEvent actionEvent) {
        Prontuario prontuarioTemp = prontuario;
    	prontuario = getFacade().findBynuCPFPaciente(getSelected().getIdPaciente().getIdPessoa().getNuCPF());
        if (prontuario != null)
        	JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/message").getString("prontuario.paciente.cpf_existente"));
        else 
        	prontuario = prontuarioTemp;
        
        prontuarioTemp = null;
    }
    
    public String update() {
        try {
            getFacade().edit(prontuario);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/message").getString("sistema.alteracao"));
            return "cadastrar";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/message").getString("sistema.alteracao.erro"));
            return null;
        }
    }

    public String destroy() {
        prontuario = (Prontuario) getItems().getRowData();
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
            getFacade().remove(prontuario);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProntuarioDeleted"));
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
            prontuario = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
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
        return JsfUtil.getSelectItems(prontuarioFacade.findAll(), false);
    }

    public List<SelectItem>  getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(prontuarioFacade.findAll(), true);
    }

    public Prontuario getProntuario(java.lang.Integer id) {
        return prontuarioFacade.find(id);
    }

    @FacesConverter(forClass = Prontuario.class)
    public static class ProntuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProntuarioController controller = (ProntuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "prontuarioController");
            return controller.getProntuario(getKey(value));
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
            if (object instanceof Prontuario) {
                Prontuario o = (Prontuario) object;
                return getStringKey(o.getIdProntuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Prontuario.class.getName());
            }
        }

    }

}
