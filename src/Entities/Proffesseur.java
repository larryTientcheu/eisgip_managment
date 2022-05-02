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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LArryJordan
 */
@Entity
@Table(name = "proffesseur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proffesseur.findAll", query = "SELECT p FROM Proffesseur p"),
    @NamedQuery(name = "Proffesseur.findByIdProff", query = "SELECT p FROM Proffesseur p WHERE p.idProff = :idProff"),
    @NamedQuery(name = "Proffesseur.findByNom", query = "SELECT p FROM Proffesseur p WHERE p.nom = :nom"),
    @NamedQuery(name = "Proffesseur.findBySpecialite", query = "SELECT p FROM Proffesseur p WHERE p.specialite = :specialite"),
    @NamedQuery(name = "Proffesseur.findByUniversite", query = "SELECT p FROM Proffesseur p WHERE p.universite = :universite"),
    @NamedQuery(name = "Proffesseur.findByEmail", query = "SELECT p FROM Proffesseur p WHERE p.email = :email")})
public class Proffesseur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proff")
    private Integer idProff;
    @Column(name = "nom")
    private String nom;
    @Column(name = "specialite")
    private String specialite;
    @Column(name = "universite")
    private String universite;
    @Column(name = "email")
    private String email;
    @ManyToMany(mappedBy = "proffesseurList")
    private List<Cours> coursList;
    @JoinColumn(name = "id_niveaux", referencedColumnName = "id_niveaux")
    @ManyToOne(optional = false)
    private Niveaux idNiveaux;
    @JoinColumn(name = "id_address", referencedColumnName = "id_address")
    @ManyToOne
    private Addresse idAddress;

    public Proffesseur() {
    }

    public Proffesseur(Integer idProff) {
        this.idProff = idProff;
    }

    public Integer getIdProff() {
        return idProff;
    }

    public void setIdProff(Integer idProff) {
        this.idProff = idProff;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Cours> getCoursList() {
        return coursList;
    }

    public void setCoursList(List<Cours> coursList) {
        this.coursList = coursList;
    }

    public Niveaux getIdNiveaux() {
        return idNiveaux;
    }

    public void setIdNiveaux(Niveaux idNiveaux) {
        this.idNiveaux = idNiveaux;
    }

    public Addresse getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Addresse idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProff != null ? idProff.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proffesseur)) {
            return false;
        }
        Proffesseur other = (Proffesseur) object;
        if ((this.idProff == null && other.idProff != null) || (this.idProff != null && !this.idProff.equals(other.idProff))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Proffesseur[ idProff=" + idProff + " ]";
    }
    
}
