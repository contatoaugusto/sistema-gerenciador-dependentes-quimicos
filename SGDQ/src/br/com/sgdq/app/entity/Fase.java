/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio Augusto
 */
@Entity
@Table(name = "\"Fase\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_fase_seq", sequenceName = "dbo.id_pessoa_seq")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fase.findAll", query = "SELECT f FROM Fase f order by f.idfase"),
    @NamedQuery(name = "Fase.findByIdfase", query = "SELECT f FROM Fase f WHERE f.idfase = :idfase"),
    @NamedQuery(name = "Fase.findByNmfase", query = "SELECT f FROM Fase f WHERE f.nmfase = :nmfase"),
    @NamedQuery(name = "Fase.findByDsfase", query = "SELECT f FROM Fase f WHERE f.dsfase = :dsfase")})
public class Fase implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_fase_seq")
	@Basic(optional = false)
    @Column(name = "idfase")
    private Integer idfase;
    
    @Size(max = 2147483647)
    @Column(name = "nmfase")
    private String nmfase;
    
    @Size(max = 2147483647)
    @Column(name = "dsfase")
    private String dsfase;
    
//    @OneToMany(mappedBy = "idfase")
//    private Collection<FaseTratamento> faseTratamentoCollection;

    public Fase() {
    }

    public Fase(Integer idfase) {
        this.idfase = idfase;
    }

    public Integer getIdfase() {
        return idfase;
    }

    public void setIdfase(Integer idfase) {
        this.idfase = idfase;
    }

    public String getNmfase() {
        return nmfase;
    }

    public void setNmfase(String nmfase) {
        this.nmfase = nmfase;
    }

    public String getDsfase() {
        return dsfase;
    }

    public void setDsfase(String dsfase) {
        this.dsfase = dsfase;
    }

//    @XmlTransient
//    public Collection<FaseTratamento> getFaseTratamentoCollection() {
//        return faseTratamentoCollection;
//    }
//
//    public void setFaseTratamentoCollection(Collection<FaseTratamento> faseTratamentoCollection) {
//        this.faseTratamentoCollection = faseTratamentoCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfase != null ? idfase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fase)) {
            return false;
        }
        Fase other = (Fase) object;
        if ((this.idfase == null && other.idfase != null) || (this.idfase != null && !this.idfase.equals(other.idfase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Fase[ idfase=" + idfase + " ]";
    }
    
}
