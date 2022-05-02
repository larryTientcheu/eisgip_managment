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
@Table(name = "staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByIdStaff", query = "SELECT s FROM Staff s WHERE s.idStaff = :idStaff"),
    @NamedQuery(name = "Staff.findByNom", query = "SELECT s FROM Staff s WHERE s.nom = :nom"),
    @NamedQuery(name = "Staff.findByPoste", query = "SELECT s FROM Staff s WHERE s.poste = :poste"),
    @NamedQuery(name = "Staff.findByEmail", query = "SELECT s FROM Staff s WHERE s.email = :email"),
    @NamedQuery(name = "Staff.findByLogin", query = "SELECT s FROM Staff s WHERE s.login = :login"),
    @NamedQuery(name = "Staff.findByPassword", query = "SELECT s FROM Staff s WHERE s.password = :password")})
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_staff")
    private Integer idStaff;
    @Column(name = "nom")
    private String nom;
    @Column(name = "poste")
    private String poste;
    @Column(name = "email")
    private String email;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "id_address", referencedColumnName = "id_address")
    @ManyToOne
    private Addresse idAddress;
    @OneToMany(mappedBy = "idStaff")
    private List<Connection> connectionList;

    public Staff() {
    }

    public Staff(Integer idStaff) {
        this.idStaff = idStaff;
    }

    public Integer getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(Integer idStaff) {
        this.idStaff = idStaff;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Addresse getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Addresse idAddress) {
        this.idAddress = idAddress;
    }

    @XmlTransient
    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStaff != null ? idStaff.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.idStaff == null && other.idStaff != null) || (this.idStaff != null && !this.idStaff.equals(other.idStaff))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Staff[ idStaff=" + idStaff + " ]";
    }
    
}
