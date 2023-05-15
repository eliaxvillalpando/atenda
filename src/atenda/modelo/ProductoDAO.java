package atenda.modelo;

import atenda.controlador.Conexion;
import atenda.controlador.Dao;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class ProductoDAO implements Dao<Producto> {

    @Override
    public Producto get(int id) {

        return null;
    }

    @Override
    public List<Producto> getAll() {

        return null;
    }

    @Override
    public void save(Producto t) {

        String sql = "INSERT INTO produto (nome, prezo, desconto, coste, iva, stock, baixa) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t.getNome());
            pstmt.setDouble(2, t.getPrezo());
            pstmt.setInt(3, t.getDesconto());
            pstmt.setDouble(4, t.getCoste());
            pstmt.setString(5, t.getIva().toString()); // Asume que Iva es un enum con métodos toString adecuados
            pstmt.setInt(6, t.getStock());
            pstmt.setBoolean(7, t.isBaixa());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(Producto t) {

    }

    @Override
    public void delete(Producto t) {

        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, t.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Producto> getAllProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT * FROM produto"; // Asume que la tabla se llama "producto"
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNome(rs.getString("nome"));
                producto.setCoste(rs.getDouble("coste"));
                producto.setPrezo(rs.getDouble("prezo"));
                producto.setDesconto(rs.getInt("desconto"));
                String ivaString = rs.getString("iva");
                Iva ivaValue = Iva.valueOf(ivaString.toUpperCase());
                producto.setIva(ivaValue);
                producto.setStock(rs.getInt("stock"));

                productos.add(producto);
            }

            rs.close();
            stmt.close();
        } // Asume que la tabla se llama "producto"

        return productos;
    }

    public boolean actualizarProducto(int id, String nombreColumna, String valor) {
        String sql = "UPDATE produto SET " + nombreColumna + " = ? WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, valor);
            pstmt.setInt(2, id);

            int filasActualizadas = pstmt.executeUpdate();

            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(Producto t) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, t.getId());
            int filasEliminadas = pstmt.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getStockByProductName(String productName) throws SQLException {
        Connection connection = Conexion.getConnection();
        String query = "SELECT stock FROM produto WHERE nome = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, productName);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("stock");
        } else {
            throw new SQLException("No se encontró el producto con nombre " + productName);
        }
    }

    public Producto getProductoByNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM produto WHERE nome = ?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNome(rs.getString("nome"));
                    producto.setCoste(rs.getDouble("coste"));
                    producto.setPrezo(rs.getDouble("prezo"));
                    producto.setDesconto(rs.getInt("desconto"));
                    String ivaString = rs.getString("iva");
                    Iva ivaValue = Iva.valueOf(ivaString.toUpperCase());
                    producto.setIva(ivaValue);
                    producto.setStock(rs.getInt("stock"));
                    return producto;
                } else {
                    throw new SQLException("No se encontró el producto con nombre: " + nombre);
                }
            }
        }
    }

    public int getIdProductoPorNombre(String nombre) {

        int idProducto = 0;
        String query = "SELECT id FROM produto WHERE nome = ?";

        try (Connection conn = Conexion.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, nombre);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idProducto = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return idProducto;

    }
    
    public int getUnidadesOriginal(int idProducto) {
    String sql = "SELECT stock FROM produto WHERE id = ?";
    int unidadesOriginal = 0;

    try (Connection conn = Conexion.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, idProducto);

        ResultSet rs = pstmt.executeQuery();

        // Extraer el resultado
        if (rs.next()) {
            unidadesOriginal = rs.getInt("stock");
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return unidadesOriginal;
}
    
    public void actualizarStockProducto(int idProducto, int unidadesRestar) {
    String sql = "UPDATE produto SET stock = stock - ? WHERE id = ?";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, unidadesRestar);
        pstmt.setInt(2, idProducto);

        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}


}
