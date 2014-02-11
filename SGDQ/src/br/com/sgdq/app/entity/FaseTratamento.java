/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio Augusto
 */
@Entity
@Table(name = "\"FaseTratamento\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_fasetratamento_seq", sequenceName = "dbo.id_fasetratamento_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FaseTratamento.findAll", query = "SELECT f FROM FaseTratamento f order by f.idfasetratamento"),
    @NamedQuery(name = "FaseTratamento.findByIdfasetratamento", query = "SELECT f FROM FaseTratamento f WHERE f.idfasetratamento = :idfasetratamento"),
    @NamedQuery(name = "FaseTratamento.findByDtfasetratamentonicio", query = "SELECT f FROM FaseTratamento f WHERE f.dtfasetratamentonicio = :dtfasetratamentonicio"),
    @NamedQuery(name = "FaseTratamento.findByDtfasetratamentofim", query = "SELECT f FROM FaseTratamento f WHERE f.dtfasetratamentofim = :dtfasetratamentofim"),
    @NamedQuery(name = "FaseTratamento.findByDsmotivofasetratamento", query = "SELECT f FROM FaseTratamento f WHERE f.dsmotivofasetratamento = :dsmotivofasetratamento"),
    @NamedQuery(name = "FaseTratamento.findByTratamento", query = "SELECT f FROM FaseTratamento f WHERE f.idtratamento = :idtratamento")})
public class FaseTratamento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_fasetratamento_seq")
	@Basic(optional = false)
    @Column(name = "idfasetratamento")
    private Integer idfasetratamento;
    
    @Column(name = "dtfasetratamentonicio")
    @Temporal(TemporalType.DATE)
    private Date dtfasetratamentonicio;
    
    @Column(name = "dtfasetratamentofim")
    @Temporal(TemporalType.DATE)
    private Date dtfasetratamentofim;
    
    @Size(max = 2147483647)
    @Column(name = "dsmotivofasetratamento")
    private String dsmotivofasetratamento;
    
//    @JoinColumn(name = "idusuario", referencedColumnName = "id_usuario")
//    @ManyToOne
//    private Tbusuario idusuario;
    private Integer idusuario;

//    @JoinColumn(name = "idtratamento", referencedColumnName = "idtratamento")
//    @ManyToOne
//    private Tratamento idtratamento;
    private Integer idtratamento;
    
//    @JoinColumn(name = "idfase", referencedColumnName = "idfase")
//    @ManyToOne
//    private Fase idfase;
    private Integer idfase;
    
    public FaseTratamento() {
    }

    public FaseTratamento(Integer idfasetratamento) {
        this.idfasetratamento = idfasetratamento;
    }

    public Integer getIdfasetratamento() {
        return idfasetratamento;
    }

    public void setIdfasetratamento(Integer idfasetratamento) {
        this.idfasetratamento = idfasetratamento;
    }

    public Date getDtfasetratamentonicio() {
        return dtfasetratamentonicio;
    }

    public void setDtfasetratamentonicio(Date dtfasetratamentonicio) {
        this.dtfasetratamentonicio = dtfasetratamentonicio;
    }

    public Date getDtfasetratamentofim() {
        return dtfasetratamentofim;
    }

    public void setDtfasetratamentofim(Date dtfasetratamentofim) {
        this.dtfasetratamentofim = dtfasetratamentofim;
    }

    public String getDsmotivofasetratamento() {
        return dsmotivofasetratamento;
    }

    public void setDsmotivofasetratamento(String dsmotivofasetratamento) {
        this.dsmotivofasetratamento = dsmotivofasetratamento;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdtratamento() {
        return idtratamento;
    }

    public void setIdtratamento(Integer idtratamento) {
        this.idtratamento = idtratamento;
    }

    public Integer getIdfase() {
        return idfase;
    }

    public void setIdfase(Integer idfase) {
        this.idfase = idfase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfasetratamento != null ? idfasetratamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FaseTratamento)) {
            return false;
        }
        FaseTratamento other = (FaseTratamento) object;
        if ((this.idfasetratamento == null && other.idfasetratamento != null) || (this.idfasetratamento != null && !this.idfasetratamento.equals(other.idfasetratamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.FaseTratamento[ idfasetratamento=" + idfasetratamento + " ]";
    }
    
}
