package atenda.vista;

import atenda.controlador.Conexion;
import atenda.controlador.ModeloDAO;
import atenda.modelo.Rol;
import atenda.modelo.Usuario;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elias
 */
public class UsuariosAdminPanel extends javax.swing.JPanel {

    /**
     * Creates new form UsuariosAdminPanel
     */
    private ModeloDAO modeloDAO;
    private boolean nuevoUsuario;
    private String uploadedImagePath;

    public UsuariosAdminPanel() {
        mostrarFotoEtiqueta = new javax.swing.JLabel();
         mostrarFotoEtiqueta.revalidate();
        mostrarFotoEtiqueta.repaint();
        initComponents();
        String[] roles = {"ADMINISTRADOR", "DEPENDENTE"};
        // Establecer el arreglo como modelo del combo box
        comboRol.setModel(new DefaultComboBoxModel<>(roles));
        modeloDAO = new ModeloDAO();
        mostrarUsuarios();
        habilitarCampos(false);

    }

    private void mostrarUsuarios() {
        // Obtener los usuarios de la base de datos
        List<String> usuarios = obtenerUsuarios();

        // Limpiar la tabla
        DefaultTableModel model = (DefaultTableModel) tablaUsuariosAdmin.getModel();
        model.setRowCount(0);

        // Agregar los usuarios a la tabla
        for (String usuario : usuarios) {
            model.addRow(new Object[]{usuario});
        }
    }

    private List<String> obtenerUsuarios() {
        List<String> usuarios = new ArrayList<>();

        // Obtener los usuarios de la base de datos
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT nome FROM usuario");
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nome");
                usuarios.add(nombre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return usuarios;
    }

    private void habilitarCampos(boolean habilitado) {
        textFieldUsername.setEnabled(habilitado);
        textFieldContraseña.setEnabled(habilitado);
        textFieldRContraseña.setEnabled(habilitado);
        textFieldNome.setEnabled(habilitado);
        comboRol.setEnabled(habilitado);
    }

    private void llenarCampos(Usuario usuario) {
        if (usuario != null) {
        habilitarCampos(true);
        textFieldUsername.setText(usuario.getUsername());
        textFieldContraseña.setText(usuario.getPassword());
        textFieldNome.setText(usuario.getNome());
        String idUsuarioString = String.valueOf(usuario.getIdUsuario());
        String imagePath = usuario.getAvatar();
        idEtiqueta.setText(idUsuarioString);

        Rol rol = usuario.getRol();

        // Obtener el nombre del rol como un String
        String rolString = rol != null ? rol.name() : "";

        // Establecer el rol seleccionado en el combo box
        comboRol.setSelectedItem(rolString);

        if (imagePath != null) {
            // Load and display the user's image
            //InputStream imageStream = getClass().getResourceAsStream(imagePath);
            String projectPath = System.getProperty("user.dir");
            //String fullImagePath = projectPath + "/src/atenda" + imagePath; LINEA PARA MAC
            String fullImagePath = projectPath + "\\src\\atenda" + imagePath;
            
            File imageFile = new File(fullImagePath);
            
            if (imageFile.exists()) {
                try {
                    BufferedImage image = ImageIO.read(imageFile);
                    int labelWidth = mostrarFotoEtiqueta.getWidth();
                    int labelHeight = mostrarFotoEtiqueta.getHeight();
                    Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    mostrarFotoEtiqueta.setIcon(scaledIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mostrarFotoEtiqueta.setIcon(null);
            }
        } else {
            mostrarFotoEtiqueta.setIcon(null);
        }
         mostrarFotoEtiqueta.revalidate();
        mostrarFotoEtiqueta.repaint();
       
    } else {
        habilitarCampos(false);
        textFieldUsername.setText("");
        textFieldContraseña.setText("");
        textFieldNome.setText("");
        mostrarFotoEtiqueta.setIcon(null); // Clear the label if no user is selected
    }

        
    }

    private void actualizarUsuario() throws FileNotFoundException {
        int selectedRow = tablaUsuariosAdmin.getSelectedRow();
    if (selectedRow != -1) {
        String selectedUser = (String) tablaUsuariosAdmin.getValueAt(selectedRow, 0);
        Usuario usuario = modeloDAO.obtenerUsuario(selectedUser);

        if (usuario != null) {
            // Obtain the user's data from the text fields
            String username = textFieldUsername.getText();
            String password = new String(textFieldContraseña.getPassword());
            String confirmPassword = new String(textFieldRContraseña.getPassword());
            String nome = textFieldNome.getText();
            String rolString = (String) comboRol.getSelectedItem();
            Rol rol = Rol.valueOf(rolString.toUpperCase());

            // Check if a new image was uploaded
            if (uploadedImagePath != null) {
                // Delete the existing image file
                deleteImage(usuario.getAvatar());

                // Update the user's avatar field with the new image path
                usuario.setAvatar(uploadedImagePath);
            }

            // Verify that the passwords match
            if (!password.equals(confirmPassword)) {
                etiquetaMensaje.setText("Contraseña no coincide");
                return;
            }

            // Update the user's fields
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setNome(nome);
            usuario.setRol(rol);
            

            // Update the user in the database
            modeloDAO.update(usuario);

            // Update the users table
            mostrarUsuarios();

            // Fill the fields with the updated user's data
            llenarCampos(usuario);

            // Clear the fields
            textFieldUsername.setText("");
            textFieldContraseña.setText("");
            textFieldRContraseña.setText("");
            textFieldNome.setText("");
            comboRol.setSelectedIndex(0);

            // Clear the error message
            etiquetaMensaje.setText("");
        }
    }
    }

    private void deleteImage(String imagePath) throws FileNotFoundException {
        if (imagePath != null) {
            String projectPath = System.getProperty("user.dir");
            //String fullImagePath = projectPath + "/src/atenda" + imagePath; PARA MAC
            String fullImagePath = projectPath + "\\src\\atenda" + imagePath;
            File imageFile = new File(fullImagePath);
            if (imageFile.exists()) {
                InputStream imageStream = new FileInputStream(imageFile);
                boolean deleted = imageFile.delete();
                if (!deleted) {
                    System.out.println("Failed to delete the image file.");
                }
            }
        }
    }

    private void crearUsuario(String imagen) {
        // Obtener los datos del usuario de los campos de texto
        String username = textFieldUsername.getText();
        String password = new String(textFieldContraseña.getPassword());
        String confirmPassword = new String(textFieldRContraseña.getPassword());
        String nome = textFieldNome.getText();
        String rolString = (String) comboRol.getSelectedItem();
        Rol rol = Rol.valueOf(rolString.toUpperCase());

        // Verificar que todos los campos estén completos
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || nome.isEmpty()) {
            etiquetaMensaje.setText("Todos los campos son requeridos");
            return;
        }

        // Verificar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            etiquetaMensaje.setText("La contraseña no coincide");
            return;
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setNome(nome);
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setAvatar(imagen);

        // Guardar el nuevo usuario en la base de datos
        modeloDAO.saveUsuario(nuevoUsuario);

        // Mostrar mensaje de éxito
        etiquetaMensaje.setText("Usuario creado exitosamente");

        // Limpiar los campos
        textFieldUsername.setText("");
        textFieldContraseña.setText("");
        textFieldRContraseña.setText("");
        textFieldNome.setText("");
        comboRol.setSelectedIndex(0);

        // Actualizar la tabla de usuarios
        mostrarUsuarios();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuariosAdmin = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        botonEliminarUsuario = new javax.swing.JButton();
        botonNuevoUsuario = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        mostrarFotoEtiqueta = new javax.swing.JLabel();
        textFieldUsername = new javax.swing.JTextField();
        textFieldNome = new javax.swing.JTextField();
        textFieldContraseña = new javax.swing.JPasswordField();
        textFieldRContraseña = new javax.swing.JPasswordField();
        comboRol = new javax.swing.JComboBox<>();
        etiquetaMensaje = new javax.swing.JLabel();
        botonSubirFoto = new javax.swing.JButton();
        idEtiqueta = new javax.swing.JLabel();

        tablaUsuariosAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaUsuariosAdmin.setColumnSelectionAllowed(true);
        tablaUsuariosAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuariosAdminMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuariosAdmin);
        tablaUsuariosAdmin.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel1.setText("Username:");

        jLabel2.setText("Contrasinal");

        jLabel3.setText("Repetir contrasinal");

        jLabel4.setText("Nome");

        jLabel5.setText("Rol");

        botonEliminarUsuario.setText("Eliminar");
        botonEliminarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEliminarUsuarioMouseClicked(evt);
            }
        });
        botonEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarUsuarioActionPerformed(evt);
            }
        });

        botonNuevoUsuario.setText("Novo");
        botonNuevoUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonNuevoUsuarioMouseClicked(evt);
            }
        });

        botonGuardar.setText("Gardar");
        botonGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonGuardarMouseClicked(evt);
            }
        });

        jLabel6.setText("Id");

        botonSubirFoto.setText("Seleccionar foto");
        botonSubirFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonSubirFotoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(idEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(60, 60, 60)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(etiquetaMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                            .addComponent(textFieldNome, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                            .addComponent(textFieldUsername)
                                            .addComponent(textFieldContraseña)
                                            .addComponent(textFieldRContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                            .addComponent(comboRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(62, 62, 62))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(mostrarFotoEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(96, 96, 96))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonSubirFoto)
                                .addGap(116, 116, 116))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonNuevoUsuario)
                        .addGap(30, 30, 30)
                        .addComponent(botonGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(botonEliminarUsuario)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(mostrarFotoEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonSubirFoto)
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(textFieldContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(textFieldRContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(textFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(idEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonEliminarUsuario)
                            .addComponent(botonNuevoUsuario)
                            .addComponent(botonGuardar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(etiquetaMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonEliminarUsuarioActionPerformed

    private void botonNuevoUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonNuevoUsuarioMouseClicked
        // TODO add your handling code here:
        habilitarCampos(true);
        nuevoUsuario = true;
    }//GEN-LAST:event_botonNuevoUsuarioMouseClicked

    private void tablaUsuariosAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosAdminMouseClicked
        // TODO add your handling code here:
        System.out.println("Seleccion usuario de tabla");
        int selectedRow = tablaUsuariosAdmin.getSelectedRow();
        if (selectedRow != -1) {
            // Obtiene el valor de la columna de usuario en la fila seleccionada
            String selectedUser = (String) tablaUsuariosAdmin.getValueAt(selectedRow, 0);
            // Obtener los datos del usuario de la base de datos
            Usuario usuario = modeloDAO.obtenerUsuario(selectedUser);
            // Rellena los campos con los valores obtenidos
            llenarCampos(usuario);
        }

    }//GEN-LAST:event_tablaUsuariosAdminMouseClicked

    private void botonGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonGuardarMouseClicked
        // TODO add your handling code here:

        String username = textFieldUsername.getText();

        if (nuevoUsuario) {
            // Si es un nuevo usuario
            crearUsuario(uploadedImagePath);
        } else if (!username.isEmpty()) {
            try {
                // Si no es un nuevo usuario y el campo de nombre de usuario no está vacío
                actualizarUsuario();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(UsuariosAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Restablecer la variable nuevoUsuario a false después de crear o actualizar
        nuevoUsuario = false;

    }//GEN-LAST:event_botonGuardarMouseClicked

    private void botonEliminarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarUsuarioMouseClicked
        // TODO add your handling code here:
        int selectedRow = tablaUsuariosAdmin.getSelectedRow();
        if (selectedRow != -1) {
            // Obtener el nombre del usuario seleccionado en la tabla
            String nombreUsuario = (String) tablaUsuariosAdmin.getValueAt(selectedRow, 0);

            // Obtener el objeto Usuario desde la base de datos
            Usuario usuario = modeloDAO.obtenerUsuario(nombreUsuario);

            if (usuario != null) {
                // Intentar eliminar el usuario de la base de datos
                boolean fueEliminado = modeloDAO.eliminarPorId(usuario.getIdUsuario());

                if (fueEliminado) {
                    // Mostrar mensaje de éxito
                    etiquetaMensaje.setText("Usuario eliminado exitosamente");

                    // Actualizar la tabla de usuarios
                    mostrarUsuarios();
                } else {
                    // Mostrar mensaje de error
                    etiquetaMensaje.setText("Hubo un error al intentar eliminar el usuario");
                }
            }
        }


    }//GEN-LAST:event_botonEliminarUsuarioMouseClicked

    private void botonSubirFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSubirFotoMouseClicked
        // TODO add your handling code here:
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
        try {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String newFileName = timeStamp + "." + extension;
            
            String projectPath = System.getProperty("user.dir");
            //String imagePath = projectPath + "/src/atenda/imagenes/" + newFileName; PARA MAC OS
            String imagePath = projectPath + "\\src\\atenda\\imagenes\\" + newFileName;

            
            File destinationFile = new File(imagePath);
            
            File parentDir = destinationFile.getParentFile();
            if (!parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    System.out.println("Failed to create parent directories for the destination file.");
                    return;
                }
            }
            
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            //uploadedImagePath = "../imagenes/" + newFileName;
            uploadedImagePath = "/imagenes/" + newFileName;
            
            //ImageIcon imageIcon = new ImageIcon(destinationFile.getAbsolutePath());
            //Image image = imageIcon.getImage();
            BufferedImage image = ImageIO.read(destinationFile);
            Image scaledImage = image.getScaledInstance(mostrarFotoEtiqueta.getWidth(), mostrarFotoEtiqueta.getHeight(), Image.SCALE_SMOOTH);
            mostrarFotoEtiqueta.setIcon(new ImageIcon(scaledImage));
            mostrarFotoEtiqueta.revalidate();
            mostrarFotoEtiqueta.repaint();
            mostrarFotoEtiqueta.getParent().revalidate();
            mostrarFotoEtiqueta.getParent().repaint();
            
        } catch (IOException ex) {
                Logger.getLogger(UsuariosAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    }//GEN-LAST:event_botonSubirFotoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEliminarUsuario;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonNuevoUsuario;
    private javax.swing.JButton botonSubirFoto;
    private javax.swing.JComboBox<String> comboRol;
    private javax.swing.JLabel etiquetaMensaje;
    private javax.swing.JLabel idEtiqueta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mostrarFotoEtiqueta;
    private javax.swing.JTable tablaUsuariosAdmin;
    private javax.swing.JPasswordField textFieldContraseña;
    private javax.swing.JTextField textFieldNome;
    private javax.swing.JPasswordField textFieldRContraseña;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables
}
