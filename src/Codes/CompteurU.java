/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import Entities.Addresse;
import Entities.Compteur;

/**
 *
 * @author LArryJordan
 */



public class CompteurU {
    
  public void CompteurU(EntityManager em, Addresse addresse, Compteur count){          
    List<Compteur> addresseList = new ArrayList<>();
        
    addresseList = em.createNamedQuery("Compteur.findAll").getResultList(); 
    
    count.setIdCompteur(addresseList.get(0).getIdCompteur());
    count.setAddresse(addresseList.get(0).getAddresse());
    count.setAnnee(addresseList.get(0).getAnnee());
    count.setCours(addresseList.get(0).getCours());
    count.setEtudiant(addresseList.get(0).getEtudiant());
    count.setNiveaux(addresseList.get(0).getNiveaux());
    count.setNotes(addresseList.get(0).getNotes());
    count.setStaff(addresseList.get(0).getStaff());
    count.setProffeseur(addresseList.get(0).getProffeseur()); 
   // addresse.setIdAddress(addresseList.get(0).getAddresse());
    em.clear();
    
    }
    
}
