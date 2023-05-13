package atenda.usuarios;

import java.util.List;
import java.sql.*;

public class UsuarioDAO implements Dao<Usuario> {

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
                Rol rol = Rol.valueOf(rolString.toUpperCase());
                usuario.setRol(rol);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return usuario;
    }

    //Metodo para autenticación
}
