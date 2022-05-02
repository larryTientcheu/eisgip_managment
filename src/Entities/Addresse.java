/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LArryJordan
 */
@Entity
@Table(name = "addresse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Addresse.findAll", query = "SELECT a FROM Addresse a"),
    @NamedQuery(name = "Addresse.findByIdAddress", query = "SELECT a FROM Addresse a WHERE a.idAddress = :idAddress"),
    @NamedQuery(name = "Addresse.findByLocation", query = "SELECT a FROM Addresse a WHERE a.location = :location"),
    @NamedQuery(name = "Addresse.findByNumero", query = "SELECT a FROM Addresse a WHERE a.numero = :numero"),
    @NamedQuery(name = "Addresse.findByEmail", query = "SELECT a FROM Addresse a WHERE a.email = :email")})
public class Addresse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_address")
    private Integer idAddress;
    @Column(name = "location")
    private String location;
    @Column(name = "numero")
    private String numero;
    @Column(name = "email")
    private String email;
    @JoinTable(name = "etudiant_address", joinColumns = {
        @JoinColumn(name = "id_address", referencedColumnName = "id_address")}, inverseJoinColumns = {
        @JoinColumn(name = "matricule", referencedColumnName = "matricule")})
    @ManyToMany
    private List<Etudiant> etudiantList;
    @OneToMany(mappedBy = "idAddress")
    private List<Staff> staffList;
    @OneToMany(mappedBy = "idAddress")
    private List<Proffesseur> proffesseurList;

    public Addresse() {
    }

    public Addresse(Integer idAddress) {
        this.idAddress = idAddress;
    }

    public Integer getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Etudiant> getEtudiantList() {
        return etudiantList;
    }

    public void setEtudiantList(List<Etudiant> etudiantList) {
        this.etudiantList = etudiantList;
    }

    @XmlTransient
    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    @XmlTransient
    public List<Proffesseur> getProffesseurList() {
        return proffesseurList;
    }

    public void setProffesseurList(List<Proffesseur> proffesseurList) {
        this.proffesseurList = proffesseurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAddress != null ? idAddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addresse)) {
            return false;
        }
        Addresse other = (Addresse) object;
        if ((this.idAddress == null && other.idAddress != null) || (this.idAddress != null && !this.idAddress.equals(other.idAddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Addresse[ idAddress=" + idAddress + " ]";
    }
    
}
