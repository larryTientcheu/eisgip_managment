/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Niveaux;
import Entities.Cours;
import java.util.ArrayList;
import java.util.List;
import Entities.Anee;
import Entities.Addresse;
import Entities.Etudiant;
import Entities.Notes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class EtudiantJpaController implements Serializable {

    public EtudiantJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Etudiant etudiant) throws PreexistingEntityException, Exception {
        if (etudiant.getCoursList() == null) {
            etudiant.setCoursList(new ArrayList<Cours>());
        }
        if (etudiant.getAneeList() == null) {
            etudiant.setAneeList(new ArrayList<Anee>());
        }
        if (etudiant.getAddresseList() == null) {
            etudiant.setAddresseList(new ArrayList<Addresse>());
        }
        if (etudiant.getNotesList() == null) {
            etudiant.setNotesList(new ArrayList<Notes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Niveaux idNiveaux = etudiant.getIdNiveaux();
            if (idNiveaux != null) {
                idNiveaux = em.getReference(idNiveaux.getClass(), idNiveaux.getIdNiveaux());
                etudiant.setIdNiveaux(idNiveaux);
            }
            List<Cours> attachedCoursList = new ArrayList<Cours>();
            for (Cours coursListCoursToAttach : etudiant.getCoursList()) {
                coursListCoursToAttach = em.getReference(coursListCoursToAttach.getClass(), coursListCoursToAttach.getCodes());
                attachedCoursList.add(coursListCoursToAttach);
            }
            etudiant.setCoursList(attachedCoursList);
            List<Anee> attachedAneeList = new ArrayList<Anee>();
            for (Anee aneeListAneeToAttach : etudiant.getAneeList()) {
                aneeListAneeToAttach = em.getReference(aneeListAneeToAttach.getClass(), aneeListAneeToAttach.getIdAnee());
                attachedAneeList.add(aneeListAneeToAttach);
            }
            etudiant.setAneeList(attachedAneeList);
            List<Addresse> attachedAddresseList = new ArrayList<Addresse>();
            for (Addresse addresseListAddresseToAttach : etudiant.getAddresseList()) {
                addresseListAddresseToAttach = em.getReference(addresseListAddresseToAttach.getClass(), addresseListAddresseToAttach.getIdAddress());
                attachedAddresseList.add(addresseListAddresseToAttach);
            }
            etudiant.setAddresseList(attachedAddresseList);
            List<Notes> attachedNotesList = new ArrayList<Notes>();
            for (Notes notesListNotesToAttach : etudiant.getNotesList()) {
                notesListNotesToAttach = em.getReference(notesListNotesToAttach.getClass(), notesListNotesToAttach.getNoteId());
                attachedNotesList.add(notesListNotesToAttach);
            }
            etudiant.setNotesList(attachedNotesList);
            em.persist(etudiant);
            if (idNiveaux != null) {
                idNiveaux.getEtudiantList().add(etudiant);
                idNiveaux = em.merge(idNiveaux);
            }
            for (Cours coursListCours : etudiant.getCoursList()) {
                coursListCours.getEtudiantList().add(etudiant);
                coursListCours = em.merge(coursListCours);
            }
            for (Anee aneeListAnee : etudiant.getAneeList()) {
                aneeListAnee.getEtudiantList().add(etudiant);
                aneeListAnee = em.merge(aneeListAnee);
            }
            for (Addresse addresseListAddresse : etudiant.getAddresseList()) {
                addresseListAddresse.getEtudiantList().add(etudiant);
                addresseListAddresse = em.merge(addresseListAddresse);
            }
            for (Notes notesListNotes : etudiant.getNotesList()) {
                Etudiant oldMatriculeOfNotesListNotes = notesListNotes.getMatricule();
                notesListNotes.setMatricule(etudiant);
                notesListNotes = em.merge(notesListNotes);
                if (oldMatriculeOfNotesListNotes != null) {
                    oldMatriculeOfNotesListNotes.getNotesList().remove(notesListNotes);
                    oldMatriculeOfNotesListNotes = em.merge(oldMatriculeOfNotesListNotes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEtudiant(etudiant.getMatricule()) != null) {
                throw new PreexistingEntityException("Etudiant " + etudiant + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Etudiant etudiant) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etudiant persistentEtudiant = em.find(Etudiant.class, etudiant.getMatricule());
            Niveaux idNiveauxOld = persistentEtudiant.getIdNiveaux();
            Niveaux idNiveauxNew = etudiant.getIdNiveaux();
            List<Cours> coursListOld = persistentEtudiant.getCoursList();
            List<Cours> coursListNew = etudiant.getCoursList();
            List<Anee> aneeListOld = persistentEtudiant.getAneeList();
            List<Anee> aneeListNew = etudiant.getAneeList();
            List<Addresse> addresseListOld = persistentEtudiant.getAddresseList();
            List<Addresse> addresseListNew = etudiant.getAddresseList();
            List<Notes> notesListOld = persistentEtudiant.getNotesList();
            List<Notes> notesListNew = etudiant.getNotesList();
            List<String> illegalOrphanMessages = null;
            for (Notes notesListOldNotes : notesListOld) {
                if (!notesListNew.contains(notesListOldNotes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notes " + notesListOldNotes + " since its matricule field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idNiveauxNew != null) {
                idNiveauxNew = em.getReference(idNiveauxNew.getClass(), idNiveauxNew.getIdNiveaux());
                etudiant.setIdNiveaux(idNiveauxNew);
            }
            List<Cours> attachedCoursListNew = new ArrayList<Cours>();
            for (Cours coursListNewCoursToAttach : coursListNew) {
                coursListNewCoursToAttach = em.getReference(coursListNewCoursToAttach.getClass(), coursListNewCoursToAttach.getCodes());
                attachedCoursListNew.add(coursListNewCoursToAttach);
            }
            coursListNew = attachedCoursListNew;
            etudiant.setCoursList(coursListNew);
            List<Anee> attachedAneeListNew = new ArrayList<Anee>();
            for (Anee aneeListNewAneeToAttach : aneeListNew) {
                aneeListNewAneeToAttach = em.getReference(aneeListNewAneeToAttach.getClass(), aneeListNewAneeToAttach.getIdAnee());
                attachedAneeListNew.add(aneeListNewAneeToAttach);
            }
            aneeListNew = attachedAneeListNew;
            etudiant.setAneeList(aneeListNew);
            List<Addresse> attachedAddresseListNew = new ArrayList<Addresse>();
            for (Addresse addresseListNewAddresseToAttach : addresseListNew) {
                addresseListNewAddresseToAttach = em.getReference(addresseListNewAddresseToAttach.getClass(), addresseListNewAddresseToAttach.getIdAddress());
                attachedAddresseListNew.add(addresseListNewAddresseToAttach);
            }
            addresseListNew = attachedAddresseListNew;
            etudiant.setAddresseList(addresseListNew);
            List<Notes> attachedNotesListNew = new ArrayList<Notes>();
            for (Notes notesListNewNotesToAttach : notesListNew) {
                notesListNewNotesToAttach = em.getReference(notesListNewNotesToAttach.getClass(), notesListNewNotesToAttach.getNoteId());
                attachedNotesListNew.add(notesListNewNotesToAttach);
            }
            notesListNew = attachedNotesListNew;
            etudiant.setNotesList(notesListNew);
            etudiant = em.merge(etudiant);
            if (idNiveauxOld != null && !idNiveauxOld.equals(idNiveauxNew)) {
                idNiveauxOld.getEtudiantList().remove(etudiant);
                idNiveauxOld = em.merge(idNiveauxOld);
            }
            if (idNiveauxNew != null && !idNiveauxNew.equals(idNiveauxOld)) {
                idNiveauxNew.getEtudiantList().add(etudiant);
                idNiveauxNew = em.merge(idNiveauxNew);
            }
            for (Cours coursListOldCours : coursListOld) {
                if (!coursListNew.contains(coursListOldCours)) {
                    coursListOldCours.getEtudiantList().remove(etudiant);
                    coursListOldCours = em.merge(coursListOldCours);
                }
            }
            for (Cours coursListNewCours : coursListNew) {
                if (!coursListOld.contains(coursListNewCours)) {
                    coursListNewCours.getEtudiantList().add(etudiant);
                    coursListNewCours = em.merge(coursListNewCours);
                }
            }
            for (Anee aneeListOldAnee : aneeListOld) {
                if (!aneeListNew.contains(aneeListOldAnee)) {
                    aneeListOldAnee.getEtudiantList().remove(etudiant);
                    aneeListOldAnee = em.merge(aneeListOldAnee);
                }
            }
            for (Anee aneeListNewAnee : aneeListNew) {
                if (!aneeListOld.contains(aneeListNewAnee)) {
                    aneeListNewAnee.getEtudiantList().add(etudiant);
                    aneeListNewAnee = em.merge(aneeListNewAnee);
                }
            }
            for (Addresse addresseListOldAddresse : addresseListOld) {
                if (!addresseListNew.contains(addresseListOldAddresse)) {
                    addresseListOldAddresse.getEtudiantList().remove(etudiant);
                    addresseListOldAddresse = em.merge(addresseListOldAddresse);
                }
            }
            for (Addresse addresseListNewAddresse : addresseListNew) {
                if (!addresseListOld.contains(addresseListNewAddresse)) {
                    addresseListNewAddresse.getEtudiantList().add(etudiant);
                    addresseListNewAddresse = em.merge(addresseListNewAddresse);
                }
            }
            for (Notes notesListNewNotes : notesListNew) {
                if (!notesListOld.contains(notesListNewNotes)) {
                    Etudiant oldMatriculeOfNotesListNewNotes = notesListNewNotes.getMatricule();
                    notesListNewNotes.setMatricule(etudiant);
                    notesListNewNotes = em.merge(notesListNewNotes);
                    if (oldMatriculeOfNotesListNewNotes != null && !oldMatriculeOfNotesListNewNotes.equals(etudiant)) {
                        oldMatriculeOfNotesListNewNotes.getNotesList().remove(notesListNewNotes);
                        oldMatriculeOfNotesListNewNotes = em.merge(oldMatriculeOfNotesListNewNotes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = etudiant.getMatricule();
                if (findEtudiant(id) == null) {
                    throw new NonexistentEntityException("The etudiant with id " + id + " no longer exists.");
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
            Etudiant etudiant;
            try {
                etudiant = em.getReference(Etudiant.class, id);
                etudiant.getMatricule();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etudiant with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Notes> notesListOrphanCheck = etudiant.getNotesList();
            for (Notes notesListOrphanCheckNotes : notesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Etudiant (" + etudiant + ") cannot be destroyed since the Notes " + notesListOrphanCheckNotes + " in its notesList field has a non-nullable matricule field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Niveaux idNiveaux = etudiant.getIdNiveaux();
            if (idNiveaux != null) {
                idNiveaux.getEtudiantList().remove(etudiant);
                idNiveaux = em.merge(idNiveaux);
            }
            List<Cours> coursList = etudiant.getCoursList();
            for (Cours coursListCours : coursList) {
                coursListCours.getEtudiantList().remove(etudiant);
                coursListCours = em.merge(coursListCours);
            }
            List<Anee> aneeList = etudiant.getAneeList();
            for (Anee aneeListAnee : aneeList) {
                aneeListAnee.getEtudiantList().remove(etudiant);
                aneeListAnee = em.merge(aneeListAnee);
            }
            List<Addresse> addresseList = etudiant.getAddresseList();
            for (Addresse addresseListAddresse : addresseList) {
                addresseListAddresse.getEtudiantList().remove(etudiant);
                addresseListAddresse = em.merge(addresseListAddresse);
            }
            em.remove(etudiant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Etudiant> findEtudiantEntities() {
        return findEtudiantEntities(true, -1, -1);
    }

    public List<Etudiant> findEtudiantEntities(int maxResults, int firstResult) {
        return findEtudiantEntities(false, maxResults, firstResult);
    }

    private List<Etudiant> findEtudiantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Etudiant.class));
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

    public Etudiant findEtudiant(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Etudiant.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtudiantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Etudiant> rt = cq.from(Etudiant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
