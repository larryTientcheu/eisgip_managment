/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;


/**
 *
 * @author LArryJordan
 */
import java.sql.Connection;
public class Connexion {
     String chaineConn;
       public static Connection con;
              
    public String getChaineConn() {
        return chaineConn;
    }

    public void setChaineConn(String chaineConn) {
        this.chaineConn = chaineConn;
    }                
    public void setCon(Connection con) {
        this.con = con;
    }
        
    
}