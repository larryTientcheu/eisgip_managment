/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codes;

import Entities.Notes;

/**
 *
 * @author LArryJordan
 */
public class CalculeNotes {
    private String cote, mention;
    private double points;
    
    
    public double melangerCc(double cc1, double cc2,double cc3, double cc4, Notes note)
      {
          double f;
          if (cc2==-1)    {                
              note.setCcC(null);
              note.setCctrois(null);
              note.setCcquatre(null);
               return cc1;
          }
          if(cc3 == -1)
          {
              note.setCctrois(null);
              note.setCcquatre(null);
              return (cc1+cc2)/2;
          }
          if(cc4 == -1)
          {
              note.setCcquatre(null);
              return (cc1+cc2+cc3)/3;
          }
                              
          else
          {
              f = (cc1+cc2+cc3+cc4)/4;
              return f;
          }
          
      }
    
    public double calculeTotale(double ccT, double examen, double ratrappage,Notes note)
    {
        double t;
        if (ratrappage == -1)
        {
            t = (((ccT*0.3)+(examen*0.7))*100)/20;
            note.setRatrappage(null);
            return t;
        }
        else
        {
            t = (((ccT*0.3)+(ratrappage*0.7))*100)/20;
            return t;
        }
    }
    
    public void calculeCote(double totale){
        if(totale >= 80 && totale <=100)
        {
            cote = "A";
            points = 4;
            mention = "Très Bien";
        }
        else if (totale >=75  && totale <80 )
        {
            cote="A-"; points =3.70 ; mention = "Bien";
        }
         else if (totale >=70  && totale <75 )
        {
              cote="B+"; points =3.30 ; mention = "Bien";
        }
         else if (totale >=65  && totale <70 )
        {
              cote="B"; points = 3.00; mention = "Assez Bien";
        }
         else if (totale >=60  && totale <65 )
        {
              cote="B-"; points = 2.70 ; mention = "Assez Bien";
        }
         else if (totale >=55  && totale <60 )
        {
              cote="C+"; points = 2.30 ; mention = "Passable";
        }
         else if (totale >=50  && totale <55 )
        {
              cote="C"; points = 2.00 ; mention = "Passable";
        }
         else if (totale >=45  && totale <50 )
        {
              cote="C-"; points = 1.70; mention = "Crédits Captitalisé non transferable";
        }
         else if (totale >=40  && totale <45 )
        {
              cote="D+"; points = 1.30; mention = "Crédits Captitalisé non transferable";
        }
         else if (totale >=35  && totale <40 )
        {
              cote="D"; points = 1.00 ; mention = "Crédits Captitalisé non transferable";
        }
         else if (totale >=30  && totale <35 )
        {
              cote="E"; points = 0.00 ; mention = "Echec";
        }
         else 
        {
           cote = "F"; 
           points = 0.00; 
           mention="Echec";
        }
    }

    public String getCote() {
        return cote;
    }

    public void setCote(String cote) {
        this.cote = cote;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
    
}
