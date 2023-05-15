/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atenda.usuario.negocio;

import atenda.usuarios.Conexion;
import atenda.usuarios.Dao;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author elias
 */
public class PedidoDAO implements Dao<Pedido> {

    @Override
    public Pedido get(int id) {

        return null;

    }

    @Override
    public List<Pedido> getAll() {

        return null;
    }

    @Override
    public void save(Pedido t) {

    }

    @Override
    public void update(Pedido t) {

    }

    @Override
    public void delete(Pedido t) {

    }

    public boolean savePedidoProducto(int idPedido, String nombreProducto, int descuento, int unidades, double prezo, double coste) {
        try (Connection conn = Conexion.getConnection()) {
            String query = "INSERT INTO linea_pedido (id_pedido, id_produto, desconto, unidades, prezo, coste) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idPedido);
                int productoId = obtenerIdProductoPorNombre(nombreProducto);
                statement.setInt(2, productoId);
                statement.setInt(3, descuento);
                statement.setInt(4, unidades);
                statement.setDouble(5, prezo);
                statement.setDouble(6, coste);
                statement.executeUpdate();
            }
            return true; // Indicar que el pedido se guardó correctamente
        } catch (SQLException e) {
            System.out.println("Error al guardar los detalles del producto en la base de datos: " + e.getMessage());
            return false; // Indicar que hubo un error al guardar el pedido
        }
    }

    public void restarUnidadesDelStock(int productoId, int unidades) {
        try (Connection conn = Conexion.getConnection()) {
            String query = "UPDATE produto SET stock = stock - ? WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, unidades);
                statement.setInt(2, productoId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al restar unidades del stock: " + e.getMessage());
        }
    }

    private int obtenerIdProductoPorNombre(String nombreProducto) {
        // Obtener el id del producto a partir de su nombre en la tabla correspondiente de la base de datos
        int productoId = 0;
        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT id FROM produto WHERE nome = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, nombreProducto);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        productoId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el id del producto por nombre: " + e.getMessage());
        }
        return productoId;
    }

    public int getNextPedidoId() {
        int nextId = 0;
        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT MAX(id_pedido) AS max_id FROM linea_pedido";
            try (PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nextId = resultSet.getInt("max_id") + 1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el siguiente número autoincremental para id_pedido: " + e.getMessage());
        }
        return nextId;
    }

    public void savePedido(int idPedido, int idCliente, String fechaHora) {
        try (Connection conn = Conexion.getConnection()) {
            String query = "INSERT INTO pedido (id_pedido_devol, id_cliente, data_hora) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idPedido);
                statement.setInt(2, idCliente);
                statement.setString(3, fechaHora);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos del pedido en la base de datos: " + e.getMessage());
        }
    }

    public List<String> getPedidoRecords() {
        List<String> pedidoRecords = new ArrayList<>();
        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT id_pedido_devol, data_hora, id_cliente FROM pedido";
            try (PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idPedido = resultSet.getInt("id_pedido_devol");
                    String dataHora = resultSet.getString("data_hora");
                    int idCliente = resultSet.getInt("id_cliente");
                    String record = idPedido + " " + dataHora + " " + idCliente;
                    pedidoRecords.add(record);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los registros de pedido: " + e.getMessage());
        }
        return pedidoRecords;
    }

    public void restarUnidadesDelStockBatch(Map<Integer, Integer> productosUnidades) {
        try (Connection conn = Conexion.getConnection()) {
            String query = "UPDATE producto SET stock = stock - ? WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                for (Map.Entry<Integer, Integer> entry : productosUnidades.entrySet()) {
                    int productoId = entry.getKey();
                    int unidades = entry.getValue();
                    statement.setInt(1, unidades);
                    statement.setInt(2, productoId);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Error al restar unidades del stock: " + e.getMessage());
        }
    }

    public List<LineaPedido> obtenerLineasPedidoPorIdPedido(int idPedido) {
        List<LineaPedido> lineasPedido = new ArrayList<>();
        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT * FROM linea_pedido WHERE id_pedido = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idPedido);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        LineaPedido lineaPedido = new LineaPedido();
                        lineaPedido.setId_pedido(idPedido);
                        lineaPedido.setId_producto(resultSet.getInt("id_produto"));
                        lineaPedido.setUnidades(resultSet.getInt("unidades"));
                        lineaPedido.setPrezo(resultSet.getDouble("prezo"));
                        lineaPedido.setDesconto(resultSet.getInt("desconto"));
                        lineasPedido.add(lineaPedido);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las líneas de pedido por id de pedido: " + e.getMessage());
        }
        return lineasPedido;
    }

    public String obtenerNombreProductoPorId(int idProducto) {
        String nombreProducto = "";
        try (Connection conn = Conexion.getConnection()) {
            String query = "SELECT nome FROM produto WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idProducto);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        nombreProducto = resultSet.getString("nome");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre del producto por id: " + e.getMessage());
        }
        return nombreProducto;
    }
    
    public boolean updateLineaPedidoForDevolucion(int idPedido, int id_produto, int unidades, double coste) {
    boolean isUpdated = false;
    String sql = "UPDATE linea_pedido SET unidades = ?, coste = ? WHERE id_pedido = ? AND id_produto = ?";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Establecer los parámetros
        pstmt.setInt(1, unidades);
        pstmt.setDouble(2, coste);
        pstmt.setInt(3, idPedido);
        pstmt.setInt(4, id_produto);

        // Actualizar y comprobar si se actualizó alguna fila
        int rowAffected = pstmt.executeUpdate();
        if(rowAffected > 0) {
            isUpdated = true;
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return isUpdated;
}

    public List<String> getPedidoRecordsPorFecha(Date fechaDesde, Date fechaHasta) {
    List<String> pedidoRecords = new ArrayList<>();
    try (Connection conn = Conexion.getConnection()) {
        String query = "SELECT id_pedido_devol, data_hora, id_cliente FROM pedido WHERE data_hora BETWEEN ? AND ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Convertir las fechas a objetos java.sql.Timestamp
            Timestamp fechaDesdeSQL = new Timestamp(fechaDesde.getTime());
            Timestamp fechaHastaSQL = new Timestamp(fechaHasta.getTime());
            
            // Establecer los parámetros de las fechas
            statement.setTimestamp(1, fechaDesdeSQL);
            statement.setTimestamp(2, fechaHastaSQL);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idPedido = resultSet.getInt("id_pedido_devol");
                    String dataHora = resultSet.getString("data_hora");
                    int idCliente = resultSet.getInt("id_cliente");
                    String record = idPedido + " " + dataHora + " " + idCliente;
                    pedidoRecords.add(record);
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener los registros de pedido por fecha: " + e.getMessage());
    }
    return pedidoRecords;
}

    
    public double sumarCosteLineaPedidoPorFecha(Date fechaDesde, Date fechaHasta) {
    double sumaCoste = 0.0;
    try (Connection conn = Conexion.getConnection()) {
        String query = "SELECT lp.id_pedido, SUM(lp.coste) AS total_coste "
                     + "FROM pedido p "
                     + "INNER JOIN linea_pedido lp ON p.id_pedido_devol = lp.id_pedido "
                     + "WHERE p.data_hora BETWEEN ? AND ? "
                     + "GROUP BY lp.id_pedido";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Convertir las fechas a objetos java.sql.Timestamp
            Timestamp fechaDesdeSQL = new Timestamp(fechaDesde.getTime());
            Timestamp fechaHastaSQL = new Timestamp(fechaHasta.getTime());
            
            // Establecer los parámetros de las fechas
            statement.setTimestamp(1, fechaDesdeSQL);
            statement.setTimestamp(2, fechaHastaSQL);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double coste = resultSet.getDouble("total_coste");
                    sumaCoste += coste;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al calcular la suma de coste en la tabla linea_pedido por fecha: " + e.getMessage());
    }
    return sumaCoste;
}
    
    public int sumarDescontoLineaPedidoPorFecha(Date fechaDesde, Date fechaHasta) {
    int sumaDesconto = 0;
    try (Connection conn = Conexion.getConnection()) {
        String query = "SELECT lp.id_pedido, SUM(lp.desconto) AS total_desconto "
                     + "FROM pedido p "
                     + "INNER JOIN linea_pedido lp ON p.id_pedido_devol = lp.id_pedido "
                     + "WHERE p.data_hora BETWEEN ? AND ? "
                     + "GROUP BY lp.id_pedido";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Convertir las fechas a objetos java.sql.Timestamp
            Timestamp fechaDesdeSQL = new Timestamp(fechaDesde.getTime());
            Timestamp fechaHastaSQL = new Timestamp(fechaHasta.getTime());
            
            // Establecer los parámetros de las fechas
            statement.setTimestamp(1, fechaDesdeSQL);
            statement.setTimestamp(2, fechaHastaSQL);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int desconto = resultSet.getInt("total_desconto");
                    sumaDesconto += desconto;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al calcular la suma de desconto en la tabla linea_pedido por fecha: " + e.getMessage());
    }
    return sumaDesconto;
}


public double multiplicarUnidadesCosteLineaPedidoPorFecha(Date fechaDesde, Date fechaHasta) {
    double totalMultiplicacion = 0.0;
    try (Connection conn = Conexion.getConnection()) {
        String query = "SELECT lp.id_pedido, SUM(lp.unidades * pr.coste) AS total_multiplicacion "
                     + "FROM pedido p "
                     + "INNER JOIN linea_pedido lp ON p.id_pedido_devol = lp.id_pedido "
                     + "INNER JOIN produto pr ON lp.id_produto = pr.id "
                     + "WHERE p.data_hora BETWEEN ? AND ? "
                     + "GROUP BY lp.id_pedido";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Convertir las fechas a objetos java.sql.Timestamp
            Timestamp fechaDesdeSQL = new Timestamp(fechaDesde.getTime());
            Timestamp fechaHastaSQL = new Timestamp(fechaHasta.getTime());
            
            // Establecer los parámetros de las fechas
            statement.setTimestamp(1, fechaDesdeSQL);
            statement.setTimestamp(2, fechaHastaSQL);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double multiplicacion = resultSet.getDouble("total_multiplicacion");
                    totalMultiplicacion += multiplicacion;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al calcular la multiplicación de unidades y coste en la tabla linea_pedido por fecha: " + e.getMessage());
    }
    return totalMultiplicacion;
}

    public double sumarIvaLineaPedidoPorFecha(Date fechaDesde, Date fechaHasta) {
    double sumaIva = 0.0;
    try (Connection conn = Conexion.getConnection()) {
        String query = "SELECT lp.id_pedido, SUM(lp.prezo * (pr.iva / 100.0)) AS total_iva "
                     + "FROM pedido p "
                     + "INNER JOIN linea_pedido lp ON p.id_pedido_devol = lp.id_pedido "
                     + "INNER JOIN produto pr ON lp.id_produto = pr.id "
                     + "WHERE p.data_hora BETWEEN ? AND ? "
                     + "GROUP BY lp.id_pedido";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Convertir las fechas a objetos java.sql.Timestamp
            Timestamp fechaDesdeSQL = new Timestamp(fechaDesde.getTime());
            Timestamp fechaHastaSQL = new Timestamp(fechaHasta.getTime());
            
            // Establecer los parámetros de las fechas
            statement.setTimestamp(1, fechaDesdeSQL);
            statement.setTimestamp(2, fechaHastaSQL);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double iva = resultSet.getDouble("total_iva");
                    sumaIva += iva;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al calcular la suma de IVA en la tabla linea_pedido y produto por fecha: " + e.getMessage());
    }
    return sumaIva;
}

    

}
