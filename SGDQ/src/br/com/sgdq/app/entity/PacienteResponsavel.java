/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgdq.app.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio Augusto
 */
@Entity
@Table(name = "\"PacienteResponsavel\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_pacienteresponsavel_seq", sequenceName = "dbo.id_pacienteresponsavel_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PacienteResponsavel.findAll", query = "SELECT p FROM PacienteResponsavel p"),
    @NamedQuery(name = "PacienteResponsavel.findByIdPacienteResponsavel", query = "SELECT p FROM PacienteResponsavel p WHERE p.idPacienteResponsavel = :idPacienteResponsavel")})
public class PacienteResponsavel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_fasetratamento_seq")
    @Basic(optional = false)
    @Column(name = "idPacienteResponsavel")
    private Integer idPacienteResponsavel;
    @JoinColumn(name = "idResponsavel", referencedColumnName = "idResponsavel")
    @ManyToOne
    private Responsavel idResponsavel;
    @JoinColumn(name = "idPaciente", referencedColumnName = "idPaciente")
    @ManyToOne
    private Paciente idPaciente;

    public PacienteResponsavel() {
    }

    public PacienteResponsavel(Integer idPacienteResponsavel) {
        this.idPacienteResponsavel = idPacienteResponsavel;
    }

    public Integer getIdPacienteResponsavel() {
        return idPacienteResponsavel;
    }

    public void setIdPacienteResponsavel(Integer idPacienteResponsavel) {
        this.idPacienteResponsavel = idPacienteResponsavel;
    }

    public Responsavel getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Responsavel idResponsavel) {
        this.idResponsavel = idResponsavel;
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
        hash += (idPacienteResponsavel != null ? idPacienteResponsavel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PacienteResponsavel)) {
            return false;
        }
        PacienteResponsavel other = (PacienteResponsavel) object;
        if ((this.idPacienteResponsavel == null && other.idPacienteResponsavel != null) || (this.idPacienteResponsavel != null && !this.idPacienteResponsavel.equals(other.idPacienteResponsavel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.PacienteResponsavel[ idPacienteResponsavel=" + idPacienteResponsavel + " ]";
    }
    
}
