package br.com.sgdq.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgdq.app.entity.Paciente;
import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.entity.Responsavel;
//import br.com.sgdq.app.service.PacienteService;
//import br.com.sgdq.app.service.ProntuarioService;
//import br.com.sgdq.app.service.ResponsavelService;

@Named(value = "manterProntuarioController")
@ManagedBean
@SessionScoped
public class ManterProntuarioController {

	private String cpf;
	private String nome;
	private int id;
	private Prontuario prontuario;
	private Prontuario prontuarioSelecionado;
	private List<Prontuario> prontuarios;
	private Paciente idPaciente;
	private Responsavel idResponsavel;

//	@Autowired
//	ProntuarioService prontuarioService;
//
//	@Autowired
//	PacienteService pacienteService;
//
//	@Autowired
//	ResponsavelService responsavelService;

	
	public ManterProntuarioController() {

		Date dataDeInclusao = new Date(System.currentTimeMillis());
		prontuario = new Prontuario();
//		idPaciente = new Paciente();
//		idResponsavel = new Responsavel();
		prontuarios = new ArrayList<Prontuario>();

//		idPaciente.setIdResponsavel(idResponsavel);
//		prontuario.setIdPaciente(idPaciente);
		//prontuario.setResponsavel(idResponsavel);
		//prontuario.setDataDeInclusao(dataDeInclusao);

	}

	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}

	public Prontuario getProntuarioSelecionado() {
		return prontuarioSelecionado;
	}

	public void setProntuarioSelecionado(Prontuario prontuarioSelecionado) {
		this.prontuarioSelecionado = prontuarioSelecionado;
	}

	public List<Prontuario> getProntuarios() {
		return prontuarios;
	}

	public void setProntuarios(List<Prontuario> prontuarios) {
		this.prontuarios = prontuarios;
	}

	public Paciente getPaciente() {
		return idPaciente;
	}

	public void setPaciente(Paciente paciente) {
		this.idPaciente = paciente;
	}

	public Responsavel getResponsavel() {
		return idResponsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.idResponsavel = responsavel;
	}

	public String detalhar() {
		return "/pages/usuario/manterprontuario/detalhar?faces-redirect=true";
	}

	public void incluir() {

//		pacienteService.incluir(idPaciente);
//		responsavelService.incluir(idResponsavel);
//		prontuarioService.incluir(prontuario);
//		prontuario = new Prontuario();

	}

	public void consultar() {
//		prontuarios = prontuarioService.consultar(id, nome, cpf);
	}

}
