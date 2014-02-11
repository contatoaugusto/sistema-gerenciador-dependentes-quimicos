/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "\"TratamentoStatus\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_tratamentostatus_seq", sequenceName = "dbo.id_tratamentostatus_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TratamentoStatus.findAll", query = "SELECT t FROM TratamentoStatus t order by t.idtratamentostatus"),
    @NamedQuery(name = "TratamentoStatus.findByIdtratamentostatus", query = "SELECT t FROM TratamentoStatus t WHERE t.idtratamentostatus = :idtratamentostatus"),
    @NamedQuery(name = "TratamentoStatus.findByNmtratamentostatus", query = "SELECT t FROM TratamentoStatus t WHERE t.nmtratamentostatus = :nmtratamentostatus"),
    @NamedQuery(name = "TratamentoStatus.findByDstratamentostatus", query = "SELECT t FROM TratamentoStatus t WHERE t.dstratamentostatus = :dstratamentostatus")})
public class TratamentoStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_tratamentostatus_seq")
    @Basic(optional = false)
    @Column(name = "idtratamentostatus")
    private Integer idtratamentostatus;
    
    @Size(max = 2147483647)
    @Column(name = "nmtratamentostatus")
    private String nmtratamentostatus;
    
    @Size(max = 2147483647)
    @Column(name = "dstratamentostatus")
    private String dstratamentostatus;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtratamentostatus")
//    private Collection<Tratamento> tratamentoCollection;

    public TratamentoStatus() {
    }

    public TratamentoStatus(Integer idtratamentostatus) {
        this.idtratamentostatus = idtratamentostatus;
    }

    public Integer getIdtratamentostatus() {
        return idtratamentostatus;
    }

    public void setIdtratamentostatus(Integer idtratamentostatus) {
        this.idtratamentostatus = idtratamentostatus;
    }

    public String getNmtratamentostatus() {
        return nmtratamentostatus;
    }

    public void setNmtratamentostatus(String nmtratamentostatus) {
        this.nmtratamentostatus = nmtratamentostatus;
    }

    public String getDstratamentostatus() {
        return dstratamentostatus;
    }

    public void setDstratamentostatus(String dstratamentostatus) {
        this.dstratamentostatus = dstratamentostatus;
    }

//    @XmlTransient
//    public Collection<Tratamento> getTratamentoCollection() {
//        return tratamentoCollection;
//    }
//
//    public void setTratamentoCollection(Collection<Tratamento> tratamentoCollection) {
//        this.tratamentoCollection = tratamentoCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtratamentostatus != null ? idtratamentostatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TratamentoStatus)) {
            return false;
        }
        TratamentoStatus other = (TratamentoStatus) object;
        if ((this.idtratamentostatus == null && other.idtratamentostatus != null) || (this.idtratamentostatus != null && !this.idtratamentostatus.equals(other.idtratamentostatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "br.com.sgdq.app.entity.TratamentoStatus[ idtratamentostatus=" + idtratamentostatus + " ]";
    	return nmtratamentostatus;
    }
    
}
