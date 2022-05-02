/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;

import Controller.NotesJpaController;
import Entities.Notes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LArryJordan
 */
public class TransactDivers {
    public static void destroyNotesDestroyCours(List<Notes> n, EntityManagerFactory emf)
    {
        EntityManager em = emf.createEntityManager();
        try{
        em.getTransaction().begin();
        NotesJpaController note = new NotesJpaController(em.getEntityManagerFactory());
        for (Notes noteListe : n)
        {
            note.destroy(noteListe.getNoteId());
        }
        em.getTransaction().commit();       
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
