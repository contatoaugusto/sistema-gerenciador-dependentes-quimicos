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
import javax.persistence.Lob;
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
 * @author Antonio Augusto
 */
@Entity
//@Indexed
@Table(name = "\"Pessoa\"", schema = "dbo")
@SequenceGenerator(name = "dbo.id_pessoa_seq", sequenceName = "dbo.id_pessoa_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p"),
    @NamedQuery(name = "Pessoa.findByIdPessoa", query = "SELECT p FROM Pessoa p WHERE p.idPessoa = :idPessoa"),
    @NamedQuery(name = "Pessoa.findByNmPessoa", query = "SELECT p FROM Pessoa p WHERE p.nmPessoa = :nmPessoa"),
    @NamedQuery(name = "Pessoa.findByNuCPF", query = "SELECT p FROM Pessoa p WHERE p.nuCPF = :nuCPF"),
    @NamedQuery(name = "Pessoa.findByNmOrgaoEmissor", query = "SELECT p FROM Pessoa p WHERE p.nmOrgaoEmissor = :nmOrgaoEmissor"),
    @NamedQuery(name = "Pessoa.findByNmSexo", query = "SELECT p FROM Pessoa p WHERE p.nmSexo = :nmSexo"),
    @NamedQuery(name = "Pessoa.findByNuTelefone", query = "SELECT p FROM Pessoa p WHERE p.nuTelefone = :nuTelefone"),
    @NamedQuery(name = "Pessoa.findByNuCelular", query = "SELECT p FROM Pessoa p WHERE p.nuCelular = :nuCelular"),
    @NamedQuery(name = "Pessoa.findByDtNascimento", query = "SELECT p FROM Pessoa p WHERE p.dtNascimento = :dtNascimento"),
    @NamedQuery(name = "Pessoa.findByNuRG", query = "SELECT p FROM Pessoa p WHERE p.nuRG = :nuRG"),
    @NamedQuery(name = "Pessoa.findByDsEmail", query = "SELECT p FROM Pessoa p WHERE p.dsEmail = :dsEmail")})
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbo.id_pessoa_seq")
    @Basic(optional = false)
    @Column(name = "idPessoa")
    private Integer idPessoa;
    
    @Column(name = "nmPessoa")
    private String nmPessoa;
    
    @Column(name = "nuCPF")
    private String nuCPF;
    
    @Column(name = "nmOrgaoEmissor")
    private String nmOrgaoEmissor;
    
    @Column(name = "nmSexo")
    private String nmSexo;
    
    @Column(name = "nuTelefone")
    private String nuTelefone;
    
    @Column(name = "nuCelular")
    private String nuCelular;
    
    @Column(name = "dtNascimento")
    @Temporal(TemporalType.DATE)
    private Date dtNascimento;
    
    @Column(name = "nuRG")
    private String nuRG;
    
    @Column(name = "dsEmail")
    private String dsEmail;
    
    @Lob
    @Column(name = "btImagem")
    private byte[] btImagem;
    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idEstadoOrgaoEmissor", referencedColumnName = "idEstado")//, insertable = false, updatable = false)
//    private Estado idEstadoOrgaoEmissor;
    @Column(name = "idEstadoOrgaoEmissor")
    private String idEstadoOrgaoEmissor;
    
    @JoinColumn(name = "idEndereco", referencedColumnName = "idEndereco")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Endereco idEndereco;
    
//    @JoinColumn(name = "idCidadeNascimento", referencedColumnName = "idCidade")//, insertable = false, updatable = false)
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
//    private Cidade idCidadeNascimento;
    @Column(name = "idCidadeNascimento")
    private String idCidadeNascimento;
    
    @OneToMany(mappedBy = "idPessoa")
    private Collection<Paciente> pacienteCollection;

    public Pessoa() {
    	//this.idCidadeNascimento = new Cidade();
    	this.idEndereco = new Endereco();
    	//this.idEstadoOrgaoEmissor = new Estado();
    }

    public Pessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNmPessoa() {
        return nmPessoa;
    }

    public void setNmPessoa(String nmPessoa) {
        this.nmPessoa = nmPessoa;
    }

    public String getNuCPF() {
        return nuCPF;
    }

    public void setNuCPF(String nuCPF) {
        this.nuCPF = nuCPF;
    }

    public String getNmOrgaoEmissor() {
        return nmOrgaoEmissor;
    }

    public void setNmOrgaoEmissor(String nmOrgaoEmissor) {
        this.nmOrgaoEmissor = nmOrgaoEmissor;
    }

    public String getNmSexo() {
        return nmSexo;
    }

    public void setNmSexo(String nmSexo) {
        this.nmSexo = nmSexo;
    }

    public String getNuTelefone() {
        return nuTelefone;
    }

    public void setNuTelefone(String nuTelefone) {
        this.nuTelefone = nuTelefone;
    }

    public String getNuCelular() {
        return nuCelular;
    }

    public void setNuCelular(String nuCelular) {
        this.nuCelular = nuCelular;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getNuRG() {
        return nuRG;
    }

    public void setNuRG(String nuRG) {
        this.nuRG = nuRG;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public byte[] getBtImagem() {
        return btImagem;
    }

    public void setBtImagem(byte[] btImagem) {
        this.btImagem = btImagem;
    }

    public String getIdEstadoOrgaoEmissor() {
        return idEstadoOrgaoEmissor;
    }

    public void setIdEstadoOrgaoEmissor(String idEstadoOrgaoEmissor) {
        this.idEstadoOrgaoEmissor = idEstadoOrgaoEmissor;
    }

    public Endereco getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Endereco idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getIdCidadeNascimento() {
        return idCidadeNascimento;
    }

    public void setIdCidadeNascimento(String idCidadeNascimento) {
        this.idCidadeNascimento = idCidadeNascimento;
    }

    @XmlTransient
    public Collection<Paciente> getPacienteCollection() {
        return pacienteCollection;
    }

    public void setPacienteCollection(Collection<Paciente> pacienteCollection) {
        this.pacienteCollection = pacienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPessoa != null ? idPessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.idPessoa == null && other.idPessoa != null) || (this.idPessoa != null && !this.idPessoa.equals(other.idPessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sgdq.app.entity.Pessoa[ idPessoa=" + idPessoa + " ]";
    }
}
