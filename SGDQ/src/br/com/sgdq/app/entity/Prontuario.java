/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Antonio Augusto
 */
@Entity
//@Indexed
@Table(name = "\"Prontuario\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_prontuario_seq", sequenceName = "dbo.id_prontuario_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prontuario.findAll", query = "SELECT p FROM Prontuario p"),
    @NamedQuery(name = "Prontuario.findByIdProntuario", query = "SELECT p FROM Prontuario p WHERE p.idProntuario = :idProntuario"),
    @NamedQuery(name = "Prontuario.findByIdProntuarioStatus", query = "SELECT p FROM Prontuario p WHERE p.idProntuarioStatus = :idProntuarioStatus"),
    @NamedQuery(name = "Prontuario.findByDtProntuarioInicio", query = "SELECT p FROM Prontuario p WHERE p.dtProntuarioInicio = :dtProntuarioInicio"),
    @NamedQuery(name = "Prontuario.findByIdUsuarioCadastro", query = "SELECT p FROM Prontuario p WHERE p.idUsuarioCadastro = :idUsuarioCadastro"),
    @NamedQuery(name = "Prontuario.findBynuCPFPaciente", query = "SELECT p FROM Prontuario p INNER JOIN fetch p.idPaciente.idPessoa pessoa WHERE pessoa.nuCPF = :nucpf"),
    @NamedQuery(name = "Prontuario.findByProntuarioCPFNomePaciente", query = "SELECT p FROM Prontuario p INNER JOIN fetch p.idPaciente.idPessoa pessoa WHERE p.idProntuario = :idProntuario OR pessoa.nmPessoa like :nmPessoa OR pessoa.nuCPF = :nucpf")})
public class Prontuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_prontuario_seq")
    @Basic(optional = false)
    @Column(name = "idProntuario")
    private Integer idProntuario;
    
    @Column(name = "idProntuarioStatus")
    private Integer idProntuarioStatus;
    
    @Column(name = "dtProntuarioInicio")
    @Temporal(TemporalType.DATE)
    private Date dtProntuarioInicio;
    
    @Column(name = "idUsuarioCadastro")
    private Integer idUsuarioCadastro;
    
    @JoinColumn(name = "idPaciente", referencedColumnName = "idPaciente")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
    private Paciente idPaciente;	

//    @JoinColumn(name="idUsuarioCadastro", referencedColumnName = "id_usuario", insertable=false, updatable=false)
//    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuarioCadastro", referencedColumnName = "id_usuario", insertable = false, updatable = false, nullable = false)
    public UsuarioSGDQ usuario; //unidirectional
    
    public UsuarioSGDQ getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSGDQ usuario) {
		this.usuario = usuario;
	}

	public Prontuario() {
    	this.idPaciente = new Paciente();
    }

    public Prontuario(Integer idProntuario) {
        this.idProntuario = idProntuario;
    }

    public Integer getIdProntuario() {
        return idProntuario;
    }

    public void setIdProntuario(Integer idProntuario) {
        this.idProntuario = idProntuario;
    }

    public Integer getIdProntuarioStatus() {
        return idProntuarioStatus;
    }

    public void setIdProntuarioStatus(Integer idProntuarioStatus) {
        this.idProntuarioStatus = idProntuarioStatus;
    }

    public Date getDtProntuarioInicio() {
        return dtProntuarioInicio;
    }

    public void setDtProntuarioInicio(Date dtProntuarioInicio) {
        this.dtProntuarioInicio = dtProntuarioInicio;
    }

    public Integer getIdUsuarioCadastro() {
        return idUsuarioCadastro;
    }

    public void setIdUsuarioCadastro(Integer idUsuarioCadastro) {
        this.idUsuarioCadastro = idUsuarioCadastro;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProntuario != null ? idProntuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prontuario)) {
            return false;
        }
        Prontuario other = (Prontuario) object;
        if ((this.idProntuario == null && other.idProntuario != null) || (this.idProntuario != null && !this.idProntuario.equals(other.idProntuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Prontuario[ idProntuario=" + idProntuario + " ]";
    }
    
}
