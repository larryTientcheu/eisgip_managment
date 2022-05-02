/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;

import Frames.universalDialogue;
import static com.lowagie.text.pdf.PdfName.H;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author LArryJordan
 */
public class ReportApercue {
    JasperPrint JPrint;
    public void releveNotes(int a, double b, String c){
        try{
        String pathToFile = ".\\Reports\\ReleveNotes\\ReleveNotes12N.jasper";
        HashMap<String,Object> params = new HashMap<>();     
         params.put("Etudiant De Niveaux", a);
         params.put("Pourcentage Exigé", b);
         params.put("Anee",c);
        JPrint=JasperFillManager.fillReport(pathToFile,params,Connexion.con);     
        JasperViewer.viewReport(JPrint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public void recapitulatif(int a, String c){
        try{
        String pathToFile = ".\\Reports\\PVRecapitulatif\\PvRecap.jasper";       
        HashMap<String,Object> params = new HashMap<>();     
         params.put("Etudiant De Niveaux", a);
         params.put("Anee",c);
        JPrint=JasperFillManager.fillReport(pathToFile,params,Connexion.con);
        JasperViewer.viewReport(JPrint,false);
        }
        catch(Exception e){            
             universalDialogue dialog = new universalDialogue(new javax.swing.JFrame(), true);
         dialog.universalDialogueText.setText("Template Introuvable, Contacter L'administrateur");         
         dialog.setTitle("Erreur de Generation");
         dialog.setVisible(true);    
        }
     }
        public void ceritificat(){
        try{
        String pathToFile = ".\\Reports\\PVRecapitulatif\\PvRecap.jasper";
        JPrint=JasperFillManager.fillReport(pathToFile,null,Connexion.con);
        JasperViewer.viewReport(JPrint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        }
        public void ficheDinscrip(){
        try{
        String pathToFile = ".\\Reports\\PVRecapitulatif\\PvRecap.jasper";
        JPrint=JasperFillManager.fillReport(pathToFile,null,Connexion.con);
        JasperViewer.viewReport(JPrint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
         public void classement(int a, String c){
        try{
        String pathToFile = ".\\Reports\\Classements\\Classement.jasper";       
        HashMap<String,Object> params = new HashMap<>();     
         params.put("Etudiant De Niveaux", a);
         params.put("Anee",c);
        JPrint=JasperFillManager.fillReport(pathToFile,params,Connexion.con);
        JasperViewer.viewReport(JPrint,false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
         }
    
}
