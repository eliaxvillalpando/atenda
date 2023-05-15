package atenda.modelo;

import atenda.controlador.Dao;
import atenda.controlador.Conexion;
import java.util.List;
import java.sql.*;

public class UsuarioDAO implements Dao<Usuario> {

    private static int idClienteActual; // Variable estática para almacenar el ID del cliente actual

    public static int getIdClienteActual() {
        return idClienteActual;
    }

    public static void setIdClienteActual(int idCliente) {
        idClienteActual = idCliente;
    }

    public int obtenerIdClientePorUsuario(String usuario) {
        int idCliente = 0;

        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT id FROM usuario WHERE nome = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, usuario);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idCliente = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el ID del cliente por usuario: " + e.getMessage());
        }

        return idCliente;
    }

    public String getNombreUsuario(String usuario) {
        String nombreUsuario = "";

        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT nome FROM usuario WHERE username = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, usuario);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        nombreUsuario = resultSet.getString("nome");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre de usuario: " + e.getMessage());
        }

        return nombreUsuario;
    }

    public int obtenerIdUsuario(String usuario) {
        int idUsuario = 0;

        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT id FROM usuario WHERE username = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, usuario);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idUsuario = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el ID de usuario: " + e.getMessage());
        }

        return idUsuario;
    }

    @Override
    public Usuario get(int id) {
        // Implementar la lógica para obtener un usuario

        return null;
    }

    @Override
    public List<Usuario> getAll() {
        // Implementar la lógica para obtener todos los usuarios

        return null;
        // Implementar la lógica para obtener todos los usuarios
    }

    @Override
    public void save(Usuario usuario) {
        // Implementar la lógica para guardar un usuario
        String sql = "INSERT INTO usuario(username, password, nome, rol, avatar) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getNome());
            pstmt.setString(4, usuario.getRol().name());
            pstmt.setString(5, usuario.getAvatar());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void update(Usuario usuario) {
        // Implementar la lógica para actualizar un usuario
        String sql = "UPDATE usuario SET username = ?, password = ?, nome = ?, rol = ? WHERE nome = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getNome());
            pstmt.setString(4, usuario.getRol().name());
            pstmt.setString(5, usuario.getNome());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Usuario usuario) {
        // Implementar la lógica para eliminar un usuario
    }

    public boolean autenticar(String usuario, String contraseña) {
        String sql = "SELECT * FROM usuario WHERE username = ? AND password = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);

            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // usuario encontrado
            // usuario no encontrado
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Rol getRol(String username) {
        String sql = "SELECT rol FROM usuario WHERE username = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Rol.valueOf(rs.getString("rol").toUpperCase());
            } else {
                return null; // usuario no encontrado
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Usuario obtenerUsuario(String nombreUsuario) {
        Usuario usuario = null;

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usuario WHERE nome = ?");) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNome(rs.getString("nome"));
                String rolString = rs.getString("rol");
                usuario.setIdUsuario(rs.getInt("id"));
                Rol rol = Rol.valueOf(rolString.toUpperCase());
                usuario.setRol(rol);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return usuario;
    }

    public String obtenerNombreConId(int idUsuario) {
        String nombreUsuario = null;

        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT nome FROM usuario WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idUsuario);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        nombreUsuario = resultSet.getString("nome");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre de usuario: " + e.getMessage());
        }

        return nombreUsuario;
    }

    public String obtenerRol(String username) {
        String sql = "SELECT rol FROM usuario WHERE username = ?";
        String rol = null;

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rol = rs.getString("rol");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return rol;
    }

    public String obtenerUserNameConId(int idUsuario) {
        String nombreUsuario = null;

        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT username FROM usuario WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idUsuario);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        nombreUsuario = resultSet.getString("username");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre de usuario: " + e.getMessage());
        }

        return nombreUsuario;
    }

    public String obtenerRolconID(int id) {
        String sql = "SELECT rol FROM usuario WHERE id = ?";
        String rol = null;

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rol = rs.getString("rol");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return rol;
    }

    public void cambiarContraseña(int id, String nuevaContraseña) {
        String sql = "UPDATE usuario SET password = ? WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevaContraseña);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;  // Devuelve true si se eliminó algún registro

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;  // Devuelve false si hubo algún error
        }
    }

}
