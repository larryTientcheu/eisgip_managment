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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LArryJordan
 */
@Entity
@Table(name = "notes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notes.findAll", query = "SELECT n FROM Notes n"),
    @NamedQuery(name = "Notes.findByNoteId", query = "SELECT n FROM Notes n WHERE n.noteId = :noteId"),
    @NamedQuery(name = "Notes.findByCc", query = "SELECT n FROM Notes n WHERE n.cc = :cc"),
    @NamedQuery(name = "Notes.findByExamen", query = "SELECT n FROM Notes n WHERE n.examen = :examen"),
    @NamedQuery(name = "Notes.findByRatrappage", query = "SELECT n FROM Notes n WHERE n.ratrappage = :ratrappage"),
    @NamedQuery(name = "Notes.findByCcC", query = "SELECT n FROM Notes n WHERE n.ccC = :ccC"),
    @NamedQuery(name = "Notes.findByCct", query = "SELECT n FROM Notes n WHERE n.cct = :cct"),
    @NamedQuery(name = "Notes.findByTotal", query = "SELECT n FROM Notes n WHERE n.total = :total"),
    @NamedQuery(name = "Notes.findByCote", query = "SELECT n FROM Notes n WHERE n.cote = :cote"),
    @NamedQuery(name = "Notes.findByPoints", query = "SELECT n FROM Notes n WHERE n.points = :points"),
    @NamedQuery(name = "Notes.findByMention", query = "SELECT n FROM Notes n WHERE n.mention = :mention"),
    @NamedQuery(name = "Notes.findByCctrois", query = "SELECT n FROM Notes n WHERE n.cctrois = :cctrois"),
    @NamedQuery(name = "Notes.findByCcquatre", query = "SELECT n FROM Notes n WHERE n.ccquatre = :ccquatre")})
public class Notes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "note_id")
    private Integer noteId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cc")
    private Double cc;
    @Column(name = "examen")
    private Double examen;
    @Column(name = "ratrappage")
    private Double ratrappage;
    @Column(name = "cc_c")
    private Double ccC;
    @Column(name = "cct")
    private Double cct;
    @Column(name = "total")
    private Double total;
    @Column(name = "cote")
    private String cote;
    @Column(name = "points")
    private Double points;
    @Column(name = "mention")
    private String mention;
    @Column(name = "cctrois")
    private Double cctrois;
    @Column(name = "ccquatre")
    private Double ccquatre;
    @JoinColumn(name = "matricule", referencedColumnName = "matricule")
    @ManyToOne(optional = false)
    private Etudiant matricule;
    @JoinColumn(name = "codes", referencedColumnName = "codes")
    @ManyToOne(optional = false)
    private Cours codes;

    public Notes() {
    }

    public Notes(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Double getCc() {
        return cc;
    }

    public void setCc(Double cc) {
        this.cc = cc;
    }

    public Double getExamen() {
        return examen;
    }

    public void setExamen(Double examen) {
        this.examen = examen;
    }

    public Double getRatrappage() {
        return ratrappage;
    }

    public void setRatrappage(Double ratrappage) {
        this.ratrappage = ratrappage;
    }

    public Double getCcC() {
        return ccC;
    }

    public void setCcC(Double ccC) {
        this.ccC = ccC;
    }

    public Double getCct() {
        return cct;
    }

    public void setCct(Double cct) {
        this.cct = cct;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCote() {
        return cote;
    }

    public void setCote(String cote) {
        this.cote = cote;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public Double getCctrois() {
        return cctrois;
    }

    public void setCctrois(Double cctrois) {
        this.cctrois = cctrois;
    }

    public Double getCcquatre() {
        return ccquatre;
    }

    public void setCcquatre(Double ccquatre) {
        this.ccquatre = ccquatre;
    }

    public Etudiant getMatricule() {
        return matricule;
    }

    public void setMatricule(Etudiant matricule) {
        this.matricule = matricule;
    }

    public Cours getCodes() {
        return codes;
    }

    public void setCodes(Cours codes) {
        this.codes = codes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noteId != null ? noteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notes)) {
            return false;
        }
        Notes other = (Notes) object;
        if ((this.noteId == null && other.noteId != null) || (this.noteId != null && !this.noteId.equals(other.noteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Notes[ noteId=" + noteId + " ]";
    }
    
}
