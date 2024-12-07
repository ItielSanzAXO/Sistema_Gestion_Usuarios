/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Alumno;

import Control.BLLUsuario;
import Control.ConvertirMayusculas;
import Control.Validar;
import Datos.*;
import crud.LOGIN;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ItielSanz
 */
public class Alumno extends javax.swing.JFrame {

    /**
     * Creates new form Alumno
     */
   Validar v=new Validar();
   BLLUsuario bll=new BLLUsuario();
   FileInputStream fis;
   int longitudBytes, apretafoto=0, id=0;
   boolean consultar=false;
   Usuario u=new Usuario();
   public int idAlumno;
   
    public Alumno() {
        initComponents();
        metodosDeInicio();
    }

    public void setIdAlumno(int id){
        this.idAlumno = id;
    }

     public void cargarDatosAlumno() {
        // Aquí, consulta los datos del alumno con el idAlumno y asigna los valores a los componentes
        //DALUsuario dal = new DALUsuario();
        Object[] datos = bll.consultarporID(idAlumno, lblFoto);

        //if (datos != null) {
            consultar = true;
            // Asignamos los datos al JFrame de Alumno
            txtmatricula.setText(datos[0].toString());
            txtnombre.setText(datos[1].toString());
            txtapellidos.setText(datos[2].toString());
            txtcorreo.setText(datos[3].toString());
            txttelefono.setText(datos[4].toString());
            txtUsuario.setText(datos[5].toString());
            txtclave.setText(datos[6].toString());
            jdateFecha.setDate((Date)datos[7]);
            // Establecer la foto en lblFoto
            try{
            lblFoto.setIcon((ImageIcon) datos[8]);
            }catch(Exception e){
            }
        //} else {
        //    JOptionPane.showMessageDialog(this, "No se encontraron datos para el alumno.");
        //}
    }
     
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            cargarDatosAlumno();
        }
        super.setVisible(visible);
    }
    
public final void metodosDeInicio(){
        
    v.validarSoloLetreas(txtnombre);
    v.validarSoloLetreas(txtapellidos);
    v.validarSoloNumeros(txtmatricula);
    v.validarSoloNumeros(txttelefono);
    v.limitarCaracteres(txtmatricula, 9);
    v.limitarCaracteres(txtnombre,  100);
    v.limitarCaracteres(txtapellidos, 100);
    v.limitarCaracteres(txtcorreo, 100);
    v.limitarCaracteres(txttelefono, 20);
    v.limitarCaracteres(txtUsuario, 20);
    v.limitarCaracteres(txtclave, 50);
    txtnombre.setDocument(new ConvertirMayusculas());
    txtapellidos.setDocument(new ConvertirMayusculas());
    //txtbuscar.setDocument(new ConvertirMayusculas());
    txtUsuario.setDocument(new ConvertirMayusculas());
    }
    
public boolean validarFormatoCorreo(String correo){
    
    Pattern pat = null ;
    Matcher mat = null ;
    pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
    mat = pat.matcher(correo);
    
        if (mat.find()){
            return true;
        
        }else{
            return false;
        }
    }

public void validarIngreso(){
        String mat=txtmatricula.getText().trim();
        int tam = mat.length();
        String nom=txtnombre.getText().trim();
        String ape=txtapellidos.getText().trim();
        boolean estado=validarFormatoCorreo(txtcorreo.getText());
        String tel=txttelefono.getText().trim();
        String usu=txtUsuario.getText().trim();
        String contra=txtclave.getText();
        Date fech=jdateFecha.getDate();
        
        if(tam !=9||nom.isEmpty()|ape.isEmpty()||estado==false||tel.isEmpty()
                ||usu.isEmpty()||contra.isEmpty()||fech==null){
        btnguardar.setEnabled(false);
        }else{
            btnguardar.setEnabled(true);
        }
        
    } 

public void cargarFoto(){
    
    JFileChooser j=new JFileChooser();
    FileNameExtensionFilter filtro= new FileNameExtensionFilter("JPG & PNG","jpg","png");
    j.setFileFilter(filtro);
    
    int estado= j.showOpenDialog(null);
    if(estado == JFileChooser.APPROVE_OPTION){
        
        try{
            fis= new FileInputStream(j.getSelectedFile());
            this.longitudBytes= (int) j.getSelectedFile().length();
            try{
                lblFoto.setIcon(null);
                Image icono= ImageIO.read(j.getSelectedFile()).getScaledInstance(lblFoto.getWidth()
                        ,lblFoto.getHeight(),Image.SCALE_DEFAULT);
            lblFoto.setIcon(new ImageIcon(icono));
            lblFoto.updateUI();
            apretafoto=1;
               System.out.println("LongitudBytes: "+longitudBytes);
            
            }catch (IOException e){
               System.out.println("error al cargar foro IO: "+e);
            }
            
        }catch (FileNotFoundException e){
          System.out.println("error al cargar file: "+e);
        }
    }
}

public void botonGuardar(){
    String matri=txtmatricula.getText().trim();
    String nom=txtnombre.getText().trim();
    String ap=txtapellidos.getText().trim();
    String correo=txtcorreo.getText().trim();
    String tel=txttelefono.getText().trim();
    String usu=txtUsuario.getText().trim();
    String clave=txtclave.getText();
    Date fech=jdateFecha.getDate();
    
    u.setIdusuario(idAlumno);
    u.setMatricula(matri);
    u.setNombre(nom);
    u.setApellidos(ap);
    u.setCorreo(correo);
    u.setTelefono(tel);
    u.setUsuario(usu);
    u.setClave(clave);
    u.setFecha(fech);
    u.setFis(fis);
    u.setLongitudBytes(longitudBytes);
    System.out.println("");
    
    if(consultar==false){
    //Insertar
        bll.insertarDatos(u);
        limpiarTodo();
     
    }else if(consultar==true){
    //Modificar
        if(apretafoto == 0) {
            bll.modificarDatosSinFoto(u);
        }else if(apretafoto == 1) {
            bll.modificarDatosConFoto(u);
        }
        JOptionPane.showMessageDialog(this, "Ahora salte");
        //limpiarTodo();
    }
    
}

public void limpiarTodo(){
    txtmatricula.setText(null);
    txtnombre.setText(null);
    txtapellidos.setText(null);
    txtcorreo.setText(null);
    txttelefono.setText(null);
    txtUsuario.setText(null);
    txtclave.setText(null);
    jdateFecha.setDate(null);
    lblFoto.setIcon(null);
    btnguardar.setEnabled(false);
    //btneliminar1.setEnabled(false);
    consultar=false;
    apretafoto=0;
    //actualizarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtmatricula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtapellidos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtcorreo = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtclave = new javax.swing.JPasswordField();
        jdateFecha = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        btnfoto = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jPanel2.add(lblFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 25, 159, 202));

        jLabel3.setText("Matricula");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 100, 30));

        txtmatricula.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtmatriculaCaretUpdate(evt);
            }
        });
        jPanel2.add(txtmatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 390, 30));

        jLabel4.setText("Nombre");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 100, 30));

        txtnombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtnombreCaretUpdate(evt);
            }
        });
        jPanel2.add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 390, 30));

        jLabel5.setText("Apellidos");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 100, 30));

        txtapellidos.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtapellidosCaretUpdate(evt);
            }
        });
        jPanel2.add(txtapellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 390, 30));

        jLabel6.setText("Correo");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 100, 30));

        txtcorreo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtcorreoCaretUpdate(evt);
            }
        });
        jPanel2.add(txtcorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 390, 30));

        txttelefono.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txttelefonoCaretUpdate(evt);
            }
        });
        jPanel2.add(txttelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 390, 30));

        jLabel7.setText("Telefono");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 100, 30));

        txtUsuario.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtUsuarioCaretUpdate(evt);
            }
        });
        jPanel2.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 390, 30));

        jLabel8.setText("Fecha de Nacimiento");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, 240, 30));

        jLabel9.setText("Usuario");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 100, 30));

        txtclave.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtclaveCaretUpdate(evt);
            }
        });
        txtclave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclaveActionPerformed(evt);
            }
        });
        jPanel2.add(txtclave, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, 390, -1));

        jdateFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdateFechaPropertyChange(evt);
            }
        });
        jPanel2.add(jdateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 310, 290, -1));

        jLabel10.setText("Contraseña");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 100, 30));

        btnfoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_Account_add_2202248.png"))); // NOI18N
        btnfoto.setText("Subir imagen");
        btnfoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfotoActionPerformed(evt);
            }
        });
        jPanel2.add(btnfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 240, 160, 40));

        btnsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_login_173049.png"))); // NOI18N
        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 360, 110, 50));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_save_173091.png"))); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.setEnabled(false);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, 110, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtmatriculaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtmatriculaCaretUpdate
        validarIngreso();       // TODO add your handling code here:
    }//GEN-LAST:event_txtmatriculaCaretUpdate

    private void txtnombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtnombreCaretUpdate
        validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreCaretUpdate

    private void txtapellidosCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapellidosCaretUpdate
        validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_txtapellidosCaretUpdate

    private void txtcorreoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtcorreoCaretUpdate
        validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_txtcorreoCaretUpdate

    private void txttelefonoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txttelefonoCaretUpdate
        validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_txttelefonoCaretUpdate

    private void txtUsuarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtUsuarioCaretUpdate
        validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioCaretUpdate

    private void txtclaveCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtclaveCaretUpdate
        validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_txtclaveCaretUpdate

    private void txtclaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclaveActionPerformed

    private void jdateFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdateFechaPropertyChange
        validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_jdateFechaPropertyChange

    private void btnfotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfotoActionPerformed
        cargarFoto();        // TODO add your handling code here:
    }//GEN-LAST:event_btnfotoActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        LOGIN login = new LOGIN();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        botonGuardar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnguardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Alumno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnfoto;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser jdateFecha;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtapellidos;
    private javax.swing.JPasswordField txtclave;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtmatricula;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
