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
import Entities.Etudiant;
import Entities.Cours;
import Entities.Notes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class NotesJpaController implements Serializable {

    public NotesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notes notes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etudiant matricule = notes.getMatricule();
            if (matricule != null) {
                matricule = em.getReference(matricule.getClass(), matricule.getMatricule());
                notes.setMatricule(matricule);
            }
            Cours codes = notes.getCodes();
            if (codes != null) {
                codes = em.getReference(codes.getClass(), codes.getCodes());
                notes.setCodes(codes);
            }
            em.persist(notes);
            if (matricule != null) {
                matricule.getNotesList().add(notes);
                matricule = em.merge(matricule);
            }
            if (codes != null) {
                codes.getNotesList().add(notes);
                codes = em.merge(codes);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notes notes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notes persistentNotes = em.find(Notes.class, notes.getNoteId());
            Etudiant matriculeOld = persistentNotes.getMatricule();
            Etudiant matriculeNew = notes.getMatricule();
            Cours codesOld = persistentNotes.getCodes();
            Cours codesNew = notes.getCodes();
            if (matriculeNew != null) {
                matriculeNew = em.getReference(matriculeNew.getClass(), matriculeNew.getMatricule());
                notes.setMatricule(matriculeNew);
            }
            if (codesNew != null) {
                codesNew = em.getReference(codesNew.getClass(), codesNew.getCodes());
                notes.setCodes(codesNew);
            }
            notes = em.merge(notes);
            if (matriculeOld != null && !matriculeOld.equals(matriculeNew)) {
                matriculeOld.getNotesList().remove(notes);
                matriculeOld = em.merge(matriculeOld);
            }
            if (matriculeNew != null && !matriculeNew.equals(matriculeOld)) {
                matriculeNew.getNotesList().add(notes);
                matriculeNew = em.merge(matriculeNew);
            }
            if (codesOld != null && !codesOld.equals(codesNew)) {
                codesOld.getNotesList().remove(notes);
                codesOld = em.merge(codesOld);
            }
            if (codesNew != null && !codesNew.equals(codesOld)) {
                codesNew.getNotesList().add(notes);
                codesNew = em.merge(codesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notes.getNoteId();
                if (findNotes(id) == null) {
                    throw new NonexistentEntityException("The notes with id " + id + " no longer exists.");
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
            Notes notes;
            try {
                notes = em.getReference(Notes.class, id);
                notes.getNoteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notes with id " + id + " no longer exists.", enfe);
            }
            Etudiant matricule = notes.getMatricule();
            if (matricule != null) {
                matricule.getNotesList().remove(notes);
                matricule = em.merge(matricule);
            }
            Cours codes = notes.getCodes();
            if (codes != null) {
                codes.getNotesList().remove(notes);
                codes = em.merge(codes);
            }
            em.remove(notes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notes> findNotesEntities() {
        return findNotesEntities(true, -1, -1);
    }

    public List<Notes> findNotesEntities(int maxResults, int firstResult) {
        return findNotesEntities(false, maxResults, firstResult);
    }

    private List<Notes> findNotesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notes.class));
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

    public Notes findNotes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notes.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notes> rt = cq.from(Notes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
