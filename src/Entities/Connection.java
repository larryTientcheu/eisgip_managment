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
@Table(name = "connection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Connection.findAll", query = "SELECT c FROM Connection c"),
    @NamedQuery(name = "Connection.findByIdconnexion", query = "SELECT c FROM Connection c WHERE c.idconnexion = :idconnexion"),
    @NamedQuery(name = "Connection.findByDatetime", query = "SELECT c FROM Connection c WHERE c.datetime = :datetime"),
    @NamedQuery(name = "Connection.findByAction", query = "SELECT c FROM Connection c WHERE c.action = :action")})
public class Connection implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconnexion")
    private Long idconnexion;
    @Column(name = "datetime")
    private String datetime;
    @Column(name = "action")
    private String action;
    @JoinColumn(name = "id_staff", referencedColumnName = "id_staff")
    @ManyToOne
    private Staff idStaff;

    public Connection() {
    }

    public Connection(Long idconnexion) {
        this.idconnexion = idconnexion;
    }

    public Long getIdconnexion() {
        return idconnexion;
    }

    public void setIdconnexion(Long idconnexion) {
        this.idconnexion = idconnexion;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Staff getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(Staff idStaff) {
        this.idStaff = idStaff;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconnexion != null ? idconnexion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Connection)) {
            return false;
        }
        Connection other = (Connection) object;
        if ((this.idconnexion == null && other.idconnexion != null) || (this.idconnexion != null && !this.idconnexion.equals(other.idconnexion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Connection[ idconnexion=" + idconnexion + " ]";
    }
    
}
