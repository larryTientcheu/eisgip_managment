/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.NonexistentEntityException;
import Entities.Addresse;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Etudiant;
import java.util.ArrayList;
import java.util.List;
import Entities.Staff;
import Entities.Proffesseur;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GRIMMTESHCO
 */
public class AddresseJpaController implements Serializable {

    public AddresseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Addresse addresse) {
        if (addresse.getEtudiantList() == null) {
            addresse.setEtudiantList(new ArrayList<Etudiant>());
        }
        if (addresse.getStaffList() == null) {
            addresse.setStaffList(new ArrayList<Staff>());
        }
        if (addresse.getProffesseurList() == null) {
            addresse.setProffesseurList(new ArrayList<Proffesseur>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Etudiant> attachedEtudiantList = new ArrayList<Etudiant>();
            for (Etudiant etudiantListEtudiantToAttach : addresse.getEtudiantList()) {
                etudiantListEtudiantToAttach = em.getReference(etudiantListEtudiantToAttach.getClass(), etudiantListEtudiantToAttach.getMatricule());
                attachedEtudiantList.add(etudiantListEtudiantToAttach);
            }
            addresse.setEtudiantList(attachedEtudiantList);
            List<Staff> attachedStaffList = new ArrayList<Staff>();
            for (Staff staffListStaffToAttach : addresse.getStaffList()) {
                staffListStaffToAttach = em.getReference(staffListStaffToAttach.getClass(), staffListStaffToAttach.getIdStaff());
                attachedStaffList.add(staffListStaffToAttach);
            }
            addresse.setStaffList(attachedStaffList);
            List<Proffesseur> attachedProffesseurList = new ArrayList<Proffesseur>();
            for (Proffesseur proffesseurListProffesseurToAttach : addresse.getProffesseurList()) {
                proffesseurListProffesseurToAttach = em.getReference(proffesseurListProffesseurToAttach.getClass(), proffesseurListProffesseurToAttach.getIdProff());
                attachedProffesseurList.add(proffesseurListProffesseurToAttach);
            }
            addresse.setProffesseurList(attachedProffesseurList);
            em.persist(addresse);
            for (Etudiant etudiantListEtudiant : addresse.getEtudiantList()) {
                etudiantListEtudiant.getAddresseList().add(addresse);
                etudiantListEtudiant = em.merge(etudiantListEtudiant);
            }
            for (Staff staffListStaff : addresse.getStaffList()) {
                Addresse oldIdAddressOfStaffListStaff = staffListStaff.getIdAddress();
                staffListStaff.setIdAddress(addresse);
                staffListStaff = em.merge(staffListStaff);
                if (oldIdAddressOfStaffListStaff != null) {
                    oldIdAddressOfStaffListStaff.getStaffList().remove(staffListStaff);
                    oldIdAddressOfStaffListStaff = em.merge(oldIdAddressOfStaffListStaff);
                }
            }
            for (Proffesseur proffesseurListProffesseur : addresse.getProffesseurList()) {
                Addresse oldIdAddressOfProffesseurListProffesseur = proffesseurListProffesseur.getIdAddress();
                proffesseurListProffesseur.setIdAddress(addresse);
                proffesseurListProffesseur = em.merge(proffesseurListProffesseur);
                if (oldIdAddressOfProffesseurListProffesseur != null) {
                    oldIdAddressOfProffesseurListProffesseur.getProffesseurList().remove(proffesseurListProffesseur);
                    oldIdAddressOfProffesseurListProffesseur = em.merge(oldIdAddressOfProffesseurListProffesseur);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Addresse addresse) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Addresse persistentAddresse = em.find(Addresse.class, addresse.getIdAddress());
            List<Etudiant> etudiantListOld = persistentAddresse.getEtudiantList();
            List<Etudiant> etudiantListNew = addresse.getEtudiantList();
            List<Staff> staffListOld = persistentAddresse.getStaffList();
            List<Staff> staffListNew = addresse.getStaffList();
            List<Proffesseur> proffesseurListOld = persistentAddresse.getProffesseurList();
            List<Proffesseur> proffesseurListNew = addresse.getProffesseurList();
            List<Etudiant> attachedEtudiantListNew = new ArrayList<Etudiant>();
            for (Etudiant etudiantListNewEtudiantToAttach : etudiantListNew) {
                etudiantListNewEtudiantToAttach = em.getReference(etudiantListNewEtudiantToAttach.getClass(), etudiantListNewEtudiantToAttach.getMatricule());
                attachedEtudiantListNew.add(etudiantListNewEtudiantToAttach);
            }
            etudiantListNew = attachedEtudiantListNew;
            addresse.setEtudiantList(etudiantListNew);
            List<Staff> attachedStaffListNew = new ArrayList<Staff>();
            for (Staff staffListNewStaffToAttach : staffListNew) {
                staffListNewStaffToAttach = em.getReference(staffListNewStaffToAttach.getClass(), staffListNewStaffToAttach.getIdStaff());
                attachedStaffListNew.add(staffListNewStaffToAttach);
            }
            staffListNew = attachedStaffListNew;
            addresse.setStaffList(staffListNew);
            List<Proffesseur> attachedProffesseurListNew = new ArrayList<Proffesseur>();
            for (Proffesseur proffesseurListNewProffesseurToAttach : proffesseurListNew) {
                proffesseurListNewProffesseurToAttach = em.getReference(proffesseurListNewProffesseurToAttach.getClass(), proffesseurListNewProffesseurToAttach.getIdProff());
                attachedProffesseurListNew.add(proffesseurListNewProffesseurToAttach);
            }
            proffesseurListNew = attachedProffesseurListNew;
            addresse.setProffesseurList(proffesseurListNew);
            addresse = em.merge(addresse);
            for (Etudiant etudiantListOldEtudiant : etudiantListOld) {
                if (!etudiantListNew.contains(etudiantListOldEtudiant)) {
                    etudiantListOldEtudiant.getAddresseList().remove(addresse);
                    etudiantListOldEtudiant = em.merge(etudiantListOldEtudiant);
                }
            }
            for (Etudiant etudiantListNewEtudiant : etudiantListNew) {
                if (!etudiantListOld.contains(etudiantListNewEtudiant)) {
                    etudiantListNewEtudiant.getAddresseList().add(addresse);
                    etudiantListNewEtudiant = em.merge(etudiantListNewEtudiant);
                }
            }
            for (Staff staffListOldStaff : staffListOld) {
                if (!staffListNew.contains(staffListOldStaff)) {
                    staffListOldStaff.setIdAddress(null);
                    staffListOldStaff = em.merge(staffListOldStaff);
                }
            }
            for (Staff staffListNewStaff : staffListNew) {
                if (!staffListOld.contains(staffListNewStaff)) {
                    Addresse oldIdAddressOfStaffListNewStaff = staffListNewStaff.getIdAddress();
                    staffListNewStaff.setIdAddress(addresse);
                    staffListNewStaff = em.merge(staffListNewStaff);
                    if (oldIdAddressOfStaffListNewStaff != null && !oldIdAddressOfStaffListNewStaff.equals(addresse)) {
                        oldIdAddressOfStaffListNewStaff.getStaffList().remove(staffListNewStaff);
                        oldIdAddressOfStaffListNewStaff = em.merge(oldIdAddressOfStaffListNewStaff);
                    }
                }
            }
            for (Proffesseur proffesseurListOldProffesseur : proffesseurListOld) {
                if (!proffesseurListNew.contains(proffesseurListOldProffesseur)) {
                    proffesseurListOldProffesseur.setIdAddress(null);
                    proffesseurListOldProffesseur = em.merge(proffesseurListOldProffesseur);
                }
            }
            for (Proffesseur proffesseurListNewProffesseur : proffesseurListNew) {
                if (!proffesseurListOld.contains(proffesseurListNewProffesseur)) {
                    Addresse oldIdAddressOfProffesseurListNewProffesseur = proffesseurListNewProffesseur.getIdAddress();
                    proffesseurListNewProffesseur.setIdAddress(addresse);
                    proffesseurListNewProffesseur = em.merge(proffesseurListNewProffesseur);
                    if (oldIdAddressOfProffesseurListNewProffesseur != null && !oldIdAddressOfProffesseurListNewProffesseur.equals(addresse)) {
                        oldIdAddressOfProffesseurListNewProffesseur.getProffesseurList().remove(proffesseurListNewProffesseur);
                        oldIdAddressOfProffesseurListNewProffesseur = em.merge(oldIdAddressOfProffesseurListNewProffesseur);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = addresse.getIdAddress();
                if (findAddresse(id) == null) {
                    throw new NonexistentEntityException("The addresse with id " + id + " no longer exists.");
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
            Addresse addresse;
            try {
                addresse = em.getReference(Addresse.class, id);
                addresse.getIdAddress();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The addresse with id " + id + " no longer exists.", enfe);
            }
            List<Etudiant> etudiantList = addresse.getEtudiantList();
            for (Etudiant etudiantListEtudiant : etudiantList) {
                etudiantListEtudiant.getAddresseList().remove(addresse);
                etudiantListEtudiant = em.merge(etudiantListEtudiant);
            }
            List<Staff> staffList = addresse.getStaffList();
            for (Staff staffListStaff : staffList) {
                staffListStaff.setIdAddress(null);
                staffListStaff = em.merge(staffListStaff);
            }
            List<Proffesseur> proffesseurList = addresse.getProffesseurList();
            for (Proffesseur proffesseurListProffesseur : proffesseurList) {
                proffesseurListProffesseur.setIdAddress(null);
                proffesseurListProffesseur = em.merge(proffesseurListProffesseur);
            }
            em.remove(addresse);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Addresse> findAddresseEntities() {
        return findAddresseEntities(true, -1, -1);
    }

    public List<Addresse> findAddresseEntities(int maxResults, int firstResult) {
        return findAddresseEntities(false, maxResults, firstResult);
    }

    private List<Addresse> findAddresseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Addresse.class));
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

    public Addresse findAddresse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Addresse.class, id);
        } finally {
            em.close();
        }
    }

    public int getAddresseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Addresse> rt = cq.from(Addresse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
