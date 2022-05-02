/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;

import Controller.ConnectionJpaController;
import Entities.Connection;
import Entities.Staff;
import java.util.Date;
import javax.persistence.EntityManager;

/**
 *
 * @author LArryJordan
 */
public  class GestionConnection {
    public  Staff staff;

    public Object getStaff() {
        return staff;
    }
    public void connexionAuSystem(Staff staf,EntityManager em,String action)
    {
        staff = staf;
        ConnectionJpaController connect = new ConnectionJpaController(em.getEntityManagerFactory());
        Connection con = new Connection();
        con.setIdStaff(new Staff(staf.getIdStaff()));
        con.setDatetime(new Date().toString());
        con.setAction(action);
        try {
            connect.create(con);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean droits(Staff staf)
    {        
        if(staf.getPoste().equalsIgnoreCase("admin")||(staf.getPoste().equalsIgnoreCase("Secret")))       
            return true;
        else 
            return false;
        
    }
    

}
