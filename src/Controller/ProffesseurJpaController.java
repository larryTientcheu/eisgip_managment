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
import Entities.Niveaux;
import Entities.Addresse;
import Entities.Cours;
import Entities.Proffesseur;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class ProffesseurJpaController implements Serializable {

    public ProffesseurJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proffesseur proffesseur) {
        if (proffesseur.getCoursList() == null) {
            proffesseur.setCoursList(new ArrayList<Cours>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Niveaux idNiveaux = proffesseur.getIdNiveaux();
            if (idNiveaux != null) {
                idNiveaux = em.getReference(idNiveaux.getClass(), idNiveaux.getIdNiveaux());
                proffesseur.setIdNiveaux(idNiveaux);
            }
            Addresse idAddress = proffesseur.getIdAddress();
            if (idAddress != null) {
                idAddress = em.getReference(idAddress.getClass(), idAddress.getIdAddress());
                proffesseur.setIdAddress(idAddress);
            }
            List<Cours> attachedCoursList = new ArrayList<Cours>();
            for (Cours coursListCoursToAttach : proffesseur.getCoursList()) {
                coursListCoursToAttach = em.getReference(coursListCoursToAttach.getClass(), coursListCoursToAttach.getCodes());
                attachedCoursList.add(coursListCoursToAttach);
            }
            proffesseur.setCoursList(attachedCoursList);
            em.persist(proffesseur);
            if (idNiveaux != null) {
                idNiveaux.getProffesseurList().add(proffesseur);
                idNiveaux = em.merge(idNiveaux);
            }
            if (idAddress != null) {
                idAddress.getProffesseurList().add(proffesseur);
                idAddress = em.merge(idAddress);
            }
            for (Cours coursListCours : proffesseur.getCoursList()) {
                coursListCours.getProffesseurList().add(proffesseur);
                coursListCours = em.merge(coursListCours);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proffesseur proffesseur) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proffesseur persistentProffesseur = em.find(Proffesseur.class, proffesseur.getIdProff());
            Niveaux idNiveauxOld = persistentProffesseur.getIdNiveaux();
            Niveaux idNiveauxNew = proffesseur.getIdNiveaux();
            Addresse idAddressOld = persistentProffesseur.getIdAddress();
            Addresse idAddressNew = proffesseur.getIdAddress();
            List<Cours> coursListOld = persistentProffesseur.getCoursList();
            List<Cours> coursListNew = proffesseur.getCoursList();
            if (idNiveauxNew != null) {
                idNiveauxNew = em.getReference(idNiveauxNew.getClass(), idNiveauxNew.getIdNiveaux());
                proffesseur.setIdNiveaux(idNiveauxNew);
            }
            if (idAddressNew != null) {
                idAddressNew = em.getReference(idAddressNew.getClass(), idAddressNew.getIdAddress());
                proffesseur.setIdAddress(idAddressNew);
            }
            List<Cours> attachedCoursListNew = new ArrayList<Cours>();
            for (Cours coursListNewCoursToAttach : coursListNew) {
                coursListNewCoursToAttach = em.getReference(coursListNewCoursToAttach.getClass(), coursListNewCoursToAttach.getCodes());
                attachedCoursListNew.add(coursListNewCoursToAttach);
            }
            coursListNew = attachedCoursListNew;
            proffesseur.setCoursList(coursListNew);
            proffesseur = em.merge(proffesseur);
            if (idNiveauxOld != null && !idNiveauxOld.equals(idNiveauxNew)) {
                idNiveauxOld.getProffesseurList().remove(proffesseur);
                idNiveauxOld = em.merge(idNiveauxOld);
            }
            if (idNiveauxNew != null && !idNiveauxNew.equals(idNiveauxOld)) {
                idNiveauxNew.getProffesseurList().add(proffesseur);
                idNiveauxNew = em.merge(idNiveauxNew);
            }
            if (idAddressOld != null && !idAddressOld.equals(idAddressNew)) {
                idAddressOld.getProffesseurList().remove(proffesseur);
                idAddressOld = em.merge(idAddressOld);
            }
            if (idAddressNew != null && !idAddressNew.equals(idAddressOld)) {
                idAddressNew.getProffesseurList().add(proffesseur);
                idAddressNew = em.merge(idAddressNew);
            }
            for (Cours coursListOldCours : coursListOld) {
                if (!coursListNew.contains(coursListOldCours)) {
                    coursListOldCours.getProffesseurList().remove(proffesseur);
                    coursListOldCours = em.merge(coursListOldCours);
                }
            }
            for (Cours coursListNewCours : coursListNew) {
                if (!coursListOld.contains(coursListNewCours)) {
                    coursListNewCours.getProffesseurList().add(proffesseur);
                    coursListNewCours = em.merge(coursListNewCours);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proffesseur.getIdProff();
                if (findProffesseur(id) == null) {
                    throw new NonexistentEntityException("The proffesseur with id " + id + " no longer exists.");
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
            Proffesseur proffesseur;
            try {
                proffesseur = em.getReference(Proffesseur.class, id);
                proffesseur.getIdProff();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proffesseur with id " + id + " no longer exists.", enfe);
            }
            Niveaux idNiveaux = proffesseur.getIdNiveaux();
            if (idNiveaux != null) {
                idNiveaux.getProffesseurList().remove(proffesseur);
                idNiveaux = em.merge(idNiveaux);
            }
            Addresse idAddress = proffesseur.getIdAddress();
            if (idAddress != null) {
                idAddress.getProffesseurList().remove(proffesseur);
                idAddress = em.merge(idAddress);
            }
            List<Cours> coursList = proffesseur.getCoursList();
            for (Cours coursListCours : coursList) {
                coursListCours.getProffesseurList().remove(proffesseur);
                coursListCours = em.merge(coursListCours);
            }
            em.remove(proffesseur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proffesseur> findProffesseurEntities() {
        return findProffesseurEntities(true, -1, -1);
    }

    public List<Proffesseur> findProffesseurEntities(int maxResults, int firstResult) {
        return findProffesseurEntities(false, maxResults, firstResult);
    }

    private List<Proffesseur> findProffesseurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proffesseur.class));
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

    public Proffesseur findProffesseur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proffesseur.class, id);
        } finally {
            em.close();
        }
    }

    public int getProffesseurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proffesseur> rt = cq.from(Proffesseur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
