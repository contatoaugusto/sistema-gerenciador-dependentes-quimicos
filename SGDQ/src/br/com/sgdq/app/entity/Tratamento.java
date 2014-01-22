/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio Augusto
 */
@Entity
@Table(name = "\"Tratamento\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_tratamento_seq", sequenceName = "dbo.id_tratamento_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tratamento.findAll", query = "SELECT t FROM Tratamento t"),
    @NamedQuery(name = "Tratamento.findByIdtratamento", query = "SELECT t FROM Tratamento t WHERE t.idtratamento = :idtratamento"),
    @NamedQuery(name = "Tratamento.findByDstratamentostatusmotivo", query = "SELECT t FROM Tratamento t WHERE t.dstratamentostatusmotivo = :dstratamentostatusmotivo"),
    @NamedQuery(name = "Tratamento.findByIcativo", query = "SELECT t FROM Tratamento t WHERE t.icativo = :icativo"),
    @NamedQuery(name = "Tratamento.findByNmmedicoresponsavel", query = "SELECT t FROM Tratamento t WHERE t.nmmedicoresponsavel = :nmmedicoresponsavel"),
    @NamedQuery(name = "Tratamento.findByDtinclusao", query = "SELECT t FROM Tratamento t WHERE t.dtinclusao = :dtinclusao"),
    @NamedQuery(name = "Tratamento.findByIdtratamentostatus", query = "SELECT t FROM Tratamento t WHERE t.idtratamentostatus = :idtratamentostatus"),
    @NamedQuery(name = "Tratamento.findByIdtratamentotipo", query = "SELECT t FROM Tratamento t WHERE t.idtratamentotipo = :idtratamentotipo"),
    @NamedQuery(name = "Tratamento.findByProntuario", query = "SELECT t FROM Tratamento t WHERE t.idprontuario = :idprontuario"),
    @NamedQuery(name = "Tratamento.findByProntuarioAtivo", query = "SELECT t FROM Tratamento t WHERE t.idprontuario = :idprontuario and t.icativo = :icativo")})
public class Tratamento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_tratamento_seq")
    @Basic(optional = false)
    @Column(name = "idtratamento")
    private Integer idtratamento;
    @Size(max = 2147483647)
    
    @Column(name = "dstratamentostatusmotivo")
    private String dstratamentostatusmotivo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "icativo", columnDefinition = "smallint default 1")
    private short icativo;
    
    @Size(max = 2147483647)
    @Column(name = "nmmedicoresponsavel")
    private String nmmedicoresponsavel;
    
    @Column(name = "dtinclusao")
    @Temporal(TemporalType.DATE)
    private Date dtinclusao;
    
//    @OneToMany(mappedBy = "idtratamento")
//    private Collection<FaseTratamento> faseTratamentoCollection;
//    
    @OneToMany(mappedBy = "idtratamento")
    private Collection<Exame> exameCollection;
    
//    @JoinColumn(name = "idusuario", referencedColumnName = "id_usuario")
//    @ManyToOne(optional = false)
//    private Tbusuario idusuario;
    @Column(name = "idusuario")
    private Integer idusuario;
    
    
//    @JoinColumn(name = "idtratamentotipo", referencedColumnName = "idtratamentotipo")
//    @ManyToOne(optional = false)
//    private TratamentoTipo idtratamentotipo;
    @Basic(optional = false)
    @Column(name = "idtratamentotipo")
    private Integer idtratamentotipo;
    
//	@JoinColumn(name = "idtratamentostatus", referencedColumnName = "idtratamentostatus")
//    @ManyToOne(optional = false)
//    private TratamentoStatus idtratamentostatus;
    @Basic(optional = false)
    @Column(name = "idtratamentostatus")
    private Integer idtratamentostatus;
    
//    @JoinColumn(name = "idprontuario", referencedColumnName = "idprontuario")
//    @ManyToOne(optional = false)
//    private Prontuario idprontuario;
    @Basic(optional = false)
    @Column(name = "idprontuario")
    private Integer idprontuario;
    
    @ManyToOne (cascade= CascadeType.REFRESH)
    @JoinColumn(name="idProntuario", referencedColumnName = "idprontuario", insertable=false, updatable=false)
    public Prontuario prontuario; //unidirectional
    
    @ManyToOne (cascade= CascadeType.REFRESH)
    @JoinColumn(name="idtratamentostatus", referencedColumnName = "idtratamentostatus", insertable=false, updatable=false)
    public TratamentoStatus tratamentoStatus; //unidirectional
    
	@Column(name = "dssolicitacaotermino")
    private String dssolicitacaotermino;

    @Column(name = "dtsolicitacaotermino")
    @Temporal(TemporalType.DATE)
    private Date dtsolicitacaotermino;
 
    @Column(name = "dttratamentofim")
    @Temporal(TemporalType.DATE)
    private Date dttratamentofim;
    
	@Column(name = "idusuariosolicitacaotermino")
    private Integer idusuariosolicitacaotermino;

    public Tratamento() {
    }

    public Tratamento(Integer idtratamento) {
        this.idtratamento = idtratamento;
    }

    public Tratamento(Integer idtratamento, short icativo) {
        this.idtratamento = idtratamento;
        this.icativo = icativo;
    }

    public Integer getIdtratamento() {
        return idtratamento;
    }

    public void setIdtratamento(Integer idtratamento) {
        this.idtratamento = idtratamento;
    }

    public String getDstratamentostatusmotivo() {
        return dstratamentostatusmotivo;
    }

    public void setDstratamentostatusmotivo(String dstratamentostatusmotivo) {
        this.dstratamentostatusmotivo = dstratamentostatusmotivo;
    }

    public short getIcativo() {
        return icativo;
    }

    public void setIcativo(short icativo) {
        this.icativo = icativo;
    }

    public String getNmmedicoresponsavel() {
        return nmmedicoresponsavel;
    }

    public void setNmmedicoresponsavel(String nmmedicoresponsavel) {
        this.nmmedicoresponsavel = nmmedicoresponsavel;
    }

    public Date getDtinclusao() {
        return dtinclusao;
    }

    public void setDtinclusao(Date dtinclusao) {
        this.dtinclusao = dtinclusao;
    }

//    @XmlTransient
//    public Collection<FaseTratamento> getFaseTratamentoCollection() {
//        return faseTratamentoCollection;
//    }
//
//    public void setFaseTratamentoCollection(Collection<FaseTratamento> faseTratamentoCollection) {
//        this.faseTratamentoCollection = faseTratamentoCollection;
//    }

    @XmlTransient
    public Collection<Exame> getExameCollection() {
        return exameCollection;
    }

    public void setExameCollection(Collection<Exame> exameCollection) {
        this.exameCollection = exameCollection;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdtratamentotipo() {
        return idtratamentotipo;
    }

    public void setIdtratamentotipo(Integer idtratamentotipo) {
        this.idtratamentotipo = idtratamentotipo;
    }

    public Integer getIdtratamentostatus() {
        return idtratamentostatus;
    }

    public void setIdtratamentostatus(Integer idtratamentostatus) {
        this.idtratamentostatus = idtratamentostatus;
    }

    public Integer getIdprontuario() {
        return idprontuario;
    }

    public void setIdprontuario(Integer idprontuario) {
        this.idprontuario = idprontuario;
    }

    
    public String getDssolicitacaotermino() {
		return dssolicitacaotermino;
	}

	public void setDssolicitacaotermino(String dssolicitacaotermino) {
		this.dssolicitacaotermino = dssolicitacaotermino;
	}

	public Date getDtsolicitacaotermino() {
		return dtsolicitacaotermino;
	}

	public void setDtsolicitacaotermino(Date dtsolicitacaotermino) {
		this.dtsolicitacaotermino = dtsolicitacaotermino;
	}
    
   public Integer getIdusuariosolicitacaotermino() {
		return idusuariosolicitacaotermino;
	}

	public void setIdusuariosolicitacaotermino(Integer idusuariosolicitacaotermino) {
		this.idusuariosolicitacaotermino = idusuariosolicitacaotermino;
	}

    public Date getDttratamentofim() {
		return dttratamentofim;
	}

	public void setDttratamentofim(Date dttratamentofim) {
		this.dttratamentofim = dttratamentofim;
	}
	

    public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}
	
    public TratamentoStatus getTratamentoStatus() {
		return tratamentoStatus;
	}

	public void setTratamentoStatus(TratamentoStatus tratamentoStatus) {
		this.tratamentoStatus = tratamentoStatus;
	}
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtratamento != null ? idtratamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tratamento)) {
            return false;
        }
        Tratamento other = (Tratamento) object;
        if ((this.idtratamento == null && other.idtratamento != null) || (this.idtratamento != null && !this.idtratamento.equals(other.idtratamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Tratamento[ idtratamento=" + idtratamento + " ]";
    }
    
}
