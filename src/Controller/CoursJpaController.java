/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Niveaux;
import Entities.Anee;
import Entities.Cours;
import Entities.Proffesseur;
import java.util.ArrayList;
import java.util.List;
import Entities.Etudiant;
import Entities.Notes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class CoursJpaController implements Serializable {

    public CoursJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cours cours) {
        if (cours.getProffesseurList() == null) {
            cours.setProffesseurList(new ArrayList<Proffesseur>());
        }
        if (cours.getEtudiantList() == null) {
            cours.setEtudiantList(new ArrayList<Etudiant>());
        }
        if (cours.getNotesList() == null) {
            cours.setNotesList(new ArrayList<Notes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Niveaux idNiveaux = cours.getIdNiveaux();
            if (idNiveaux != null) {
                idNiveaux = em.getReference(idNiveaux.getClass(), idNiveaux.getIdNiveaux());
                cours.setIdNiveaux(idNiveaux);
            }
            Anee idAnee = cours.getIdAnee();
            if (idAnee != null) {
                idAnee = em.getReference(idAnee.getClass(), idAnee.getIdAnee());
                cours.setIdAnee(idAnee);
            }
            List<Proffesseur> attachedProffesseurList = new ArrayList<Proffesseur>();
            for (Proffesseur proffesseurListProffesseurToAttach : cours.getProffesseurList()) {
                proffesseurListProffesseurToAttach = em.getReference(proffesseurListProffesseurToAttach.getClass(), proffesseurListProffesseurToAttach.getIdProff());
                attachedProffesseurList.add(proffesseurListProffesseurToAttach);
            }
            cours.setProffesseurList(attachedProffesseurList);
            List<Etudiant> attachedEtudiantList = new ArrayList<Etudiant>();
            for (Etudiant etudiantListEtudiantToAttach : cours.getEtudiantList()) {
                etudiantListEtudiantToAttach = em.getReference(etudiantListEtudiantToAttach.getClass(), etudiantListEtudiantToAttach.getMatricule());
                attachedEtudiantList.add(etudiantListEtudiantToAttach);
            }
            cours.setEtudiantList(attachedEtudiantList);
            List<Notes> attachedNotesList = new ArrayList<Notes>();
            for (Notes notesListNotesToAttach : cours.getNotesList()) {
                notesListNotesToAttach = em.getReference(notesListNotesToAttach.getClass(), notesListNotesToAttach.getNoteId());
                attachedNotesList.add(notesListNotesToAttach);
            }
            cours.setNotesList(attachedNotesList);
            em.persist(cours);
            if (idNiveaux != null) {
                idNiveaux.getCoursList().add(cours);
                idNiveaux = em.merge(idNiveaux);
            }
            if (idAnee != null) {
                idAnee.getCoursList().add(cours);
                idAnee = em.merge(idAnee);
            }
            for (Proffesseur proffesseurListProffesseur : cours.getProffesseurList()) {
                proffesseurListProffesseur.getCoursList().add(cours);
                proffesseurListProffesseur = em.merge(proffesseurListProffesseur);
            }
            for (Etudiant etudiantListEtudiant : cours.getEtudiantList()) {
                etudiantListEtudiant.getCoursList().add(cours);
                etudiantListEtudiant = em.merge(etudiantListEtudiant);
            }
            for (Notes notesListNotes : cours.getNotesList()) {
                Cours oldCodesOfNotesListNotes = notesListNotes.getCodes();
                notesListNotes.setCodes(cours);
                notesListNotes = em.merge(notesListNotes);
                if (oldCodesOfNotesListNotes != null) {
                    oldCodesOfNotesListNotes.getNotesList().remove(notesListNotes);
                    oldCodesOfNotesListNotes = em.merge(oldCodesOfNotesListNotes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cours cours) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cours persistentCours = em.find(Cours.class, cours.getCodes());
            Niveaux idNiveauxOld = persistentCours.getIdNiveaux();
            Niveaux idNiveauxNew = cours.getIdNiveaux();
            Anee idAneeOld = persistentCours.getIdAnee();
            Anee idAneeNew = cours.getIdAnee();
            List<Proffesseur> proffesseurListOld = persistentCours.getProffesseurList();
            List<Proffesseur> proffesseurListNew = cours.getProffesseurList();
            List<Etudiant> etudiantListOld = persistentCours.getEtudiantList();
            List<Etudiant> etudiantListNew = cours.getEtudiantList();
            List<Notes> notesListOld = persistentCours.getNotesList();
            List<Notes> notesListNew = cours.getNotesList();
            List<String> illegalOrphanMessages = null;
            for (Notes notesListOldNotes : notesListOld) {
                if (!notesListNew.contains(notesListOldNotes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notes " + notesListOldNotes + " since its codes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idNiveauxNew != null) {
                idNiveauxNew = em.getReference(idNiveauxNew.getClass(), idNiveauxNew.getIdNiveaux());
                cours.setIdNiveaux(idNiveauxNew);
            }
            if (idAneeNew != null) {
                idAneeNew = em.getReference(idAneeNew.getClass(), idAneeNew.getIdAnee());
                cours.setIdAnee(idAneeNew);
            }
            List<Proffesseur> attachedProffesseurListNew = new ArrayList<Proffesseur>();
            for (Proffesseur proffesseurListNewProffesseurToAttach : proffesseurListNew) {
                proffesseurListNewProffesseurToAttach = em.getReference(proffesseurListNewProffesseurToAttach.getClass(), proffesseurListNewProffesseurToAttach.getIdProff());
                attachedProffesseurListNew.add(proffesseurListNewProffesseurToAttach);
            }
            proffesseurListNew = attachedProffesseurListNew;
            cours.setProffesseurList(proffesseurListNew);
            List<Etudiant> attachedEtudiantListNew = new ArrayList<Etudiant>();
            for (Etudiant etudiantListNewEtudiantToAttach : etudiantListNew) {
                etudiantListNewEtudiantToAttach = em.getReference(etudiantListNewEtudiantToAttach.getClass(), etudiantListNewEtudiantToAttach.getMatricule());
                attachedEtudiantListNew.add(etudiantListNewEtudiantToAttach);
            }
            etudiantListNew = attachedEtudiantListNew;
            cours.setEtudiantList(etudiantListNew);
            List<Notes> attachedNotesListNew = new ArrayList<Notes>();
            for (Notes notesListNewNotesToAttach : notesListNew) {
                notesListNewNotesToAttach = em.getReference(notesListNewNotesToAttach.getClass(), notesListNewNotesToAttach.getNoteId());
                attachedNotesListNew.add(notesListNewNotesToAttach);
            }
            notesListNew = attachedNotesListNew;
            cours.setNotesList(notesListNew);
            cours = em.merge(cours);
            if (idNiveauxOld != null && !idNiveauxOld.equals(idNiveauxNew)) {
                idNiveauxOld.getCoursList().remove(cours);
                idNiveauxOld = em.merge(idNiveauxOld);
            }
            if (idNiveauxNew != null && !idNiveauxNew.equals(idNiveauxOld)) {
                idNiveauxNew.getCoursList().add(cours);
                idNiveauxNew = em.merge(idNiveauxNew);
            }
            if (idAneeOld != null && !idAneeOld.equals(idAneeNew)) {
                idAneeOld.getCoursList().remove(cours);
                idAneeOld = em.merge(idAneeOld);
            }
            if (idAneeNew != null && !idAneeNew.equals(idAneeOld)) {
                idAneeNew.getCoursList().add(cours);
                idAneeNew = em.merge(idAneeNew);
            }
            for (Proffesseur proffesseurListOldProffesseur : proffesseurListOld) {
                if (!proffesseurListNew.contains(proffesseurListOldProffesseur)) {
                    proffesseurListOldProffesseur.getCoursList().remove(cours);
                    proffesseurListOldProffesseur = em.merge(proffesseurListOldProffesseur);
                }
            }
            for (Proffesseur proffesseurListNewProffesseur : proffesseurListNew) {
                if (!proffesseurListOld.contains(proffesseurListNewProffesseur)) {
                    proffesseurListNewProffesseur.getCoursList().add(cours);
                    proffesseurListNewProffesseur = em.merge(proffesseurListNewProffesseur);
                }
            }
            for (Etudiant etudiantListOldEtudiant : etudiantListOld) {
                if (!etudiantListNew.contains(etudiantListOldEtudiant)) {
                    etudiantListOldEtudiant.getCoursList().remove(cours);
                    etudiantListOldEtudiant = em.merge(etudiantListOldEtudiant);
                }
            }
            for (Etudiant etudiantListNewEtudiant : etudiantListNew) {
                if (!etudiantListOld.contains(etudiantListNewEtudiant)) {
                    etudiantListNewEtudiant.getCoursList().add(cours);
                    etudiantListNewEtudiant = em.merge(etudiantListNewEtudiant);
                }
            }
            for (Notes notesListNewNotes : notesListNew) {
                if (!notesListOld.contains(notesListNewNotes)) {
                    Cours oldCodesOfNotesListNewNotes = notesListNewNotes.getCodes();
                    notesListNewNotes.setCodes(cours);
                    notesListNewNotes = em.merge(notesListNewNotes);
                    if (oldCodesOfNotesListNewNotes != null && !oldCodesOfNotesListNewNotes.equals(cours)) {
                        oldCodesOfNotesListNewNotes.getNotesList().remove(notesListNewNotes);
                        oldCodesOfNotesListNewNotes = em.merge(oldCodesOfNotesListNewNotes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cours.getCodes();
                if (findCours(id) == null) {
                    throw new NonexistentEntityException("The cours with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cours cours;
            try {
                cours = em.getReference(Cours.class, id);
                cours.getCodes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cours with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Notes> notesListOrphanCheck = cours.getNotesList();
            for (Notes notesListOrphanCheckNotes : notesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cours (" + cours + ") cannot be destroyed since the Notes " + notesListOrphanCheckNotes + " in its notesList field has a non-nullable codes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Niveaux idNiveaux = cours.getIdNiveaux();
            if (idNiveaux != null) {
                idNiveaux.getCoursList().remove(cours);
                idNiveaux = em.merge(idNiveaux);
            }
            Anee idAnee = cours.getIdAnee();
            if (idAnee != null) {
                idAnee.getCoursList().remove(cours);
                idAnee = em.merge(idAnee);
            }
            List<Proffesseur> proffesseurList = cours.getProffesseurList();
            for (Proffesseur proffesseurListProffesseur : proffesseurList) {
                proffesseurListProffesseur.getCoursList().remove(cours);
                proffesseurListProffesseur = em.merge(proffesseurListProffesseur);
            }
            List<Etudiant> etudiantList = cours.getEtudiantList();
            for (Etudiant etudiantListEtudiant : etudiantList) {
                etudiantListEtudiant.getCoursList().remove(cours);
                etudiantListEtudiant = em.merge(etudiantListEtudiant);
            }
            em.remove(cours);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cours> findCoursEntities() {
        return findCoursEntities(true, -1, -1);
    }

    public List<Cours> findCoursEntities(int maxResults, int firstResult) {
        return findCoursEntities(false, maxResults, firstResult);
    }

    private List<Cours> findCoursEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cours.class));
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

    public Cours findCours(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cours.class, id);
        } finally {
            em.close();
        }
    }

    public int getCoursCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cours> rt = cq.from(Cours.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
