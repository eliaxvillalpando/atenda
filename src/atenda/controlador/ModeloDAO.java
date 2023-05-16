package atenda.controlador;

import atenda.controlador.Conexion;
import atenda.modelo.Devolucion;
import atenda.modelo.Iva;
import atenda.modelo.LineaPedido;
import atenda.modelo.Producto;
import atenda.modelo.ProductosWraper;
import atenda.modelo.Rol;
import atenda.modelo.Usuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

public class ModeloDAO {

    //METODOS DEVOLUCIONES 
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

    //METODOS PEDIDODAO
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
            if (rowAffected > 0) {
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
            String query = "SELECT lp.id_pedido, SUM(lp.prezo * CASE pr.iva WHEN 'TIPO_REDUCIDO' THEN 10.0 / 100.0 WHEN 'TIPO_XERAL' THEN 21.0 / 100.0 WHEN 'TIPO_SUPERREDUCIDO' THEN 4.0 / 100.0 ELSE 0.0 END) AS total_iva "
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

    //METODOS PRODUCTO 
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

    public void saveUsuario(Usuario usuario) {
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
                producto.setIva(Iva.valueOf(rs.getString("iva")));
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
                    producto.setIva(Iva.valueOf(rs.getString("iva")));
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

    public void importarProductos(String filePath) throws PropertyException {
        String sql = "SELECT nome, prezo, desconto, coste, iva, stock FROM produto";
        List<Producto> listaProductos = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Producto produto = new Producto(rs.getString("nome"), rs.getDouble("prezo"), rs.getInt("desconto"), rs.getDouble("coste"), rs.getString("iva"), rs.getInt("stock"));
                produto.setIvaString(rs.getString("iva"));
                listaProductos.add(produto);
            }

            ProductosWraper productos = new ProductosWraper();
            productos.setProductos(listaProductos);

            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductosWraper.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(productos, new FileWriter(file));

        } catch (SQLException | JAXBException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void importarProductosFromXML(String filePath) throws RuntimeException {
        File file = new File(filePath);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductosWraper.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ProductosWraper productosWrapper = (ProductosWraper) jaxbUnmarshaller.unmarshal(file);
            List<Producto> productosList = productosWrapper.getProductos();

            String sql = "INSERT INTO produto (nome, prezo, desconto, coste, iva, stock) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = Conexion.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Producto producto : productosList) {
                    pstmt.setString(1, producto.getNome());
                    pstmt.setDouble(2, producto.getPrezo());
                    pstmt.setInt(3, producto.getDesconto());
                    pstmt.setDouble(4, producto.getCoste());
                    // Parse ivaAsignado to corresponding Iva Enum value
                    Iva iva = Iva.fromDouble(producto.getIvaAsignado());
                    pstmt.setString(5, iva.name());
                    pstmt.setInt(6, producto.getStock());
                    pstmt.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Error occurred while saving the product to the database.", ex);
            }

        } catch (JAXBException ex) {
            throw new RuntimeException("The XML file is not in the correct format.", ex);
        }
    }

}
