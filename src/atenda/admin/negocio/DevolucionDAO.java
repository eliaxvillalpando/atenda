/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atenda.admin.negocio;

import atenda.dao.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DevolucionDAO {

    public void guardarDevolucion(int id_pedido, String dependente, int uds, int id_producto, String fecha) {
        String sql = "INSERT INTO devolucion(id_pedido, dependente, unidades, id_producto, data_time) VALUES (?, ?, ?, ?, ?)";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(fecha, formatter);
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id_pedido);
            pstmt.setString(2, dependente);
            pstmt.setInt(3, uds);
            pstmt.setInt(4, id_producto);
            pstmt.setTimestamp(5, timestamp);
            //pstmt.setDate(5, id_producto);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
     public List<Devolucion> getDevoluciones() {
    List<Devolucion> devoluciones = new ArrayList<>();
    String sql = "SELECT * FROM devolucion";

    try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Devolucion devolucion = new Devolucion();
            devolucion.setId_pedido(rs.getInt("id_pedido"));
            
            String dataTimeString = rs.getString("data_time");
            devolucion.setFecha(dataTimeString);

            devolucion.setDependente(rs.getString("dependente"));
            devolucion.setUnidades(rs.getInt("unidades"));
            devoluciones.add(devolucion);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return devoluciones;
}

    
    
    

}
