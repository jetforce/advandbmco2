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
public class place_total_income_query implements Query {
    
    private Income_Location box;
    
    public place_total_income_query(Income_Location box){
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
        Income incomes = this.box.getIncome();
        Range[] rlist = incomes.getRanges();
        String init="";
        
        for(int i=0; i< rlist.length; i++){            
            init = init + "WHEN "+ incomes.getMoneyType() +" BETWEEN "+ rlist[i].small +" and "+ rlist[i].big +" THEN '"+rlist[i].toString()+"'\n";
        }
        
        return init;
    }
    
    private String getWhere(){
        Income incomes = this.box.getIncome();
        Range[] rlist = incomes.getRanges();
        String init= incomes.getMoneyType()+ " >= "+ rlist[0].small +" and "+incomes.getMoneyType() + "<= "+ rlist[0].big +"\n";
        
        for(int i=1; i< rlist.length; i++){            
            init = init + "or "+incomes.getMoneyType()+ " >= "+ rlist[i].small +" and "+incomes.getMoneyType() + "<= "+ rlist[i].big +"\n";
        }
        
        return init;
        
    }
  
    private String getLastWhere(){
         Location loc =this.box.getLoc();
        ArrayList<String> s_list = loc.getLocations(); 
        String init ="place_id ";
                
        for(int i=1; i< s_list.size(); i++){
            init = init +" ,"+ s_list.get(i);
        }
        
        return init;      
        
    }
    
    private String makeQuery(){
        Income incomes = this.box.getIncome();
        String q = "select "+ getSelect()+ " SUM("+ incomes.getMoneyType() +") as 'Total income'\n" +
                    "from location as l,\n" +
                    "family as f\n"+
                    "where l.house_id = f.house_id\n" +
                    "group by "+ getLastWhere() +" with rollup";
        
      return q;  
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
