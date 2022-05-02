/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LArryJordan
 */
@Entity
@Table(name = "echecs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Echecs.findAll", query = "SELECT e FROM Echecs e"),
    @NamedQuery(name = "Echecs.findById", query = "SELECT e FROM Echecs e WHERE e.id = :id"),
    @NamedQuery(name = "Echecs.findByEtudiant", query = "SELECT e FROM Echecs e WHERE e.etudiant = :etudiant"),
    @NamedQuery(name = "Echecs.findByAnnee", query = "SELECT e FROM Echecs e WHERE e.annee = :annee"),
    @NamedQuery(name = "Echecs.findByStatut", query = "SELECT e FROM Echecs e WHERE e.statut = :statut")})
public class Echecs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "etudiant")
    private String etudiant;
    @Basic(optional = false)
    @Column(name = "annee")
    private String annee;
    @Column(name = "statut")
    private String statut;

    public Echecs() {
    }

    public Echecs(Integer id) {
        this.id = id;
    }

    public Echecs(Integer id, String etudiant, String annee) {
        this.id = id;
        this.etudiant = etudiant;
        this.annee = annee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(String etudiant) {
        this.etudiant = etudiant;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Echecs)) {
            return false;
        }
        Echecs other = (Echecs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Echecs[ id=" + id + " ]";
    }
    
}
