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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jett
 */
public class education_location_query implements Query{


    private Education_Location box;
    
    public education_location_query(Education_Location box){
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
    
     private String getAgeType(){
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
     
    
    private String sliceEduGender(){
        Edu edu = this.box.getEdu();
        ArrayList<String> s_list = edu.getFilter();
        String init="";
        
        
        Age age = this.box.getAge();
        String s = age.getGender();
        String init2="";
        if(!s.matches("Both")){
            init2 = " and sex = "+s+"\n";
        }
        
        
        
        
        if(s_list.size()!=0){
            init =" and ( educal = "+ s_list.get(0) +"\n";
              
            for(int i=1; i< s_list.size();i++){
             init = init +"or educal = " +s_list.get(i)+"\n";
            }
            
            init = init + " )";
        }
        
        init = init + init2;
        
        return init;
        
    }
    
        
    private String getIncomeType(){
        Income incomes = this.box.getIncome();
        Range[] rlist = incomes.getRanges();
        String init="";
        
        for(int i=0; i< rlist.length; i++){            
            init = init + "WHEN "+ incomes.getMoneyType() +" BETWEEN "+ rlist[i].small +" and "+ rlist[i].big +" THEN '"+rlist[i].toString()+"'\n";
        }
        
        return init;
    }

    private String Income_getWhere(){
        Income incomes = this.box.getIncome();
        Range[] rlist = incomes.getRanges();
        String init= incomes.getMoneyType()+ " >= "+ rlist[0].small +" and "+incomes.getMoneyType() + "<= "+ rlist[0].big +"\n";
        
        for(int i=1; i< rlist.length; i++){            
            init = init + "or "+incomes.getMoneyType()+ " >= "+ rlist[i].small +" and "+incomes.getMoneyType() + "<= "+ rlist[i].big +"\n";
        }
        
        return init;
        
    }
    
    private String getGroupby(){
         Location loc =this.box.getLoc();
        ArrayList<String> s_list = loc.getLocations(); 
        String init ="place_id ";
                
        for(int i=1; i< s_list.size(); i++){
            init = init +" ,"+ s_list.get(i);
        }
        
        return init;      
        
    }
    
    public String makeQuery(){
        String init = "select "+  getSelect() +"age_group, income_group, educal, count(educal)  \n" +
                        "from (select person_id,house_id, educal from `survey`) as d, \n" +
                        "(select person_id ,sex,\n" +
                        "	(CASE\n" +
                                getAgeType()+
                        "    END) as age_group\n" +
                        "from person\n" +
                        "where "+getWhere() +
                        ")as p,\n" +
                       "(select house_id,avg_income,\n" +
                        "	(CASE\n" +
                        getIncomeType() +
                        "        END\n" +
                        "	) as income_group\n" +
                        "from family\n" +
                        "where "+ Income_getWhere() +
                        ") as f,\n" +
                        "location as l\n" +
                        "where d.person_id = p.person_id\n" +
                        "and d.house_id= f.house_id\n"+
                        "and d.house_id = l.house_id\n" +
                        sliceEduGender()+
                        "group by "+ getGroupby() +" ,age_group, income_group,educal with rollup";
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
    
    
    

