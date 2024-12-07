/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import Datos.Conexion;

/**
 *
 * @author ItielSanz
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static Conexion hc;
    
    public static void main(String[] args) {
        
        LOGIN vista=new LOGIN();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        try{
            hc=new Conexion();
            System.out.println("Conectado");
            IU_GESTIONUSUARIO g=new IU_GESTIONUSUARIO();
            g.setLocationRelativeTo(null);
            g.setVisible(false);
            
        }catch (Exception e){
            System.out.println("error al iniciar: "+e);
        }
        
    }
}
        // TODO code application
