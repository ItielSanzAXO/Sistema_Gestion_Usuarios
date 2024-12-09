/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import Control.BLLUsuario;
import Control.ConvertirMayusculas;
import Control.Validar;
import Datos.Usuario;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ItielSanz
 */
public class IU_GESTIONUSUARIO extends javax.swing.JFrame {

    /**
     * Creates new form IU_GESTIONUSUARIO
     */
   Validar v=new Validar();
   DefaultTableModel modelo_tabla;
   BLLUsuario bll=new BLLUsuario();
   FileInputStream fis;
   int longitudBytes, apretafoto=0, id=0;
   boolean consultar=false;
   Usuario u=new Usuario();
    
    public IU_GESTIONUSUARIO() {
        initComponents();
        metodosDeInicio();
    modelo_tabla = new DefaultTableModel(){
    //Para que las filas no se editen
    @Override
    public boolean isCellEditable(int Fila, int columna){
    return false;
    } 
    };
    
    tblDatos.setModel(modelo_tabla);
    modelo_tabla.addColumn("id");
    modelo_tabla.addColumn("Matricula");
    modelo_tabla.addColumn("Nombre");
    modelo_tabla.addColumn("Apellidos");
    modelo_tabla.addColumn("Correo");
    bll.mostrarLista(modelo_tabla, tblDatos);
    //Comumnas Fijas
    tblDatos.getTableHeader().setReorderingAllowed(false);
    
    tblDatos.getColumnModel().getColumn(0).setMaxWidth(0);
    tblDatos.getColumnModel().getColumn(0).setMinWidth(0);
    tblDatos.getColumnModel().getColumn(0).setPreferredWidth(0);
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
    txtbuscar.setDocument(new ConvertirMayusculas());
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
    
    u.setIdusuario(id);
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
        
        limpiarTodo();
    }
    
}

public void actualizarTabla(){
    //Limpiar
    while(modelo_tabla.getRowCount() >0){
        modelo_tabla.removeRow(0);
    }
    //Cargar
    bll.mostrarLista(modelo_tabla, tblDatos);
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
    btneliminar1.setEnabled(false);
    consultar=false;
    apretafoto=0;
    actualizarTabla();
    }

    public void buscar(){
        String dato=txtbuscar.getText();
        if(dato.isEmpty()){
            actualizarTabla();
        }else if(!dato.isEmpty()){ 
            while(modelo_tabla.getRowCount() >0){
            modelo_tabla.removeRow(0);
        }
            bll.buscarLista(modelo_tabla, tblDatos, dato);
        
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        txtbuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
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
        btnnuevo = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneliminar1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtNombreProfe = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtApellidosProfe = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCorreoProfe = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTelefonoProfe = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtUsuarioProfe = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtClaveProfe = new javax.swing.JPasswordField();
        btnNuevoProfe = new javax.swing.JButton();
        btnEliminarProfe = new javax.swing.JButton();
        btnGuardarProfe = new javax.swing.JButton();
        btnsalir1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pane.setBackground(new java.awt.Color(255, 204, 204));
        pane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDatos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 708, 350));

        txtbuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtbuscarCaretUpdate(evt);
            }
        });
        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        jPanel1.add(txtbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 600, 38));

        jLabel1.setText("Buscar");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 34, 163, -1));

        pane.addTab("Lista de Usuarios", jPanel1);

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

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_plus-sign_173078.png"))); // NOI18N
        btnnuevo.setText("Nuevo");
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jPanel2.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 300, 150, 40));

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

        btneliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_Bin_2202256.png"))); // NOI18N
        btneliminar1.setText("Eliminar");
        btneliminar1.setEnabled(false);
        btneliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btneliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 360, 110, 50));

        pane.addTab("Alumnos", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("Nombre");

        txtNombreProfe.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNombreProfeCaretUpdate(evt);
            }
        });

        jLabel12.setText("Apellidos");

        txtApellidosProfe.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtApellidosProfeCaretUpdate(evt);
            }
        });

        jLabel13.setText("Correo");

        txtCorreoProfe.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCorreoProfeCaretUpdate(evt);
            }
        });

        jLabel14.setText("Telefono");

        txtTelefonoProfe.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTelefonoProfeCaretUpdate(evt);
            }
        });

        jLabel15.setText("Usuario");

        txtUsuarioProfe.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtUsuarioProfeCaretUpdate(evt);
            }
        });

        jLabel16.setText("Contraseña");

        txtClaveProfe.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtClaveProfeCaretUpdate(evt);
            }
        });
        txtClaveProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaveProfeActionPerformed(evt);
            }
        });

        btnNuevoProfe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_plus-sign_173078.png"))); // NOI18N
        btnNuevoProfe.setText("Nuevo");
        btnNuevoProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProfeActionPerformed(evt);
            }
        });

        btnEliminarProfe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_Bin_2202256.png"))); // NOI18N
        btnEliminarProfe.setText("Eliminar");
        btnEliminarProfe.setEnabled(false);
        btnEliminarProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProfeActionPerformed(evt);
            }
        });

        btnGuardarProfe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_save_173091.png"))); // NOI18N
        btnGuardarProfe.setText("Guardar");
        btnGuardarProfe.setEnabled(false);
        btnGuardarProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProfeActionPerformed(evt);
            }
        });

        btnsalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_login_173049.png"))); // NOI18N
        btnsalir1.setText("Salir");
        btnsalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(176, 176, 176)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(txtNombreProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(176, 176, 176)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(txtApellidosProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(176, 176, 176)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(txtCorreoProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(176, 176, 176)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(txtTelefonoProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(176, 176, 176)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(txtUsuarioProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(176, 176, 176)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(txtClaveProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnNuevoProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(49, 49, 49)
                            .addComponent(btnEliminarProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(107, 107, 107)
                            .addComponent(btnGuardarProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(170, 170, 170)
                            .addComponent(btnsalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombreProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtApellidosProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCorreoProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTelefonoProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUsuarioProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtClaveProfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNuevoProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnEliminarProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGuardarProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnsalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pane.addTab("Profesores", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
    validarIngreso();   // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void txtclaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclaveActionPerformed
   // TODO add your handling code here:
    }//GEN-LAST:event_txtclaveActionPerformed

    private void btnfotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfotoActionPerformed
    cargarFoto();        // TODO add your handling code here:
    }//GEN-LAST:event_btnfotoActionPerformed

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

    private void jdateFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdateFechaPropertyChange
    validarIngreso();         // TODO add your handling code here:
    }//GEN-LAST:event_jdateFechaPropertyChange

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
    botonGuardar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnguardarActionPerformed

    private void txtbuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtbuscarCaretUpdate
    buscar();     // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarCaretUpdate

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        if(evt.getClickCount() ==2){
        consultar=true;
        int fila=tblDatos.getSelectedRow();
        id=(int) tblDatos.getValueAt(fila, 0);
        Object [] datos=bll.consultarporID(id, lblFoto);
        txtmatricula.setText(datos[0].toString());
        txtnombre.setText(datos[1].toString());
        txtapellidos.setText(datos[2].toString());
        txtcorreo.setText(datos[3].toString());
        txttelefono.setText(datos[4].toString());
        txtUsuario.setText(datos[5].toString());
        txtclave.setText(datos[6].toString());
        jdateFecha.setDate((Date)datos[7]);
        try{
            lblFoto.setIcon((Icon)datos[8]);
        }catch (Exception e){ 
        }
        
        pane.setSelectedIndex(1);
        btneliminar1.setEnabled(true);
    }   // TODO add your handling code here:
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
    limpiarTodo();        // TODO add your handling code here:
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btneliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar1ActionPerformed
        
        int respuesta=JOptionPane.showConfirmDialog(null, "¿Realmente Quiere Eliminar Este Usuario?");
            if(respuesta==0){
                u.setIdusuario(id);
                bll.eliminarUsuario(u);
                limpiarTodo();
            }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btneliminar1ActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        LOGIN login = new LOGIN();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsalirActionPerformed

    private void txtNombreProfeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNombreProfeCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProfeCaretUpdate

    private void txtApellidosProfeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtApellidosProfeCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidosProfeCaretUpdate

    private void txtCorreoProfeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCorreoProfeCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoProfeCaretUpdate

    private void txtTelefonoProfeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTelefonoProfeCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProfeCaretUpdate

    private void txtUsuarioProfeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtUsuarioProfeCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioProfeCaretUpdate

    private void txtClaveProfeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtClaveProfeCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClaveProfeCaretUpdate

    private void txtClaveProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaveProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClaveProfeActionPerformed

    private void btnNuevoProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoProfeActionPerformed

    private void btnEliminarProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarProfeActionPerformed

    private void btnGuardarProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarProfeActionPerformed

    private void btnsalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsalir1ActionPerformed

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
            java.util.logging.Logger.getLogger(IU_GESTIONUSUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IU_GESTIONUSUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IU_GESTIONUSUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IU_GESTIONUSUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IU_GESTIONUSUARIO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarProfe;
    private javax.swing.JButton btnGuardarProfe;
    private javax.swing.JButton btnNuevoProfe;
    private javax.swing.JButton btneliminar1;
    private javax.swing.JButton btnfoto;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnsalir;
    private javax.swing.JButton btnsalir1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdateFecha;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTabbedPane pane;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtApellidosProfe;
    private javax.swing.JPasswordField txtClaveProfe;
    private javax.swing.JTextField txtCorreoProfe;
    private javax.swing.JTextField txtNombreProfe;
    private javax.swing.JTextField txtTelefonoProfe;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtUsuarioProfe;
    private javax.swing.JTextField txtapellidos;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JPasswordField txtclave;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtmatricula;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
