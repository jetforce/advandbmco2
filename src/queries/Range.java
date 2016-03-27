/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

/**
 *
 * @author Jett
 */
public class Range {
    
    public int small;
    public int big;
    public String type;
    
    public Range(int small,int big,String type){
        this.small= small;
        this.big=big;
        this.type = type;
    }
    
    @Override
    public String toString(){
        return small+"<="+ this.type +"<= "+ big;
    }
    
    
  
}
