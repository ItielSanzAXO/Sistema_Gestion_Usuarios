/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Datos.DALUsuario;
import Datos.Usuario;
import Profesor.Profe;
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
        System.out.println("SIN FOTO");
    }
    
    public void modificarDatosConFoto(Usuario u){
        dal.modificarDatosConFoto(u);
        System.out.println("CON FOTO");
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

    public void insertarDatosProfe(Profe p) {
        dal.insertarDatosProfe(p);
    }
    public void modificarDatosProfe(Profe p) {
        dal.modificarDatosProfe(p);
    }

    public void buscarListaProfe(DefaultTableModel modelo_tabla, JTable tblDatos, String dato) {
        dal.buscarListaProfe(modelo_tabla, tblDatos, dato);
    }
}
