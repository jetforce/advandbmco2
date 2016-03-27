/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 *
 * @author Jett
 */
public class place_person_income_query implements Query{

    private Person_Income_Location box;
    
    
    
    public place_person_income_query(Person_Income_Location box){
        this.box = box;
    }
    
    private String getSelect(){
        Location loc =this.box.getLoc();
        ArrayList<String> s_list = loc.getLocations(); 
        String init = "place_name ,";
        for(int i=1; i< s_list.size(); i++){
            init = init + s_list.get(i)+" ,";
        }
        return init;
    }
    
     private String getIncomeType(){
        Age age = this.box.getAge();
        Range[] rlist = age.getRanges();
        String init="";
        
        for(int i=0; i< rlist.length; i++){            
            init = init + "WHEN age_yr BETWEEN "+ rlist[i].small +" and "+ rlist[i].big +" THEN '"+rlist[i].toString()+"'\n";
        }
        
        return init;
    }
    
    private String getWhere(){
        Age incomes = this.box.getAge();
        Range[] rlist = incomes.getRanges();
        String init= "age_yr "+" >= "+ rlist[0].small +" and age_yr <= "+ rlist[0].big +"\n";
        
        for(int i=1; i< rlist.length; i++){            
            init = init + "or age_yr >= "+ rlist[i].small +" and  age_yr <= "+ rlist[i].big +"\n";
        }
        
        return init;
        
    }
  
     
    private String getLastWhere(){
         Location loc =this.box.getLoc();
        ArrayList<String> s_list = loc.getLocations(); 
        String init ="place_id ,";
                
        for(int i=1; i< s_list.size(); i++){
            init = init + s_list.get(i)+" ,";
        }
        
        return init;      
        
    }
     
    
    private String sliceGender(){
        Age age = this.box.getAge();
        String s = age.getGender();
        String init="";
        if(!s.matches("Both")){
            init = "and sex = "+s+"\n";
        }
        
        return init;
        
    }
    

    
    
    
    public String makeQuery(){
        String init = "select "+  getSelect() +"age_group, SUM(wagcshm + wagkndm)  \n" +
                        "from `survey` as d, \n" +
                        "(select person_id ,sex,\n" +
                        "	(CASE\n" +
                                getIncomeType()+
                        "    END) as age_group\n" +
                        "from person\n" +
                        "where "+getWhere() +
                        ")as p,\n" +
                        "location as l\n" +
                        "where d.person_id = p.person_id\n" +
                        "and l.house_id = d.house_id\n" +
                        sliceGender()+
                        "group by "+ getLastWhere() +"  age_group";
        
        return init;
    }
    
    
    
    
    @Override
    public ResultSet go() {
        
        String q = makeQuery();
        System.out.println(q);
          Statement s= null;
       ResultSet rs = null;
       String query;
        try {
            query = q;
            s = Data.con.createStatement();
            s.execute(query);
            rs = s.getResultSet();
            System.out.println(query);
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;    
    }
    
    
}
