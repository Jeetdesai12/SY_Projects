/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author JeetD
 */
public class DbConnect {
    public static Connection c;
    public static Statement st;
    
    static{
        try{
        c =DriverManager.getConnection("jdbc:mysql://localhost:3306/spendingdb"+"?useSSL=false","jeetdesai12","jnd111222");
        st=c.createStatement();
        
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
}

