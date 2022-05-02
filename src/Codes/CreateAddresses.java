/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codes;

import Controller.AddresseJpaController;
import Entities.Addresse;
import javax.persistence.EntityManager;
import Entities.Compteur;
import Controller.CompteurJpaController;

/**
 *
 * @author LArryJordan
 */
public class CreateAddresses {

    public void etudiant_addresse(Addresse addresse, EntityManager entity) {
        
        Compteur count = new Compteur();
        AddresseJpaController addr = new AddresseJpaController(entity.getEntityManagerFactory());
        CompteurJpaController countControl = new CompteurJpaController(entity.getEntityManagerFactory());
        CompteurU compt = new CompteurU();

        compt.CompteurU(entity, addresse, count);

        try {

            addr.create(addresse);
            count.setAddresse(count.getAddresse() + 1);
            count.setEtudiant(count.getEtudiant()+1);
            countControl.edit(count);

        } catch (Exception e) {
            e.toString();
        }
    }
}
