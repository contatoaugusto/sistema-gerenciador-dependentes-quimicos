/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio Augusto
 */
@Entity
//@Indexed
@Table(name = "\"Estado\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_estado_seq", sequenceName = "dbo.id_estado_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
    @NamedQuery(name = "Estado.findByIdEstado", query = "SELECT e FROM Estado e WHERE e.idEstado = :idEstado"),
    @NamedQuery(name = "Estado.findByNmEstado", query = "SELECT e FROM Estado e WHERE e.nmEstado = :nmEstado"),
    @NamedQuery(name = "Estado.findBySgEstado", query = "SELECT e FROM Estado e WHERE e.sgEstado = :sgEstado"),
    @NamedQuery(name = "Estado.findByIdPais", query = "SELECT e FROM Estado e WHERE e.idPais = :idPais")})
public class Estado implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_estado_seq")
	@Basic(optional = false)
    @Column(name = "idEstado")
    private Integer idEstado;
    
	@Size(max = 2147483647)
    @Column(name = "nmEstado")
    private String nmEstado;
    
	@Size(max = 2147483647)
    @Column(name = "sgEstado")
    private String sgEstado;
    
	@Column(name = "idPais")
    private Integer idPais;

    public Estado() {
    }

    public Estado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNmEstado() {
        return nmEstado;
    }

    public void setNmEstado(String nmEstado) {
        this.nmEstado = nmEstado;
    }

    public String getSgEstado() {
        return sgEstado;
    }

    public void setSgEstado(String sgEstado) {
        this.sgEstado = sgEstado;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "br.com.sgdq.app.entity.Estado[ idEstado=" + idEstado + " ]";
    	return nmEstado;
    }
    
}
