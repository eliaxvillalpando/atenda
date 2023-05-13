package atenda.admin.vistas;

import atenda.usuarios.Conexion;
import atenda.usuarios.Rol;
import atenda.usuarios.Usuario;
import atenda.usuarios.UsuarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elias
 */
public class UsuariosPanel extends javax.swing.JPanel {

    /**
     * Creates new form UsuariosPanel
     */
    private UsuarioDAO daoUsuario;
    private boolean nuevoUsuario;

    public UsuariosPanel() {
        initComponents();
        String[] roles = {"ADMINISTRADOR", "DEPENDENTE"};
        // Establecer el arreglo como modelo del combo box
        comboRol.setModel(new DefaultComboBoxModel<>(roles));
        daoUsuario = new UsuarioDAO();
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
        System.out.println("Llenar campos metodo" + usuario.getNome());
        if (usuario != null) {
            habilitarCampos(true);
            textFieldUsername.setText(usuario.getUsername());
            textFieldContraseña.setText(usuario.getPassword());
            textFieldNome.setText(usuario.getNome());

            Rol rol = usuario.getRol();

            // Obtener el nombre del rol como un String
            String rolString = rol != null ? rol.name() : "";

            // Establecer el rol seleccionado en el combo box
            comboRol.setSelectedItem(rolString);

        } else {

            habilitarCampos(false);
            textFieldUsername.setText("");
            textFieldContraseña.setText("");
            textFieldNome.setText("");
        }
    }

    private void actualizarUsuario() {
        int selectedRow = tablaUsuariosAdmin.getSelectedRow();
        if (selectedRow != -1) {
            String selectedUser = (String) tablaUsuariosAdmin.getValueAt(selectedRow, 0);
            Usuario usuario = daoUsuario.obtenerUsuario(selectedUser);

            if (usuario != null) {
                // Obtener los datos del usuario de los campos de texto
                String username = textFieldUsername.getText();
                String password = new String(textFieldContraseña.getPassword());
                String confirmPassword = new String(textFieldRContraseña.getPassword());
                String nome = textFieldNome.getText();
                String rolString = (String) comboRol.getSelectedItem();
                Rol rol = Rol.valueOf(rolString.toUpperCase());

                // Verificar que las contraseñas coincidan
                if (!password.equals(confirmPassword)) {
                    etiquetaMensaje.setText("Contraseña no coincide");
                    return;
                }

                // Actualizar los campos del usuario
                usuario.setUsername(username);
                usuario.setPassword(password);
                usuario.setNome(nome);
                usuario.setRol(rol);
                usuario.setAvatar("/path/to/avatar");

                // Actualizar el usuario en la base de datos
                daoUsuario.update(usuario);

                // Llenar los campos con los nuevos datos del usuario actualizado
                llenarCampos(usuario);

                // Actualizar la tabla de usuarios
                mostrarUsuarios();

                // Limpiar los campos
                textFieldUsername.setText("");
                textFieldContraseña.setText("");
                textFieldRContraseña.setText("");
                textFieldNome.setText("");
                comboRol.setSelectedIndex(0);

                // Limpiar el mensaje de error
                etiquetaMensaje.setText("");
            }
        }
    }

    private void crearUsuario() {
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
        nuevoUsuario.setAvatar("/path/to/avatar");

        // Guardar el nuevo usuario en la base de datos
        daoUsuario.save(nuevoUsuario);

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
        jButton1 = new javax.swing.JButton();
        botonNuevoUsuario = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textFieldUsername = new javax.swing.JTextField();
        textFieldNome = new javax.swing.JTextField();
        textFieldContraseña = new javax.swing.JPasswordField();
        textFieldRContraseña = new javax.swing.JPasswordField();
        comboRol = new javax.swing.JComboBox<>();
        etiquetaMensaje = new javax.swing.JLabel();

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

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        jLabel7.setText("Foto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonNuevoUsuario)
                        .addGap(30, 30, 30)
                        .addComponent(botonGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel1)))
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(227, 227, 227))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(etiquetaMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textFieldNome, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                        .addComponent(textFieldUsername)
                                        .addComponent(textFieldContraseña)
                                        .addComponent(textFieldRContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                        .addComponent(comboRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(62, 62, 62))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(botonNuevoUsuario)
                            .addComponent(botonGuardar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(etiquetaMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            Usuario usuario = daoUsuario.obtenerUsuario(selectedUser);
            // Rellena los campos con los valores obtenidos
            llenarCampos(usuario);
        }

    }//GEN-LAST:event_tablaUsuariosAdminMouseClicked

    private void botonGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonGuardarMouseClicked
        // TODO add your handling code here:

        String username = textFieldUsername.getText();

        if (nuevoUsuario) {
            // Si es un nuevo usuario
            crearUsuario();
        } else if (!username.isEmpty()) {
            // Si no es un nuevo usuario y el campo de nombre de usuario no está vacío
            actualizarUsuario();
        }

        // Restablecer la variable nuevoUsuario a false después de crear o actualizar
        nuevoUsuario = false;

    }//GEN-LAST:event_botonGuardarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonNuevoUsuario;
    private javax.swing.JComboBox<String> comboRol;
    private javax.swing.JLabel etiquetaMensaje;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaUsuariosAdmin;
    private javax.swing.JPasswordField textFieldContraseña;
    private javax.swing.JTextField textFieldNome;
    private javax.swing.JPasswordField textFieldRContraseña;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables
}
