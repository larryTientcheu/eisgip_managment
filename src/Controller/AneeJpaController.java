/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.Anee;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Etudiant;
import java.util.ArrayList;
import java.util.List;
import Entities.Cours;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class AneeJpaController implements Serializable {

    public AneeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anee anee) throws PreexistingEntityException, Exception {
        if (anee.getEtudiantList() == null) {
            anee.setEtudiantList(new ArrayList<Etudiant>());
        }
        if (anee.getCoursList() == null) {
            anee.setCoursList(new ArrayList<Cours>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Etudiant> attachedEtudiantList = new ArrayList<Etudiant>();
            for (Etudiant etudiantListEtudiantToAttach : anee.getEtudiantList()) {
                etudiantListEtudiantToAttach = em.getReference(etudiantListEtudiantToAttach.getClass(), etudiantListEtudiantToAttach.getMatricule());
                attachedEtudiantList.add(etudiantListEtudiantToAttach);
            }
            anee.setEtudiantList(attachedEtudiantList);
            List<Cours> attachedCoursList = new ArrayList<Cours>();
            for (Cours coursListCoursToAttach : anee.getCoursList()) {
                coursListCoursToAttach = em.getReference(coursListCoursToAttach.getClass(), coursListCoursToAttach.getCodes());
                attachedCoursList.add(coursListCoursToAttach);
            }
            anee.setCoursList(attachedCoursList);
            em.persist(anee);
            for (Etudiant etudiantListEtudiant : anee.getEtudiantList()) {
                etudiantListEtudiant.getAneeList().add(anee);
                etudiantListEtudiant = em.merge(etudiantListEtudiant);
            }
            for (Cours coursListCours : anee.getCoursList()) {
                Anee oldIdAneeOfCoursListCours = coursListCours.getIdAnee();
                coursListCours.setIdAnee(anee);
                coursListCours = em.merge(coursListCours);
                if (oldIdAneeOfCoursListCours != null) {
                    oldIdAneeOfCoursListCours.getCoursList().remove(coursListCours);
                    oldIdAneeOfCoursListCours = em.merge(oldIdAneeOfCoursListCours);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnee(anee.getIdAnee()) != null) {
                throw new PreexistingEntityException("Anee " + anee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anee anee) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anee persistentAnee = em.find(Anee.class, anee.getIdAnee());
            List<Etudiant> etudiantListOld = persistentAnee.getEtudiantList();
            List<Etudiant> etudiantListNew = anee.getEtudiantList();
            List<Cours> coursListOld = persistentAnee.getCoursList();
            List<Cours> coursListNew = anee.getCoursList();
            List<String> illegalOrphanMessages = null;
            for (Cours coursListOldCours : coursListOld) {
                if (!coursListNew.contains(coursListOldCours)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cours " + coursListOldCours + " since its idAnee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Etudiant> attachedEtudiantListNew = new ArrayList<Etudiant>();
            for (Etudiant etudiantListNewEtudiantToAttach : etudiantListNew) {
                etudiantListNewEtudiantToAttach = em.getReference(etudiantListNewEtudiantToAttach.getClass(), etudiantListNewEtudiantToAttach.getMatricule());
                attachedEtudiantListNew.add(etudiantListNewEtudiantToAttach);
            }
            etudiantListNew = attachedEtudiantListNew;
            anee.setEtudiantList(etudiantListNew);
            List<Cours> attachedCoursListNew = new ArrayList<Cours>();
            for (Cours coursListNewCoursToAttach : coursListNew) {
                coursListNewCoursToAttach = em.getReference(coursListNewCoursToAttach.getClass(), coursListNewCoursToAttach.getCodes());
                attachedCoursListNew.add(coursListNewCoursToAttach);
            }
            coursListNew = attachedCoursListNew;
            anee.setCoursList(coursListNew);
            anee = em.merge(anee);
            for (Etudiant etudiantListOldEtudiant : etudiantListOld) {
                if (!etudiantListNew.contains(etudiantListOldEtudiant)) {
                    etudiantListOldEtudiant.getAneeList().remove(anee);
                    etudiantListOldEtudiant = em.merge(etudiantListOldEtudiant);
                }
            }
            for (Etudiant etudiantListNewEtudiant : etudiantListNew) {
                if (!etudiantListOld.contains(etudiantListNewEtudiant)) {
                    etudiantListNewEtudiant.getAneeList().add(anee);
                    etudiantListNewEtudiant = em.merge(etudiantListNewEtudiant);
                }
            }
            for (Cours coursListNewCours : coursListNew) {
                if (!coursListOld.contains(coursListNewCours)) {
                    Anee oldIdAneeOfCoursListNewCours = coursListNewCours.getIdAnee();
                    coursListNewCours.setIdAnee(anee);
                    coursListNewCours = em.merge(coursListNewCours);
                    if (oldIdAneeOfCoursListNewCours != null && !oldIdAneeOfCoursListNewCours.equals(anee)) {
                        oldIdAneeOfCoursListNewCours.getCoursList().remove(coursListNewCours);
                        oldIdAneeOfCoursListNewCours = em.merge(oldIdAneeOfCoursListNewCours);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = anee.getIdAnee();
                if (findAnee(id) == null) {
                    throw new NonexistentEntityException("The anee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anee anee;
            try {
                anee = em.getReference(Anee.class, id);
                anee.getIdAnee();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cours> coursListOrphanCheck = anee.getCoursList();
            for (Cours coursListOrphanCheckCours : coursListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Anee (" + anee + ") cannot be destroyed since the Cours " + coursListOrphanCheckCours + " in its coursList field has a non-nullable idAnee field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Etudiant> etudiantList = anee.getEtudiantList();
            for (Etudiant etudiantListEtudiant : etudiantList) {
                etudiantListEtudiant.getAneeList().remove(anee);
                etudiantListEtudiant = em.merge(etudiantListEtudiant);
            }
            em.remove(anee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anee> findAneeEntities() {
        return findAneeEntities(true, -1, -1);
    }

    public List<Anee> findAneeEntities(int maxResults, int firstResult) {
        return findAneeEntities(false, maxResults, firstResult);
    }

    private List<Anee> findAneeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anee.class));
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

    public Anee findAnee(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anee.class, id);
        } finally {
            em.close();
        }
    }

    public int getAneeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anee> rt = cq.from(Anee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
