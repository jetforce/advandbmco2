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
public class Income_Location extends javax.swing.JPanel {

    /**
     * Creates new form Income_Location
     */
    
    private Location location;
    private Income income;
    public Income_Location() {
        initComponents();
        location = new Location();
        income = new Income();
        add(location);
        add(income);
    }
    
    public Location getLoc(){
        return location;
    }
    
    public Income getIncome(){
        return income;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}