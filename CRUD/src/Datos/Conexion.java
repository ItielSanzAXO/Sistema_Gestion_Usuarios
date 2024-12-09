/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ItielSanz
 */
public class Conexion implements Serializable{
    
    public Connection con =null;
    
    public Conexion(){
        con=Conexion.realizaConexion();
        
    }
    
    public Connection getCont(){
        return con;
    }
    
    public static Connection realizaConexion() {
    
    Connection c=null;
    
        try{
            
            Class.forName("org.postgresql.Driver");
            //c=DriverManager.getConnection("jdbc:postgresql://192.168.2.6:5432/sistemas",
            //        "postgres","password");
            c=DriverManager.getConnection("jdbc:postgresql://192.168.2.6:5432/itiz",
                    "postgres","password");
            System.out.println("Conectado a POSTGRESQL");
        }catch (SQLException e) {
            System.out.println("Error: "+e);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error 2: "+ex);
        }
        
        return c;
    }
    
    public boolean ejecutarSQL(PreparedStatement sentencia){
    
            try{
                sentencia.execute();
                sentencia.close();
                return true;
            }catch (SQLException e){
                System.out.println("Error al ejecutar: "+e);
                return false;
            }
    }
    
    public ResultSet ejecutarSQLSelect(String sql){
        
        ResultSet resultado;
        try{
            PreparedStatement sentencia=con.prepareStatement(sql);
            resultado=sentencia.executeQuery();
            return resultado;
        }catch (SQLException e){
            System.out.println("Error al ejecutar consulta: "+e);
            return null;
        }
        
    }
    
}
