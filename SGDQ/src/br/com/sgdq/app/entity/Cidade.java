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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "\"Cidade\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_cidade_seq", sequenceName = "dbo.id_cidade_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c"),
    @NamedQuery(name = "Cidade.findByIdCidade", query = "SELECT c FROM Cidade c WHERE c.idCidade = :idCidade"),
    @NamedQuery(name = "Cidade.findByNmCidade", query = "SELECT c FROM Cidade c WHERE c.nmCidade = :nmCidade")})
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY ) //strategy = GenerationType.SEQUENCE, generator = "id_cidade_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_cidade_seq")
    @Basic(optional = false)
    @Column(name = "idCidade")
    private Integer idCidade;
    
    @Column(name = "nmCidade")
    private String nmCidade;
    
    @JoinColumn(name = "idEstado", referencedColumnName = "idEstado")//, insertable = false, updatable = false )
    @ManyToOne(cascade = CascadeType.ALL) 
    private Estado idEstado;
    
//    @OneToMany(mappedBy = "idCidadeNascimento")
//    private Collection<Pessoa> pessoaCollection;
    
//    @OneToMany(mappedBy = "idCidade")
//    private Collection<Endereco> enderecoCollection;

    public Cidade() {
    	this.idEstado = new Estado();
    }

    public Cidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getNmCidade() {
        return nmCidade;
    }

    public void setNmCidade(String nmCidade) {
        this.nmCidade = nmCidade;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

//    @XmlTransient
//    public Collection<Pessoa> getPessoaCollection() {
//        return pessoaCollection;
//    }
//
//    public void setPessoaCollection(Collection<Pessoa> pessoaCollection) {
//        this.pessoaCollection = pessoaCollection;
//    }

//    @XmlTransient
//    public Collection<Endereco> getEnderecoCollection() {
//        return enderecoCollection;
//    }
//
//    public void setEnderecoCollection(Collection<Endereco> enderecoCollection) {
//        this.enderecoCollection = enderecoCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCidade != null ? idCidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.idCidade == null && other.idCidade != null) || (this.idCidade != null && !this.idCidade.equals(other.idCidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "br.com.sgdq.app.entity.Cidade[ idCidade=" + idCidade + " ]";
    	return nmCidade + " - " + idEstado.getSgEstado();
    }
    
}
