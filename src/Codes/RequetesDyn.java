/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author LArryJordan
 */
public class RequetesDyn {
    
   // public static String yooo = "meeer";
    
   
    public static List EtudiantListCours(int niv, EntityManager em){
        return em.createQuery("SELECT e FROM Etudiant e WHERE e.idNiveaux.idNiveaux = :eNiv")
                .setParameter("eNiv", niv)                
                .getResultList();
    }
       public static List EtudiantListCoursNotes(int niv, EntityManager em){
        return em.createQuery("SELECT e.matricule FROM Etudiant e WHERE e.idNiveaux.idNiveaux = :eNiv")
                .setParameter("eNiv", niv)                
                .getResultList();
    }
       public static List ProffListCours(int niv, EntityManager em){
           return em.createQuery("SELECT p FROM Proffesseur  p WHERE p.idNiveaux.idNiveaux = :pNiv")
                   .setParameter("pNiv", niv)
                   .getResultList();
       }
       
       public static List NoteListePourCours(int noteCourId, EntityManager em){
           return em.createQuery("SELECT n FROM Notes n WHERE n.codes.codes = :nCodes")
                   .setParameter("nCodes", noteCourId)
                   .getResultList();
       }
       
       public static List EtudianDetails(String matricule, EntityManager em){
           return em.createQuery("SELECT e FROM Etudiant e where e.matricule = :eMat")
                   .setParameter("eMat", matricule)
                   .getResultList();
       }
       
       public static List EtudiantAnneeList(String annee, EntityManager em){
           return em.createQuery("SELECT a.idAnee FROM Anee a")
                   .setParameter("aAnnee", annee)
                   .getResultList();
       }
       
       public static List NoteDuplice(String matricule, int code, EntityManager em){
           return em.createQuery("SELECT n FROM Notes n WHERE n.codes.codes = :cod AND n.matricule.matricule = :mat")
                   .setParameter("cod", code)
                   .setParameter("mat", matricule) 
                   .getResultList();
       }
              
      public static List staffLogin(String login, String pswd, EntityManager em){
          return em.createQuery("SELECT s FROM Staff s WHERE s.login = :log AND s.password = :psd")
                  .setParameter("log", login)
                  .setParameter("psd", pswd)
                  .getResultList();
      } 
          
      public static List updateEtudian(EntityManager em){          
          return em.createQuery("SELECT e FROM Etudiant e")
                  .getResultList();
      }        
      
      public static List updateCour(EntityManager em){          
          return em.createQuery("SELECT c FROM Cours c")
                  .getResultList();
      }  
       public static List updateCourA(String anee, EntityManager em)
       {
           return em.createQuery("SELECT c FROM Cours c WHERE c.idAnee.idAnee = :year")
                   .setParameter("year", anee)
                   .getResultList();
       }
      public static List etudiantQuery(String anee, EntityManager em)
      {
          return em.createQuery("SELECT e FROM Etudiant e WHERE e.aneeList.idAnee = :year")
                  .setParameter("year", anee)
                  .getResultList();
      }
       public static List anneeListA(String anee, EntityManager em){
           return em.createQuery("SELECT a FROM Anee a WHERE a.idAnee = :year")
                   .setParameter("year", anee)
                   .getResultList();
       }
      
      public static List anneeList(EntityManager em)
      {
          return em.createQuery("SELECT a FROM Anee a ORDER BY a.idAnee")
                  .getResultList();
      }
     
      public static List niveauPart(String nivo, EntityManager em)
      {
          return em.createQuery("SELECT n.idNiveaux FROM Niveaux n WHERE n.nom = :niv")
                  .setParameter("niv", nivo)
                  .getResultList();
      }
}
