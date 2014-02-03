/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio Augusto
 */
@Entity
@Table(name = "tbusuario", schema = "dbo")
@SequenceGenerator(name = "dbo.id_usuario_seq", sequenceName = "dbo.id_usuario_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioSGDQ.findAll", query = "SELECT u FROM UsuarioSGDQ u"),
    @NamedQuery(name = "UsuarioSGDQ.findByIdUsuario", query = "SELECT u FROM UsuarioSGDQ u WHERE u.id_usuario = :id_usuario"),
    @NamedQuery(name = "UsuarioSGDQ.findByNmLogin", query = "SELECT u FROM UsuarioSGDQ u WHERE u.nm_login = :nm_login")})
public class UsuarioSGDQ implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_usuario_seq")
	@Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer id_usuario;
    
	@Size(max = 2147483647)
    @Column(name = "nm_login")
    private String nm_login;
    
	@Size(max = 2147483647)
    @Column(name = "ds_senha")
    private String ds_senha;
    
    @Column(name = "fg_ativo")
    private boolean fg_ativo;
	
    @OneToOne (cascade= CascadeType.REFRESH)
    @JoinColumn(name="id_pessoa", referencedColumnName = "idpessoa", insertable=false, updatable=false)
    public Pessoa pessoa; //unidirectional
    
	
    public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNm_login() {
		return nm_login;
	}

	public void setNm_login(String nm_login) {
		this.nm_login = nm_login;
	}

	public String getDs_senha() {
		return ds_senha;
	}

	public void setDs_senha(String ds_senha) {
		this.ds_senha = ds_senha;
	}

	public boolean isFg_ativo() {
		return fg_ativo;
	}

	public void setFg_ativo(boolean fg_ativo) {
		this.fg_ativo = fg_ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id_usuario != null ? id_usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioSGDQ)) {
            return false;
        }
        UsuarioSGDQ other = (UsuarioSGDQ) object;
        if ((this.id_usuario == null && other.id_usuario != null) || (this.id_usuario != null && !this.id_usuario.equals(other.id_usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "br.com.sgdq.app.entity.Estado[ idEstado=" + idEstado + " ]";
    	return nm_login;
    }
    
}
