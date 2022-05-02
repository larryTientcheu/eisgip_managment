/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.NonexistentEntityException;
import Entities.Connection;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Staff;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class ConnectionJpaController implements Serializable {

    public ConnectionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Connection connection) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Staff idStaff = connection.getIdStaff();
            if (idStaff != null) {
                idStaff = em.getReference(idStaff.getClass(), idStaff.getIdStaff());
                connection.setIdStaff(idStaff);
            }
            em.persist(connection);
            if (idStaff != null) {
                idStaff.getConnectionList().add(connection);
                idStaff = em.merge(idStaff);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Connection connection) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Connection persistentConnection = em.find(Connection.class, connection.getIdconnexion());
            Staff idStaffOld = persistentConnection.getIdStaff();
            Staff idStaffNew = connection.getIdStaff();
            if (idStaffNew != null) {
                idStaffNew = em.getReference(idStaffNew.getClass(), idStaffNew.getIdStaff());
                connection.setIdStaff(idStaffNew);
            }
            connection = em.merge(connection);
            if (idStaffOld != null && !idStaffOld.equals(idStaffNew)) {
                idStaffOld.getConnectionList().remove(connection);
                idStaffOld = em.merge(idStaffOld);
            }
            if (idStaffNew != null && !idStaffNew.equals(idStaffOld)) {
                idStaffNew.getConnectionList().add(connection);
                idStaffNew = em.merge(idStaffNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = connection.getIdconnexion();
                if (findConnection(id) == null) {
                    throw new NonexistentEntityException("The connection with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Connection connection;
            try {
                connection = em.getReference(Connection.class, id);
                connection.getIdconnexion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The connection with id " + id + " no longer exists.", enfe);
            }
            Staff idStaff = connection.getIdStaff();
            if (idStaff != null) {
                idStaff.getConnectionList().remove(connection);
                idStaff = em.merge(idStaff);
            }
            em.remove(connection);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Connection> findConnectionEntities() {
        return findConnectionEntities(true, -1, -1);
    }

    public List<Connection> findConnectionEntities(int maxResults, int firstResult) {
        return findConnectionEntities(false, maxResults, firstResult);
    }

    private List<Connection> findConnectionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Connection.class));
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

    public Connection findConnection(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Connection.class, id);
        } finally {
            em.close();
        }
    }

    public int getConnectionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Connection> rt = cq.from(Connection.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
