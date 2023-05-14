package atenda.usuario.negocio;
import atenda.usuarios.Conexion;
import java.sql.*;

public class Pedido {

    private Integer id;
    private Integer id_produto;
    private Integer desconto;
    private Integer unidades;
    private Double prezo;
    private Double costo;

    public Pedido() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(Integer desconto) {
        this.desconto = desconto;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Double getPrezo() {
        return prezo;
    }

    public void setPrezo(Double prezo) {
        this.prezo = prezo;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
    
    public int getProductoStock(String nombreProducto) throws SQLException {
        String sql = "SELECT stock FROM produtos WHERE nome = ?";
        int stock = 0;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1,nombreProducto);
            ResultSet rs  = pstmt.executeQuery();

            if (rs.next()) {
                stock = rs.getInt("stock");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return stock;
    }

}
