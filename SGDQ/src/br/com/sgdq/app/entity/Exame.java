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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio Augusto
 */
@Entity
@Table(name = "\"Exame\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_exame_seq", sequenceName = "dbo.id_exame_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exame.findAll", query = "SELECT e FROM Exame e"),
    @NamedQuery(name = "Exame.findByIdexame", query = "SELECT e FROM Exame e WHERE e.idexame = :idexame"),
    @NamedQuery(name = "Exame.findByNmmedico", query = "SELECT e FROM Exame e WHERE e.nmmedico = :nmmedico"),
    @NamedQuery(name = "Exame.findByDtexame", query = "SELECT e FROM Exame e WHERE e.dtexame = :dtexame"),
    @NamedQuery(name = "Exame.findByNmexame", query = "SELECT e FROM Exame e WHERE e.nmexame = :nmexame"),
    @NamedQuery(name = "Exame.findByIdTratamento", query = "SELECT e FROM Exame e WHERE e.idtratamento = :idtratamento order by e.idexame")})
public class Exame implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_exame_seq")
    @Basic(optional = false)
    @NotNull
    @Column(name = "idexame")
    private Integer idexame;
    
    @Size(max = 2147483647)
    @Column(name = "nmmedico")
    private String nmmedico;
    
    @Column(name = "dtexame")
    @Temporal(TemporalType.DATE)
    private Date dtexame;
    
    @Size(max = 2147483647)
    @Column(name = "nmexame")
    private String nmexame;
    
    @Size(max = 2147483647)
    @Column(name = "dsresultado")
    private String dsresultado;
    
	//    @JoinColumn(name = "idusuario", referencedColumnName = "id_usuario")
//    @ManyToOne
//    private Tbusuario idusuario;
    private Integer idusuario;
    
    @JoinColumn(name = "idtratamento", referencedColumnName = "idtratamento")
    @ManyToOne
    private Tratamento idtratamento;

    public Exame() {
    }

    public Exame(Integer idexame) {
        this.idexame = idexame;
    }

    public Integer getIdexame() {
        return idexame;
    }

    public void setIdexame(Integer idexame) {
        this.idexame = idexame;
    }

    public String getNmmedico() {
        return nmmedico;
    }

    public void setNmmedico(String nmmedico) {
        this.nmmedico = nmmedico;
    }

    public Date getDtexame() {
        return dtexame;
    }

    public void setDtexame(Date dtexame) {
        this.dtexame = dtexame;
    }

    public String getNmexame() {
        return nmexame;
    }

    public void setNmexame(String nmexame) {
        this.nmexame = nmexame;
    }

    public String getDsresultado() {
		return dsresultado;
	}

	public void setDsresultado(String dsresultado) {
		this.dsresultado = dsresultado;
	}
    
    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Tratamento getIdtratamento() {
        return idtratamento;
    }

    public void setIdtratamento(Tratamento idtratamento) {
        this.idtratamento = idtratamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idexame != null ? idexame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exame)) {
            return false;
        }
        Exame other = (Exame) object;
        if ((this.idexame == null && other.idexame != null) || (this.idexame != null && !this.idexame.equals(other.idexame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Exame[ idexame=" + idexame + " ]";
    }
    
}
