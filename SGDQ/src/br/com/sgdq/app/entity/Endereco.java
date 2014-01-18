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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio Augusto
 */
@Entity
//@Indexed
@Table(name = "\"Endereco\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_endereco_seq", sequenceName = "dbo.id_endereco_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
    @NamedQuery(name = "Endereco.findByIdEndereco", query = "SELECT e FROM Endereco e WHERE e.idEndereco = :idEndereco"),
    @NamedQuery(name = "Endereco.findByDsEndereco", query = "SELECT e FROM Endereco e WHERE e.dsEndereco = :dsEndereco"),
    @NamedQuery(name = "Endereco.findByNmBairro", query = "SELECT e FROM Endereco e WHERE e.nmBairro = :nmBairro"),
    @NamedQuery(name = "Endereco.findByNuCEP", query = "SELECT e FROM Endereco e WHERE e.nuCEP = :nuCEP"),
    @NamedQuery(name = "Endereco.findByNuResidencia", query = "SELECT e FROM Endereco e WHERE e.nuResidencia = :nuResidencia")})
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_endereco_seq")
    @Basic(optional = false)
    @Column(name = "idEndereco")
    private Integer idEndereco;
    
    @Column(name = "dsEndereco")
    private String dsEndereco;
    
    @Column(name = "nmBairro")
    private String nmBairro;
    
    @Column(name = "nuCEP")
    private String nuCEP;
    
    @Column(name = "nuResidencia")
    private Integer nuResidencia;
    
//    @JoinColumn(name = "idCidade", referencedColumnName = "idCidade")//, insertable = false, updatable = false)
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Cidade idCidade;
    @Column(name = "idCidade")
    private String idCidade;

    public Endereco() {
    	//this.idCidade = new Cidade();
    }

    public Endereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public String getNmBairro() {
        return nmBairro;
    }

    public void setNmBairro(String nmBairro) {
        this.nmBairro = nmBairro;
    }

    public String getNuCEP() {
        return nuCEP;
    }

    public void setNuCEP(String nuCEP) {
        this.nuCEP = nuCEP;
    }

    public Integer getNuResidencia() {
        return nuResidencia;
    }

    public void setNuResidencia(Integer nuResidencia) {
        this.nuResidencia = nuResidencia;
    }

    public String getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(String idCidade) {
        this.idCidade = idCidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEndereco != null ? idEndereco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.idEndereco == null && other.idEndereco != null) || (this.idEndereco != null && !this.idEndereco.equals(other.idEndereco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Endereco[ idEndereco=" + idEndereco + " ]";
    }
    
}
