package br.com.sgdq.app.controller;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgdq.app.entity.Tratamento;
//import br.com.sgdq.app.service.TratamentoService;

/**
*
* @author Antonio Augusto
*/
@ManagedBean
@SessionScoped
public class ManterTratamentoController {

	public ManterTratamentoController() {
		
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		ManterProntuarioController manterProntuarioController = (ManterProntuarioController) httpSession.getAttribute("manterProntuarioController");
		
		tratamento = new Tratamento();
		Date dataDeInclusao = new Date(System.currentTimeMillis());
		
		//atamento.setProntuario(manterProntuarioController.getProntuarioSelecionado());
		//.setDataDeInclusao(dataDeInclusao);
		
	}

	private Tratamento tratamento;
	private List<Tratamento> tratamentos;
	
	@Autowired
//	TratamentoService tratamentoService;
	
	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public List<Tratamento> getTratamentos() {
		return tratamentos;
	}

	public void setTratamentos(List<Tratamento> tratamentos) {
		this.tratamentos = tratamentos;
	}
	
	public void incluir() {
		
//		tratamentoService.incluir(tratamento);
		tratamento = new Tratamento();
		
	}

}
