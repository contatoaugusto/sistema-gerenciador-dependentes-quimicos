package br.com.sgdq.app.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgdq.app.entity.Usuario;

@ManagedBean
@SessionScoped
public class EfetuarLoginController {
	
	public EfetuarLoginController() {
		usuario = new Usuario();
	}
	
	private Usuario usuario;
	private String cpf;
	private String senha;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
