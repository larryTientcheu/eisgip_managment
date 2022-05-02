/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;

import Controller.StaffJpaController;
import Entities.Staff;
import Frames.universalDialogue;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author LArryJordan
 */


public class Logins {
    
     EntityManager GLEntity = javax.persistence.Persistence.createEntityManagerFactory("EISGIP_MANAGEMENTPU").createEntityManager();
     StaffJpaController staff = new StaffJpaController(GLEntity.getEntityManagerFactory());   
     public void changeLoginUniv()
     {
     universalDialogue dialog = new universalDialogue(new javax.swing.JFrame(), true);
         dialog.universalDialogueText.setText("Login/Mot de Passe incorrect!!");         
         dialog.setTitle("Erreur de Connexion");
         dialog.setVisible(true);    
     }
     
    public boolean loginpswd(String login, String password )
    {
        List<Staff> stf;
     stf = RequetesDyn.staffLogin(login, password, GLEntity);
     if(stf.isEmpty())  {                 
         return true;    
     }
     else
     {    
       GestionConnection con = new GestionConnection();
       Staff staf = new Staff();
       staf.setIdStaff(stf.get(0).getIdStaff());
       staf.setNom(stf.get(0).getNom());
       staf.setPoste(stf.get(0).getPoste());
       con.connexionAuSystem(staf, GLEntity,"Connexion");
       Frames.Frame1.deconnexion(staf);
       return false;
     }
    }
        
}
