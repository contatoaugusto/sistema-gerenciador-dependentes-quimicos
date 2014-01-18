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
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prohgy
 */
@Entity
//@Indexed
@Table(name = "\"Paciente\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_paciente_seq", sequenceName = "dbo.id_paciente_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p"),
    @NamedQuery(name = "Paciente.findByIdPaciente", query = "SELECT p FROM Paciente p WHERE p.idPaciente = :idPaciente"),
    @NamedQuery(name = "Paciente.findByDtEntrada", query = "SELECT p FROM Paciente p WHERE p.dtEntrada = :dtEntrada")})
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_paciente_seq")
    @Basic(optional = false)
    @Column(name = "idPaciente")
    private Integer idPaciente;
    
    @Column(name = "dtEntrada")
    @Temporal(TemporalType.DATE)
    private Date dtEntrada;
    
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Prontuario> prontuarioCollection;
    
    @JoinColumn(name = "idResponsavel", referencedColumnName = "idResponsavel")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Responsavel idResponsavel;
    
    @JoinColumn(name = "idPessoa", referencedColumnName = "idPessoa")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Pessoa idPessoa;

    public Paciente() {
    	this.idPessoa = new Pessoa();
    	this.idResponsavel = new Responsavel();
    }

    public Paciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(Date dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    @XmlTransient
    public Collection<Prontuario> getProntuarioCollection() {
        return prontuarioCollection;
    }

    public void setProntuarioCollection(Collection<Prontuario> prontuarioCollection) {
        this.prontuarioCollection = prontuarioCollection;
    }

    public Responsavel getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Responsavel idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Pessoa getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Pessoa idPessoa) {
        this.idPessoa = idPessoa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Paciente[ idPaciente=" + idPaciente + " ]";
    }
    
}
