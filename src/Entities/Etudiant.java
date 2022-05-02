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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "etudiant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e"),
    @NamedQuery(name = "Etudiant.findByMatricule", query = "SELECT e FROM Etudiant e WHERE e.matricule = :matricule"),
    @NamedQuery(name = "Etudiant.findByNom", query = "SELECT e FROM Etudiant e WHERE e.nom = :nom"),
    @NamedQuery(name = "Etudiant.findByPrenom", query = "SELECT e FROM Etudiant e WHERE e.prenom = :prenom"),
    @NamedQuery(name = "Etudiant.findByAge", query = "SELECT e FROM Etudiant e WHERE e.age = :age"),
    @NamedQuery(name = "Etudiant.findByLieu", query = "SELECT e FROM Etudiant e WHERE e.lieu = :lieu"),
    @NamedQuery(name = "Etudiant.findByRegion", query = "SELECT e FROM Etudiant e WHERE e.region = :region"),
    @NamedQuery(name = "Etudiant.findByNationalite", query = "SELECT e FROM Etudiant e WHERE e.nationalite = :nationalite")})
public class Etudiant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "matricule")
    private String matricule;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "age")
    private String age;
    @Column(name = "lieu")
    private String lieu;
    @Column(name = "region")
    private String region;
    @Column(name = "nationalite")
    private String nationalite;
    @ManyToMany(mappedBy = "etudiantList")
    private List<Cours> coursList;
    @ManyToMany(mappedBy = "etudiantList")
    private List<Anee> aneeList;
    @ManyToMany(mappedBy = "etudiantList")
    private List<Addresse> addresseList;
    @JoinColumn(name = "id_niveaux", referencedColumnName = "id_niveaux")
    @ManyToOne(optional = false)
    private Niveaux idNiveaux;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matricule")
    private List<Notes> notesList;

    public Etudiant() {
    }

    public Etudiant(String matricule) {
        this.matricule = matricule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    @XmlTransient
    public List<Cours> getCoursList() {
        return coursList;
    }

    public void setCoursList(List<Cours> coursList) {
        this.coursList = coursList;
    }

    @XmlTransient
    public List<Anee> getAneeList() {
        return aneeList;
    }

    public void setAneeList(List<Anee> aneeList) {
        this.aneeList = aneeList;
    }

    @XmlTransient
    public List<Addresse> getAddresseList() {
        return addresseList;
    }

    public void setAddresseList(List<Addresse> addresseList) {
        this.addresseList = addresseList;
    }

    public Niveaux getIdNiveaux() {
        return idNiveaux;
    }

    public void setIdNiveaux(Niveaux idNiveaux) {
        this.idNiveaux = idNiveaux;
    }

    @XmlTransient
    public List<Notes> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Notes> notesList) {
        this.notesList = notesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matricule != null ? matricule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etudiant)) {
            return false;
        }
        Etudiant other = (Etudiant) object;
        if ((this.matricule == null && other.matricule != null) || (this.matricule != null && !this.matricule.equals(other.matricule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+matricule;
    }
    
}
