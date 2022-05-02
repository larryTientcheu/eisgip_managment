/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.Compteur;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author GRIMMTESHCO
 */
public class CompteurJpaController implements Serializable {

    public CompteurJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compteur compteur) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(compteur);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompteur(compteur.getIdCompteur()) != null) {
                throw new PreexistingEntityException("Compteur " + compteur + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compteur compteur) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            compteur = em.merge(compteur);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compteur.getIdCompteur();
                if (findCompteur(id) == null) {
                    throw new NonexistentEntityException("The compteur with id " + id + " no longer exists.");
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
            Compteur compteur;
            try {
                compteur = em.getReference(Compteur.class, id);
                compteur.getIdCompteur();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compteur with id " + id + " no longer exists.", enfe);
            }
            em.remove(compteur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compteur> findCompteurEntities() {
        return findCompteurEntities(true, -1, -1);
    }

    public List<Compteur> findCompteurEntities(int maxResults, int firstResult) {
        return findCompteurEntities(false, maxResults, firstResult);
    }

    private List<Compteur> findCompteurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compteur.class));
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

    public Compteur findCompteur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compteur.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompteurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compteur> rt = cq.from(Compteur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
