/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import crud.Main;

/**
 *
 * @author ItielSanz
 */
public class DALUsuario {

    Conexion con = Main.hc;

    CargarDatos c = new CargarDatos();

    public void mostrarLista(DefaultTableModel model, JTable tabla) {
        try {
            String sql = "select idusuario, matricula,nombre,apellidos,correo from usuario "
                    + "where estado=1 order by nombre asc ";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error al caragar tabla dal: "+e); 
        }
    }

    public void buscarLista(DefaultTableModel model, JTable tabla, String dato) {
        try {
            String sql = "select idusuario, matricula,nombre,apellidos,correo from usuario\n"
                    + "where estado=1 and (nombre like '%" + dato + "%' or apellidos like '%" + dato + "%' ) order by nombre asc";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error al caragar tabla dal: "+e); 
        }
    }

    public void insertarDatos(Usuario u) {

        try {
            String sql = "INSERT INTO usuario(matricula, nombre, apellidos, correo, "
                    + "telefono, usuario, clave, fecha, foto)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, '" + u.getFecha() + "', ?);";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getMatricula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getUsuario());
            ps.setString(7, u.getClave());
            ps.setBinaryStream(8, u.getFis(), u.getLongitudBytes());
            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Ingresado ");
            } else if (ejecucion == false) {
                JOptionPane.showMessageDialog(null, "Error Al Igresar Usuario");
            }

        } catch (Exception e) {
            System.out.println("Error al insertar: " + e);
        }

    }

    public void modificarDatosSinFoto(Usuario u) {
        System.out.println("UPDATE SIN FOTO");
        try {
            String sql = "UPDATE usuario SET matricula=?, nombre=?, apellidos=?, correo=?, telefono=?, \n"
                    + "usuario=?, clave=?, fecha='" + u.getFecha() + "'\n"
                    + " WHERE idusuario=?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getMatricula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getUsuario());
            ps.setString(7, u.getClave());
            //ps.setInt(8, u.getIdusuario();
            ps.setInt(8, u.getIdusuario());
            boolean ejecucion = con.ejecutarSQL(ps);
            System.out.println(""+ejecucion);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Actualizado ");
            } else if (ejecucion == false) {
                JOptionPane.showMessageDialog(null, "Error Al Modificar Usuario");
            }

        } catch (Exception e) {

        }

    }

    public void modificarDatosConFoto(Usuario u) {
        System.out.println("UPDATE CON FOTO");
        try {
            String sql = "UPDATE usuario SET matricula=?, nombre=?, apellidos=?, correo=?, telefono=?, \n"
                    + "usuario=?, clave=?, foto=?, fecha='" + u.getFecha() + "'\n"
                    + " WHERE idusuario=?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getMatricula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getUsuario());
            ps.setString(7, u.getClave());
            ps.setBinaryStream(8, u.getFis(), u.getLongitudBytes());
            ps.setInt(9, u.getIdusuario());
            boolean ejecucion = con.ejecutarSQL(ps);
            System.out.println("" + ejecucion );
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Actualizado ");
            } else if (ejecucion == false) {
                JOptionPane.showMessageDialog(null, "Error Al Modificar Usuario");
            }

        } catch (Exception e) {

        }

    }

    public Object[] consultarporID(int id, JLabel lblFoto) {
        Object[] datos = new Object[9];
        ImageIcon foto;
        InputStream is;

        try {
            String sql = "SELECT matricula, nombre, apellidos, correo, telefono, usuario, \n"
                    + "  clave, fecha, foto\n"
                    + "  FROM usuario WHERE idusuario=?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getDate(8);
                is = rs.getBinaryStream(9);
                BufferedImage bi = ImageIO.read(is);
                foto = new ImageIcon(bi);
                Image img = foto.getImage();
                Image newimg = img.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), 
                        java.awt.Image.SCALE_SMOOTH);
                ImageIcon newicon = new ImageIcon(newimg);
                datos[8] = newicon;

            }
        } catch (Exception e) {
            System.out.println("Error al consular: " + e);
        }
        return datos;
    }

    public void eliminarUsuario(Usuario u) {

        try {
            String sql = "DELETE FROM public.usuario WHERE idusuario=?";
            //String sql = "UPDATE usuario SET estado=0 WHERE idusuario=?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, u.getIdusuario());
            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Eliminado ");
            } else if (ejecucion == false) {
                JOptionPane.showMessageDialog(null, "Error Al Eliminar Usuario");
            }

        } catch (Exception e) {

        }

    }
    
    public Object[] validarLogin(String usuario, String pass) {
    // Consultas SQL ajustadas a las tablas Administrador, Alumno y Profesor
    String sqlAdmin = "SELECT idAdministrador FROM Administrador WHERE usuario = ? AND pass = ?";
    String sqlAlumno = "SELECT idAlumno FROM Alumno WHERE usuario = ? AND clave = ? AND estado = true";
    String sqlProfesor = "SELECT idProfesor FROM Profesor WHERE correo = ? AND telefono = ?";

    // Array para almacenar el rol (1 = Admin, 2 = Alumno, 3 = Profesor) y el ID correspondiente
    Object[] resultado = new Object[2];

    try (PreparedStatement stmtAdmin = con.getCont().prepareStatement(sqlAdmin);
         PreparedStatement stmtAlumno = con.getCont().prepareStatement(sqlAlumno);
         PreparedStatement stmtProfesor = con.getCont().prepareStatement(sqlProfesor)) {

        // Validar si es administrador
        stmtAdmin.setString(1, usuario);
        stmtAdmin.setString(2, pass);
        try (ResultSet rsAdmin = stmtAdmin.executeQuery()) {
            if (rsAdmin.next()) {
                resultado[0] = 1; // Rol de administrador
                resultado[1] = rsAdmin.getInt("idAdministrador");
                return resultado;
            }
        }

        // Validar si es alumno
        stmtAlumno.setString(1, usuario);
        stmtAlumno.setString(2, pass);
        try (ResultSet rsAlumno = stmtAlumno.executeQuery()) {
            if (rsAlumno.next()) {
                resultado[0] = 2; // Rol de alumno
                resultado[1] = rsAlumno.getInt("idAlumno");
                return resultado;
            }
        }

        // Validar si es profesor
        stmtProfesor.setString(1, usuario);
        stmtProfesor.setString(2, pass);
        try (ResultSet rsProfesor = stmtProfesor.executeQuery()) {
            if (rsProfesor.next()) {
                resultado[0] = 3; // Rol de profesor
                resultado[1] = rsProfesor.getInt("idProfesor");
                return resultado;
            }
        }

    } catch (Exception e) {
        System.out.println("Error al validar login: " + e);
    }

    // Si no se encuentra el usuario en ninguna tabla
    resultado[0] = 0; // Usuario o contraseña incorrectos
    resultado[1] = null;
    return resultado;
    }

}
