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
import Entities.Etudiant;
import java.util.ArrayList;
import java.util.List;
import Entities.Cours;
import Entities.Niveaux;
import Entities.Proffesseur;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class NiveauxJpaController implements Serializable {

    public NiveauxJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Niveaux niveaux) throws PreexistingEntityException, Exception {
        if (niveaux.getEtudiantList() == null) {
            niveaux.setEtudiantList(new ArrayList<Etudiant>());
        }
        if (niveaux.getCoursList() == null) {
            niveaux.setCoursList(new ArrayList<Cours>());
        }
        if (niveaux.getProffesseurList() == null) {
            niveaux.setProffesseurList(new ArrayList<Proffesseur>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Etudiant> attachedEtudiantList = new ArrayList<Etudiant>();
            for (Etudiant etudiantListEtudiantToAttach : niveaux.getEtudiantList()) {
                etudiantListEtudiantToAttach = em.getReference(etudiantListEtudiantToAttach.getClass(), etudiantListEtudiantToAttach.getMatricule());
                attachedEtudiantList.add(etudiantListEtudiantToAttach);
            }
            niveaux.setEtudiantList(attachedEtudiantList);
            List<Cours> attachedCoursList = new ArrayList<Cours>();
            for (Cours coursListCoursToAttach : niveaux.getCoursList()) {
                coursListCoursToAttach = em.getReference(coursListCoursToAttach.getClass(), coursListCoursToAttach.getCodes());
                attachedCoursList.add(coursListCoursToAttach);
            }
            niveaux.setCoursList(attachedCoursList);
            List<Proffesseur> attachedProffesseurList = new ArrayList<Proffesseur>();
            for (Proffesseur proffesseurListProffesseurToAttach : niveaux.getProffesseurList()) {
                proffesseurListProffesseurToAttach = em.getReference(proffesseurListProffesseurToAttach.getClass(), proffesseurListProffesseurToAttach.getIdProff());
                attachedProffesseurList.add(proffesseurListProffesseurToAttach);
            }
            niveaux.setProffesseurList(attachedProffesseurList);
            em.persist(niveaux);
            for (Etudiant etudiantListEtudiant : niveaux.getEtudiantList()) {
                Niveaux oldIdNiveauxOfEtudiantListEtudiant = etudiantListEtudiant.getIdNiveaux();
                etudiantListEtudiant.setIdNiveaux(niveaux);
                etudiantListEtudiant = em.merge(etudiantListEtudiant);
                if (oldIdNiveauxOfEtudiantListEtudiant != null) {
                    oldIdNiveauxOfEtudiantListEtudiant.getEtudiantList().remove(etudiantListEtudiant);
                    oldIdNiveauxOfEtudiantListEtudiant = em.merge(oldIdNiveauxOfEtudiantListEtudiant);
                }
            }
            for (Cours coursListCours : niveaux.getCoursList()) {
                Niveaux oldIdNiveauxOfCoursListCours = coursListCours.getIdNiveaux();
                coursListCours.setIdNiveaux(niveaux);
                coursListCours = em.merge(coursListCours);
                if (oldIdNiveauxOfCoursListCours != null) {
                    oldIdNiveauxOfCoursListCours.getCoursList().remove(coursListCours);
                    oldIdNiveauxOfCoursListCours = em.merge(oldIdNiveauxOfCoursListCours);
                }
            }
            for (Proffesseur proffesseurListProffesseur : niveaux.getProffesseurList()) {
                Niveaux oldIdNiveauxOfProffesseurListProffesseur = proffesseurListProffesseur.getIdNiveaux();
                proffesseurListProffesseur.setIdNiveaux(niveaux);
                proffesseurListProffesseur = em.merge(proffesseurListProffesseur);
                if (oldIdNiveauxOfProffesseurListProffesseur != null) {
                    oldIdNiveauxOfProffesseurListProffesseur.getProffesseurList().remove(proffesseurListProffesseur);
                    oldIdNiveauxOfProffesseurListProffesseur = em.merge(oldIdNiveauxOfProffesseurListProffesseur);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNiveaux(niveaux.getIdNiveaux()) != null) {
                throw new PreexistingEntityException("Niveaux " + niveaux + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Niveaux niveaux) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Niveaux persistentNiveaux = em.find(Niveaux.class, niveaux.getIdNiveaux());
            List<Etudiant> etudiantListOld = persistentNiveaux.getEtudiantList();
            List<Etudiant> etudiantListNew = niveaux.getEtudiantList();
            List<Cours> coursListOld = persistentNiveaux.getCoursList();
            List<Cours> coursListNew = niveaux.getCoursList();
            List<Proffesseur> proffesseurListOld = persistentNiveaux.getProffesseurList();
            List<Proffesseur> proffesseurListNew = niveaux.getProffesseurList();
            List<String> illegalOrphanMessages = null;
            for (Etudiant etudiantListOldEtudiant : etudiantListOld) {
                if (!etudiantListNew.contains(etudiantListOldEtudiant)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Etudiant " + etudiantListOldEtudiant + " since its idNiveaux field is not nullable.");
                }
            }
            for (Cours coursListOldCours : coursListOld) {
                if (!coursListNew.contains(coursListOldCours)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cours " + coursListOldCours + " since its idNiveaux field is not nullable.");
                }
            }
            for (Proffesseur proffesseurListOldProffesseur : proffesseurListOld) {
                if (!proffesseurListNew.contains(proffesseurListOldProffesseur)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proffesseur " + proffesseurListOldProffesseur + " since its idNiveaux field is not nullable.");
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
            niveaux.setEtudiantList(etudiantListNew);
            List<Cours> attachedCoursListNew = new ArrayList<Cours>();
            for (Cours coursListNewCoursToAttach : coursListNew) {
                coursListNewCoursToAttach = em.getReference(coursListNewCoursToAttach.getClass(), coursListNewCoursToAttach.getCodes());
                attachedCoursListNew.add(coursListNewCoursToAttach);
            }
            coursListNew = attachedCoursListNew;
            niveaux.setCoursList(coursListNew);
            List<Proffesseur> attachedProffesseurListNew = new ArrayList<Proffesseur>();
            for (Proffesseur proffesseurListNewProffesseurToAttach : proffesseurListNew) {
                proffesseurListNewProffesseurToAttach = em.getReference(proffesseurListNewProffesseurToAttach.getClass(), proffesseurListNewProffesseurToAttach.getIdProff());
                attachedProffesseurListNew.add(proffesseurListNewProffesseurToAttach);
            }
            proffesseurListNew = attachedProffesseurListNew;
            niveaux.setProffesseurList(proffesseurListNew);
            niveaux = em.merge(niveaux);
            for (Etudiant etudiantListNewEtudiant : etudiantListNew) {
                if (!etudiantListOld.contains(etudiantListNewEtudiant)) {
                    Niveaux oldIdNiveauxOfEtudiantListNewEtudiant = etudiantListNewEtudiant.getIdNiveaux();
                    etudiantListNewEtudiant.setIdNiveaux(niveaux);
                    etudiantListNewEtudiant = em.merge(etudiantListNewEtudiant);
                    if (oldIdNiveauxOfEtudiantListNewEtudiant != null && !oldIdNiveauxOfEtudiantListNewEtudiant.equals(niveaux)) {
                        oldIdNiveauxOfEtudiantListNewEtudiant.getEtudiantList().remove(etudiantListNewEtudiant);
                        oldIdNiveauxOfEtudiantListNewEtudiant = em.merge(oldIdNiveauxOfEtudiantListNewEtudiant);
                    }
                }
            }
            for (Cours coursListNewCours : coursListNew) {
                if (!coursListOld.contains(coursListNewCours)) {
                    Niveaux oldIdNiveauxOfCoursListNewCours = coursListNewCours.getIdNiveaux();
                    coursListNewCours.setIdNiveaux(niveaux);
                    coursListNewCours = em.merge(coursListNewCours);
                    if (oldIdNiveauxOfCoursListNewCours != null && !oldIdNiveauxOfCoursListNewCours.equals(niveaux)) {
                        oldIdNiveauxOfCoursListNewCours.getCoursList().remove(coursListNewCours);
                        oldIdNiveauxOfCoursListNewCours = em.merge(oldIdNiveauxOfCoursListNewCours);
                    }
                }
            }
            for (Proffesseur proffesseurListNewProffesseur : proffesseurListNew) {
                if (!proffesseurListOld.contains(proffesseurListNewProffesseur)) {
                    Niveaux oldIdNiveauxOfProffesseurListNewProffesseur = proffesseurListNewProffesseur.getIdNiveaux();
                    proffesseurListNewProffesseur.setIdNiveaux(niveaux);
                    proffesseurListNewProffesseur = em.merge(proffesseurListNewProffesseur);
                    if (oldIdNiveauxOfProffesseurListNewProffesseur != null && !oldIdNiveauxOfProffesseurListNewProffesseur.equals(niveaux)) {
                        oldIdNiveauxOfProffesseurListNewProffesseur.getProffesseurList().remove(proffesseurListNewProffesseur);
                        oldIdNiveauxOfProffesseurListNewProffesseur = em.merge(oldIdNiveauxOfProffesseurListNewProffesseur);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = niveaux.getIdNiveaux();
                if (findNiveaux(id) == null) {
                    throw new NonexistentEntityException("The niveaux with id " + id + " no longer exists.");
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
            Niveaux niveaux;
            try {
                niveaux = em.getReference(Niveaux.class, id);
                niveaux.getIdNiveaux();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The niveaux with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Etudiant> etudiantListOrphanCheck = niveaux.getEtudiantList();
            for (Etudiant etudiantListOrphanCheckEtudiant : etudiantListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Niveaux (" + niveaux + ") cannot be destroyed since the Etudiant " + etudiantListOrphanCheckEtudiant + " in its etudiantList field has a non-nullable idNiveaux field.");
            }
            List<Cours> coursListOrphanCheck = niveaux.getCoursList();
            for (Cours coursListOrphanCheckCours : coursListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Niveaux (" + niveaux + ") cannot be destroyed since the Cours " + coursListOrphanCheckCours + " in its coursList field has a non-nullable idNiveaux field.");
            }
            List<Proffesseur> proffesseurListOrphanCheck = niveaux.getProffesseurList();
            for (Proffesseur proffesseurListOrphanCheckProffesseur : proffesseurListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Niveaux (" + niveaux + ") cannot be destroyed since the Proffesseur " + proffesseurListOrphanCheckProffesseur + " in its proffesseurList field has a non-nullable idNiveaux field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(niveaux);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Niveaux> findNiveauxEntities() {
        return findNiveauxEntities(true, -1, -1);
    }

    public List<Niveaux> findNiveauxEntities(int maxResults, int firstResult) {
        return findNiveauxEntities(false, maxResults, firstResult);
    }

    private List<Niveaux> findNiveauxEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Niveaux.class));
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

    public Niveaux findNiveaux(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Niveaux.class, id);
        } finally {
            em.close();
        }
    }

    public int getNiveauxCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Niveaux> rt = cq.from(Niveaux.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
