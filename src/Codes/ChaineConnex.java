/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LArryJordan
 */
public class ChaineConnex {
    
    private Connection con; 
    private String chaine;

    public String getChaine() {
        return chaine;
    }
    public Connection getCon() {
        return con;
    }
  
    public boolean testConnectBD(String ip, String bd, String usr, String psw)
    {
        try{    
            chaine = "jdbc:postgresql://"+ip+"/"+bd+"?user="+usr+"&password="+psw;
        con = DriverManager.getConnection(chaine);
        return true;
        }       
        catch(SQLException e)
        {
            return false;
        }
    }
}
