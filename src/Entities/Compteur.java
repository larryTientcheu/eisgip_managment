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
@Table(name = "compteur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compteur.findAll", query = "SELECT c FROM Compteur c"),
    @NamedQuery(name = "Compteur.findByIdCompteur", query = "SELECT c FROM Compteur c WHERE c.idCompteur = :idCompteur"),
    @NamedQuery(name = "Compteur.findByEtudiant", query = "SELECT c FROM Compteur c WHERE c.etudiant = :etudiant"),
    @NamedQuery(name = "Compteur.findByNiveaux", query = "SELECT c FROM Compteur c WHERE c.niveaux = :niveaux"),
    @NamedQuery(name = "Compteur.findByProffeseur", query = "SELECT c FROM Compteur c WHERE c.proffeseur = :proffeseur"),
    @NamedQuery(name = "Compteur.findByAddresse", query = "SELECT c FROM Compteur c WHERE c.addresse = :addresse"),
    @NamedQuery(name = "Compteur.findByStaff", query = "SELECT c FROM Compteur c WHERE c.staff = :staff"),
    @NamedQuery(name = "Compteur.findByCours", query = "SELECT c FROM Compteur c WHERE c.cours = :cours"),
    @NamedQuery(name = "Compteur.findByNotes", query = "SELECT c FROM Compteur c WHERE c.notes = :notes"),
    @NamedQuery(name = "Compteur.findByAnnee", query = "SELECT c FROM Compteur c WHERE c.annee = :annee")})
public class Compteur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_compteur")
    private Integer idCompteur;
    @Column(name = "etudiant")
    private Integer etudiant;
    @Column(name = "niveaux")
    private Integer niveaux;
    @Column(name = "proffeseur")
    private Integer proffeseur;
    @Column(name = "addresse")
    private Integer addresse;
    @Column(name = "staff")
    private Integer staff;
    @Column(name = "cours")
    private Integer cours;
    @Column(name = "notes")
    private Integer notes;
    @Column(name = "annee")
    private Integer annee;

    public Compteur() {
    }

    public Compteur(Integer idCompteur) {
        this.idCompteur = idCompteur;
    }

    public Integer getIdCompteur() {
        return idCompteur;
    }

    public void setIdCompteur(Integer idCompteur) {
        this.idCompteur = idCompteur;
    }

    public Integer getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Integer etudiant) {
        this.etudiant = etudiant;
    }

    public Integer getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(Integer niveaux) {
        this.niveaux = niveaux;
    }

    public Integer getProffeseur() {
        return proffeseur;
    }

    public void setProffeseur(Integer proffeseur) {
        this.proffeseur = proffeseur;
    }

    public Integer getAddresse() {
        return addresse;
    }

    public void setAddresse(Integer addresse) {
        this.addresse = addresse;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public Integer getCours() {
        return cours;
    }

    public void setCours(Integer cours) {
        this.cours = cours;
    }

    public Integer getNotes() {
        return notes;
    }

    public void setNotes(Integer notes) {
        this.notes = notes;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompteur != null ? idCompteur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compteur)) {
            return false;
        }
        Compteur other = (Compteur) object;
        if ((this.idCompteur == null && other.idCompteur != null) || (this.idCompteur != null && !this.idCompteur.equals(other.idCompteur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Compteur[ idCompteur=" + idCompteur + " ]";
    }
    
}
