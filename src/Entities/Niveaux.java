/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LArryJordan
 */
@Entity
@Table(name = "niveaux")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Niveaux.findAll", query = "SELECT n FROM Niveaux n"),
    @NamedQuery(name = "Niveaux.findByIdNiveaux", query = "SELECT n FROM Niveaux n WHERE n.idNiveaux = :idNiveaux"),
    @NamedQuery(name = "Niveaux.findByNbreEtud", query = "SELECT n FROM Niveaux n WHERE n.nbreEtud = :nbreEtud"),
    @NamedQuery(name = "Niveaux.findByNbreCours", query = "SELECT n FROM Niveaux n WHERE n.nbreCours = :nbreCours"),
    @NamedQuery(name = "Niveaux.findByNbreProf", query = "SELECT n FROM Niveaux n WHERE n.nbreProf = :nbreProf")})
public class Niveaux implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @Column(name = "Nom")
    private String nom;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_niveaux")
    private Integer idNiveaux;
    @Column(name = "nbre_etud")
    private Integer nbreEtud;
    @Column(name = "nbre_cours")
    private Integer nbreCours;
    @Column(name = "nbre_prof")
    private Integer nbreProf;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNiveaux")
    private List<Etudiant> etudiantList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNiveaux")
    private List<Cours> coursList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNiveaux")
    private List<Proffesseur> proffesseurList;

    public Niveaux() {
    }

    public Niveaux(Integer idNiveaux) {
        this.idNiveaux = idNiveaux;
    }

    public Integer getIdNiveaux() {
        return idNiveaux;
    }

    public void setIdNiveaux(Integer idNiveaux) {
        Integer oldIdNiveaux = this.idNiveaux;
        this.idNiveaux = idNiveaux;
        changeSupport.firePropertyChange("idNiveaux", oldIdNiveaux, idNiveaux);
    }

    public Integer getNbreEtud() {
        return nbreEtud;
    }

    public void setNbreEtud(Integer nbreEtud) {
        Integer oldNbreEtud = this.nbreEtud;
        this.nbreEtud = nbreEtud;
        changeSupport.firePropertyChange("nbreEtud", oldNbreEtud, nbreEtud);
    }

    public Integer getNbreCours() {
        return nbreCours;
    }

    public void setNbreCours(Integer nbreCours) {
        Integer oldNbreCours = this.nbreCours;
        this.nbreCours = nbreCours;
        changeSupport.firePropertyChange("nbreCours", oldNbreCours, nbreCours);
    }

    public Integer getNbreProf() {
        return nbreProf;
    }

    public void setNbreProf(Integer nbreProf) {
        Integer oldNbreProf = this.nbreProf;
        this.nbreProf = nbreProf;
        changeSupport.firePropertyChange("nbreProf", oldNbreProf, nbreProf);
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
        hash += (idNiveaux != null ? idNiveaux.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Niveaux)) {
            return false;
        }
        Niveaux other = (Niveaux) object;
        if ((this.idNiveaux == null && other.idNiveaux != null) || (this.idNiveaux != null && !this.idNiveaux.equals(other.idNiveaux))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+idNiveaux;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        changeSupport.firePropertyChange("nom", oldNom, nom);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
