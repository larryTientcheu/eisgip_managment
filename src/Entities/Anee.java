/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "anee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anee.findAll", query = "SELECT a FROM Anee a"),
    @NamedQuery(name = "Anee.findByIdAnee", query = "SELECT a FROM Anee a WHERE a.idAnee = :idAnee")})
public class Anee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_anee")
    private String idAnee;
    @JoinTable(name = "etudiant_annee", joinColumns = {
        @JoinColumn(name = "id_anee", referencedColumnName = "id_anee")}, inverseJoinColumns = {
        @JoinColumn(name = "matricule", referencedColumnName = "matricule")})
    @ManyToMany
    private List<Etudiant> etudiantList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnee")
    private List<Cours> coursList;

    public Anee() {
    }

    public Anee(String idAnee) {
        this.idAnee = idAnee;
    }

    public String getIdAnee() {
        return idAnee;
    }

    public void setIdAnee(String idAnee) {
        this.idAnee = idAnee;
    }

    @XmlTransient
    public List<Etudiant> getEtudiantList() {
        return etudiantList;
    }

    public void setEtudiantList(List<Etudiant> etudiantList) {
        this.etudiantList = etudiantList;
    }

    @XmlTransient
    public List<Cours> getCoursList() {
        return coursList;
    }

    public void setCoursList(List<Cours> coursList) {
        this.coursList = coursList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnee != null ? idAnee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anee)) {
            return false;
        }
        Anee other = (Anee) object;
        if ((this.idAnee == null && other.idAnee != null) || (this.idAnee != null && !this.idAnee.equals(other.idAnee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+idAnee;
    }
    
}
