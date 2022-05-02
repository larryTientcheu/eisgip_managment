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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "cours")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cours.findAll", query = "SELECT c FROM Cours c"),
    @NamedQuery(name = "Cours.findByCodes", query = "SELECT c FROM Cours c WHERE c.codes = :codes"),
    @NamedQuery(name = "Cours.findByNom", query = "SELECT c FROM Cours c WHERE c.nom = :nom"),
    @NamedQuery(name = "Cours.findBySemestre", query = "SELECT c FROM Cours c WHERE c.semestre = :semestre"),
    @NamedQuery(name = "Cours.findByCredits", query = "SELECT c FROM Cours c WHERE c.credits = :credits"),
    @NamedQuery(name = "Cours.findByCodesperso", query = "SELECT c FROM Cours c WHERE c.codesperso = :codesperso")})
public class Cours implements Serializable {
    @Column(name = "ordre")
    private Integer ordre;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codes")
    private Integer codes;
    @Column(name = "nom")
    private String nom;
    @Column(name = "semestre")
    private Integer semestre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "credits")
    private Double credits;
    @Basic(optional = false)
    @Column(name = "codesperso")
    private String codesperso;
    @JoinTable(name = "prof_cours", joinColumns = {
        @JoinColumn(name = "codes", referencedColumnName = "codes")}, inverseJoinColumns = {
        @JoinColumn(name = "id_proff", referencedColumnName = "id_proff")})
    @ManyToMany
    private List<Proffesseur> proffesseurList;
    @JoinTable(name = "etudiant_cours", joinColumns = {
        @JoinColumn(name = "codes", referencedColumnName = "codes")}, inverseJoinColumns = {
        @JoinColumn(name = "matricule", referencedColumnName = "matricule")})
    @ManyToMany
    private List<Etudiant> etudiantList;
    @JoinColumn(name = "id_niveaux", referencedColumnName = "id_niveaux")
    @ManyToOne(optional = false)
    private Niveaux idNiveaux;
    @JoinColumn(name = "id_anee", referencedColumnName = "id_anee")
    @ManyToOne(optional = false)
    private Anee idAnee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codes")
    private List<Notes> notesList;

    public Cours() {
    }

    public Cours(Integer codes) {
        this.codes = codes;
    }

    public Cours(Integer codes, String codesperso) {
        this.codes = codes;
        this.codesperso = codesperso;
    }

    public Integer getCodes() {
        return codes;
    }

    public void setCodes(Integer codes) {
        this.codes = codes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public String getCodesperso() {
        return codesperso;
    }

    public void setCodesperso(String codesperso) {
        this.codesperso = codesperso;
    }

    @XmlTransient
    public List<Proffesseur> getProffesseurList() {
        return proffesseurList;
    }

    public void setProffesseurList(List<Proffesseur> proffesseurList) {
        this.proffesseurList = proffesseurList;
    }

    @XmlTransient
    public List<Etudiant> getEtudiantList() {
        return etudiantList;
    }

    public void setEtudiantList(List<Etudiant> etudiantList) {
        this.etudiantList = etudiantList;
    }

    public Niveaux getIdNiveaux() {
        return idNiveaux;
    }

    public void setIdNiveaux(Niveaux idNiveaux) {
        this.idNiveaux = idNiveaux;
    }

    public Anee getIdAnee() {
        return idAnee;
    }

    public void setIdAnee(Anee idAnee) {
        this.idAnee = idAnee;
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
        hash += (codes != null ? codes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cours)) {
            return false;
        }
        Cours other = (Cours) object;
        if ((this.codes == null && other.codes != null) || (this.codes != null && !this.codes.equals(other.codes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Cours[ codes=" + codes + " ]";
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }
    
}
