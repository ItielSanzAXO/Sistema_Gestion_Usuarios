/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Datos.DALUsuario;
import Datos.Usuario;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ItielSanz
 */
public class BLLUsuario {
    
    DALUsuario dal=new DALUsuario();
    public void  mostrarLista(DefaultTableModel model,JTable tabla){
        dal.mostrarLista(model, tabla);
    }
    
    public void insertarDatos(Usuario u){
        dal.insertarDatos(u);
    }
    
    public void modificarDatosSinFoto(Usuario u){
        dal.modificarDatosSinFoto(u);
    }
    
    public void modificarDatosConFoto(Usuario u){
        dal.modificarDatosConFoto(u);
    }
    
    public void buscarLista(DefaultTableModel model, JTable tabla, String dato) {
        dal.buscarLista(model, tabla, dato);
    }
    
    //public Object[] consultaporID(int id){
    public Object[] consultarporID(int id, JLabel lblFoto){
        return dal.consultarporID (id, lblFoto);
        //return dal.consultarporID(id );
    }
    
    public void eliminarUsuario(Usuario u){
        dal.eliminarUsuario(u);
    }
}
