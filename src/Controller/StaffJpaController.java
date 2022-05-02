/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Addresse;
import Entities.Connection;
import Entities.Staff;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class StaffJpaController implements Serializable {

    public StaffJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Staff staff) {
        if (staff.getConnectionList() == null) {
            staff.setConnectionList(new ArrayList<Connection>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Addresse idAddress = staff.getIdAddress();
            if (idAddress != null) {
                idAddress = em.getReference(idAddress.getClass(), idAddress.getIdAddress());
                staff.setIdAddress(idAddress);
            }
            List<Connection> attachedConnectionList = new ArrayList<Connection>();
            for (Connection connectionListConnectionToAttach : staff.getConnectionList()) {
                connectionListConnectionToAttach = em.getReference(connectionListConnectionToAttach.getClass(), connectionListConnectionToAttach.getIdconnexion());
                attachedConnectionList.add(connectionListConnectionToAttach);
            }
            staff.setConnectionList(attachedConnectionList);
            em.persist(staff);
            if (idAddress != null) {
                idAddress.getStaffList().add(staff);
                idAddress = em.merge(idAddress);
            }
            for (Connection connectionListConnection : staff.getConnectionList()) {
                Staff oldIdStaffOfConnectionListConnection = connectionListConnection.getIdStaff();
                connectionListConnection.setIdStaff(staff);
                connectionListConnection = em.merge(connectionListConnection);
                if (oldIdStaffOfConnectionListConnection != null) {
                    oldIdStaffOfConnectionListConnection.getConnectionList().remove(connectionListConnection);
                    oldIdStaffOfConnectionListConnection = em.merge(oldIdStaffOfConnectionListConnection);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Staff staff) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Staff persistentStaff = em.find(Staff.class, staff.getIdStaff());
            Addresse idAddressOld = persistentStaff.getIdAddress();
            Addresse idAddressNew = staff.getIdAddress();
            List<Connection> connectionListOld = persistentStaff.getConnectionList();
            List<Connection> connectionListNew = staff.getConnectionList();
            if (idAddressNew != null) {
                idAddressNew = em.getReference(idAddressNew.getClass(), idAddressNew.getIdAddress());
                staff.setIdAddress(idAddressNew);
            }
            List<Connection> attachedConnectionListNew = new ArrayList<Connection>();
            for (Connection connectionListNewConnectionToAttach : connectionListNew) {
                connectionListNewConnectionToAttach = em.getReference(connectionListNewConnectionToAttach.getClass(), connectionListNewConnectionToAttach.getIdconnexion());
                attachedConnectionListNew.add(connectionListNewConnectionToAttach);
            }
            connectionListNew = attachedConnectionListNew;
            staff.setConnectionList(connectionListNew);
            staff = em.merge(staff);
            if (idAddressOld != null && !idAddressOld.equals(idAddressNew)) {
                idAddressOld.getStaffList().remove(staff);
                idAddressOld = em.merge(idAddressOld);
            }
            if (idAddressNew != null && !idAddressNew.equals(idAddressOld)) {
                idAddressNew.getStaffList().add(staff);
                idAddressNew = em.merge(idAddressNew);
            }
            for (Connection connectionListOldConnection : connectionListOld) {
                if (!connectionListNew.contains(connectionListOldConnection)) {
                    connectionListOldConnection.setIdStaff(null);
                    connectionListOldConnection = em.merge(connectionListOldConnection);
                }
            }
            for (Connection connectionListNewConnection : connectionListNew) {
                if (!connectionListOld.contains(connectionListNewConnection)) {
                    Staff oldIdStaffOfConnectionListNewConnection = connectionListNewConnection.getIdStaff();
                    connectionListNewConnection.setIdStaff(staff);
                    connectionListNewConnection = em.merge(connectionListNewConnection);
                    if (oldIdStaffOfConnectionListNewConnection != null && !oldIdStaffOfConnectionListNewConnection.equals(staff)) {
                        oldIdStaffOfConnectionListNewConnection.getConnectionList().remove(connectionListNewConnection);
                        oldIdStaffOfConnectionListNewConnection = em.merge(oldIdStaffOfConnectionListNewConnection);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = staff.getIdStaff();
                if (findStaff(id) == null) {
                    throw new NonexistentEntityException("The staff with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Staff staff;
            try {
                staff = em.getReference(Staff.class, id);
                staff.getIdStaff();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The staff with id " + id + " no longer exists.", enfe);
            }
            Addresse idAddress = staff.getIdAddress();
            if (idAddress != null) {
                idAddress.getStaffList().remove(staff);
                idAddress = em.merge(idAddress);
            }
            List<Connection> connectionList = staff.getConnectionList();
            for (Connection connectionListConnection : connectionList) {
                connectionListConnection.setIdStaff(null);
                connectionListConnection = em.merge(connectionListConnection);
            }
            em.remove(staff);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Staff> findStaffEntities() {
        return findStaffEntities(true, -1, -1);
    }

    public List<Staff> findStaffEntities(int maxResults, int firstResult) {
        return findStaffEntities(false, maxResults, firstResult);
    }

    private List<Staff> findStaffEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Staff.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Staff findStaff(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Staff.class, id);
        } finally {
            em.close();
        }
    }

    public int getStaffCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Staff> rt = cq.from(Staff.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
