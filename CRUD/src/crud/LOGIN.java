/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

//import com.sun.awt.AWTUtilities;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import Datos.*;
import Alumno.*;
import Control.*;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author ItielSanz
 */
public class LOGIN extends javax.swing.JFrame {

    public static Conexion hc;
   BLLUsuario bll=new BLLUsuario();

    private int intentos;

    /**
     * Creates new form LOGIN
     */
    public LOGIN() {
        initComponents();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsu = new javax.swing.JTextField();
        txtClave = new javax.swing.JPasswordField();
        btnSalir = new javax.swing.JButton();
        btnIngresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 0, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Usuario");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 54, 58, 28));

        jLabel2.setText("Clave");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 100, 58, 26));
        jPanel1.add(txtUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 54, 196, 28));

        txtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClaveKeyPressed(evt);
            }
        });
        jPanel1.add(txtClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 100, 196, -1));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 187, -1, -1));

        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
    System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtClaveKeyPressed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
    String usuario = txtUsu.getText();
    String pass = new String(txtClave.getPassword());

    DALUsuario dalUsuario = new DALUsuario();
    Object[] resultado = dalUsuario.validarLogin(usuario, pass);

    int rol = (int) resultado[0];
    Integer id = (Integer) resultado[1];

    if (rol == 1) {
        JOptionPane.showMessageDialog(this, "¡Bienvenido, Administrador!");

        // Redirigir al JFrame del Administrador (IU_GESTIONUSUARIO)
        IU_GESTIONUSUARIO gestionUsuario = new IU_GESTIONUSUARIO();
        gestionUsuario.setVisible(true);
        this.dispose();
    } else if (rol == 2 && id != null) {
        // Redirigir al JFrame del Alumno (Alumno)
        Alumno alumno = new Alumno();

        // Asignamos solo el ID del Alumno, para despues cargarlo
        alumno.setIdAlumno(id);
        System.out.println("El id del alumno es: " + id);
        alumno.setVisible(true);
        this.dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
    }
    }//GEN-LAST:event_btnIngresarActionPerformed
    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
    //     IU_GESTIONUSUARIO i=new IU_GESTIONUSUARIO();
    //    AWTUtilities.setWindowOpaque(i, false);
    //   i.setVisible(true);
    //}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtUsu;
    // End of variables declaration//GEN-END:variables
}
