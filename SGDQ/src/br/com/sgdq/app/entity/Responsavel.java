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
@Table(name = "\"Responsavel\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_responsavel_seq", sequenceName = "dbo.id_responsavel_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Responsavel.findAll", query = "SELECT r FROM Responsavel r"),
    @NamedQuery(name = "Responsavel.findByIdResponsavel", query = "SELECT r FROM Responsavel r WHERE r.idResponsavel = :idResponsavel"),
    @NamedQuery(name = "Responsavel.findByNmVinculoFamiliar", query = "SELECT r FROM Responsavel r WHERE r.nmVinculoFamiliar = :nmVinculoFamiliar"),
    @NamedQuery(name = "Responsavel.findByVrRendaFamiliar", query = "SELECT r FROM Responsavel r WHERE r.vrRendaFamiliar = :vrRendaFamiliar"),
    @NamedQuery(name = "Responsavel.findByDsObservacao", query = "SELECT r FROM Responsavel r WHERE r.dsObservacao = :dsObservacao")})
public class Responsavel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_responsavel_seq")
    @Basic(optional = false)
    @Column(name = "idResponsavel")
    private Integer idResponsavel;
    
    @Column(name = "nmVinculoFamiliar")
    private String nmVinculoFamiliar;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vrRendaFamiliar")
    private Double vrRendaFamiliar;
    
    @Column(name = "dsObservacao")
    private String dsObservacao;
    
    @JoinColumn(name = "idPessoa", referencedColumnName = "idPessoa")
    @ManyToOne(cascade = CascadeType.ALL)
    private Pessoa idPessoa;
    
//    @OneToMany(mappedBy = "idResponsavel")
//    private Collection<Prontuario> prontuarioCollection;

    public Responsavel() {
    	this.idPessoa = new Pessoa();
    }

    public Responsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getNmVinculoFamiliar() {
        return nmVinculoFamiliar;
    }

    public void setNmVinculoFamiliar(String nmVinculoFamiliar) {
        this.nmVinculoFamiliar = nmVinculoFamiliar;
    }

    public Double getVrRendaFamiliar() {
        return vrRendaFamiliar;
    }

    public void setVrRendaFamiliar(Double vrRendaFamiliar) {
        this.vrRendaFamiliar = vrRendaFamiliar;
    }

    public String getDsObservacao() {
        return dsObservacao;
    }

    public void setDsObservacao(String dsObservacao) {
        this.dsObservacao = dsObservacao;
    }

    public Pessoa getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Pessoa idPessoa) {
        this.idPessoa = idPessoa;
    }

//    @XmlTransient
//    public Collection<Prontuario> getProntuarioCollection() {
//        return prontuarioCollection;
//    }
//
//    public void setProntuarioCollection(Collection<Prontuario> prontuarioCollection) {
//        this.prontuarioCollection = prontuarioCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResponsavel != null ? idResponsavel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Responsavel)) {
            return false;
        }
        Responsavel other = (Responsavel) object;
        if ((this.idResponsavel == null && other.idResponsavel != null) || (this.idResponsavel != null && !this.idResponsavel.equals(other.idResponsavel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Responsavel[ idResponsavel=" + idResponsavel + " ]";
    }
    
}
