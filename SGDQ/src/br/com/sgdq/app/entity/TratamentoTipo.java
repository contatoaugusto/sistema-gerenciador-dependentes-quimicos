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
@Table(name = "\"TratamentoTipo\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_tratamentotipo_seq", sequenceName = "dbo.id_tratamentotipo_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TratamentoTipo.findAll", query = "SELECT t FROM TratamentoTipo t"),
    @NamedQuery(name = "TratamentoTipo.findByIdtratamentotipo", query = "SELECT t FROM TratamentoTipo t WHERE t.idtratamentotipo = :idtratamentotipo"),
    @NamedQuery(name = "TratamentoTipo.findByNmtratamentotipo", query = "SELECT t FROM TratamentoTipo t WHERE t.nmtratamentotipo = :nmtratamentotipo"),
    @NamedQuery(name = "TratamentoTipo.findByDstratamentotipo", query = "SELECT t FROM TratamentoTipo t WHERE t.dstratamentotipo = :dstratamentotipo")})
public class TratamentoTipo implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_tratamentotipo_seq")
	@Basic(optional = false)
    @Column(name = "idtratamentotipo")
    private Integer idtratamentotipo;
    
	@Size(max = 2147483647)
    @Column(name = "nmtratamentotipo")
    private String nmtratamentotipo;
    
	@Size(max = 2147483647)
    @Column(name = "dstratamentotipo")
    private String dstratamentotipo;
    
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idtratamentotipo")
//    private Collection<Tratamento> tratamentoCollection;

    public TratamentoTipo() {
    }

    public TratamentoTipo(Integer idtratamentotipo) {
        this.idtratamentotipo = idtratamentotipo;
    }

    public Integer getIdtratamentotipo() {
        return idtratamentotipo;
    }

    public void setIdtratamentotipo(Integer idtratamentotipo) {
        this.idtratamentotipo = idtratamentotipo;
    }

    public String getNmtratamentotipo() {
        return nmtratamentotipo;
    }

    public void setNmtratamentotipo(String nmtratamentotipo) {
        this.nmtratamentotipo = nmtratamentotipo;
    }

    public String getDstratamentotipo() {
        return dstratamentotipo;
    }

    public void setDstratamentotipo(String dstratamentotipo) {
        this.dstratamentotipo = dstratamentotipo;
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
        hash += (idtratamentotipo != null ? idtratamentotipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TratamentoTipo)) {
            return false;
        }
        TratamentoTipo other = (TratamentoTipo) object;
        if ((this.idtratamentotipo == null && other.idtratamentotipo != null) || (this.idtratamentotipo != null && !this.idtratamentotipo.equals(other.idtratamentotipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "br.com.sgdq.app.entity.TratamentoTipo[ idtratamentotipo=" + idtratamentotipo + " ]";
    	return nmtratamentotipo;
    }
    
}
