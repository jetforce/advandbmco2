/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jet
 */
public class Queries {

    /**
     * @param args the command line arguments
     */
    
    public static ArrayList<Query> query_set = new ArrayList<>();
    
    public static void main(String[] args) {
       Frame f = new Frame();
       
       Income_Location il = new Income_Location();
       f.addPanel("Num of fam w/ Income/Location", il);
       query_set.add(new place_income_count_query(il));
       
       Income_Location il2 = new Income_Location();
       f.addPanel("Total Income/Location", il2);
       query_set.add(new place_total_income_query(il2));
       
       Person_Income_Location il3 = new Person_Income_Location();
       f.addPanel("Total Income individual/location", il3);
       query_set.add(new place_person_income_query(il3));
       
       Education_Location il4 = new Education_Location();
       f.addPanel("Age Education Location", il4);
       query_set.add(new education_location_query(il4));
           
  /*     
       //Hannah, Rigel add here your query statement and Query UI. dapat in order.
       QueryBox1 box1 = new QueryBox1();
       f.addPanel("PWD", box1);
       query_set.add(new PWD_Query(box1));
       
       //For Voter Profile
       age_income_box box2 = new age_income_box();
       f.addPanel("Voter", box2);
       query_set.add(new Voter_Profile(box2));
       
       //For Phil Health Profile
       age_income_box box3 = new age_income_box();
       f.addPanel("PhilH 3 Query", box3);
       query_set.add(new Phil_Profile3(box3));
       
       //for ph 7
       age_income_box box4 = new age_income_box();
       f.addPanel("PhilH 7 Query", box4);
       query_set.add(new Phil_Profile7(box4));
       
       // For Poverty
       QueryBox1 box5 = new QueryBox1();
       f.addPanel("Poverty", box5);
       query_set.add(new Poverty_Query(box5));
       
       // For Poverty
       CBEP_Age_Box box6 = new CBEP_Age_Box();
       f.addPanel("CBEP", box6);
       query_set.add(new CBEP_Query(box6));
            
       //For reason
       age_income_box box7 = new age_income_box();
       f.addPanel("Reason",box7);
       query_set.add(new Reason(box7));
       box7.disableboxes();
   */
       f.setVisible(true);
       System.out.println("Hello");
    }
    
}
