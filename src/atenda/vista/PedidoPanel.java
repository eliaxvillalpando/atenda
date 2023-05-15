package atenda.vista;

import atenda.modelo.Iva;
import atenda.modelo.Producto;
import atenda.modelo.ProductoDAO;
import atenda.modelo.Pedido;
import atenda.modelo.PedidoDAO;
import atenda.modelo.UsuarioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elias
 */
public class PedidoPanel extends javax.swing.JPanel {

    /**
     * Creates new form PedidoPanel
     */
    private int stockDisponible = 0;
    private Timer timer;
    private VistaTPV vistaTPV;
    private List<Producto> productosAgregados = new ArrayList<>();

    public PedidoPanel(VistaTPV vistaTPV) {
        initComponents();
        this.vistaTPV = vistaTPV; // Almacenar la instancia recibida en la variable de instancia
        fillComboProductos();
        setupTimer();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboProductos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        SliderCantidadUnidades = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        botonAgregarProducto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaContenidoPedido = new javax.swing.JTable();
        botonHacerPedido = new javax.swing.JButton();
        botonCancelarPedido = new javax.swing.JButton();
        botonEliminarProducto = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        stockProducto = new javax.swing.JLabel();
        cantidadDescuento = new javax.swing.JSpinner();
        mensajeStock = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        totalPedidoLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        etiquetaIvaTotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        subTotalEtiqueta = new javax.swing.JLabel();

        comboProductos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboProductosItemStateChanged(evt);
            }
        });

        jLabel2.setText("Seleccione produto");

        jLabel1.setText("Uds");

        SliderCantidadUnidades.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SliderCantidadUnidadesStateChanged(evt);
            }
        });
        SliderCantidadUnidades.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                SliderCantidadUnidadesPropertyChange(evt);
            }
        });

        jLabel3.setText("Desconto (%)");

        botonAgregarProducto.setText("Engadir ao pedido");
        botonAgregarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAgregarProductoMouseClicked(evt);
            }
        });

        tablaContenidoPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "UDS", "Prezo (€)", "Desc (%)", "IVA (%)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaContenidoPedido);

        botonHacerPedido.setText("Tramitar pedido");
        botonHacerPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonHacerPedidoMouseClicked(evt);
            }
        });

        botonCancelarPedido.setText("Cancelar pedido");
        botonCancelarPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonCancelarPedidoMouseClicked(evt);
            }
        });

        botonEliminarProducto.setText("Eliminar do pedido");
        botonEliminarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEliminarProductoMouseClicked(evt);
            }
        });

        jLabel4.setText("Stock");

        stockProducto.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N

        jLabel5.setText("Total:");

        jLabel6.setText("Iva:");

        jLabel7.setText("Sub-total:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(stockProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboProductos, javax.swing.GroupLayout.Alignment.LEADING, 0, 147, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(SliderCantidadUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonAgregarProducto)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(cantidadDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)))
                        .addGap(0, 151, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(botonHacerPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(159, 159, 159)
                                .addComponent(botonCancelarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(93, 93, 93)
                                        .addComponent(mensajeStock, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botonEliminarProducto)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(etiquetaIvaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(subTotalEtiqueta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                        .addComponent(totalPedidoLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cantidadDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SliderCantidadUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stockProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(botonAgregarProducto)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(mensajeStock, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonEliminarProducto)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(etiquetaIvaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(subTotalEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(totalPedidoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelarPedido)
                    .addComponent(botonHacerPedido))
                .addGap(69, 69, 69))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SliderCantidadUnidadesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_SliderCantidadUnidadesPropertyChange
        // TODO add your handling code here:
        int cantidad = (int) SliderCantidadUnidades.getValue();
        int stockDisponible = obtenerStockProductoSeleccionado();

        if (cantidad > stockDisponible) {
            mostrarMensajeStock("No puedes agregar una cantidad mayor al stock disponible");
            SliderCantidadUnidades.setValue(stockDisponible); // Restaurar la cantidad al stock disponible
        } else if (cantidad < 0) {
            mostrarMensajeStock("No puedes agregar una cantidad negativa");
            SliderCantidadUnidades.setValue(0); // Restaurar la cantidad a 0
        } else {
            mensajeStock.setText(""); // Borra el mensaje
        }
    }//GEN-LAST:event_SliderCantidadUnidadesPropertyChange

    private void SliderCantidadUnidadesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SliderCantidadUnidadesStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_SliderCantidadUnidadesStateChanged

    private void botonAgregarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarProductoMouseClicked
        // TODO add your handling code here:
        String selectedProduct = (String) comboProductos.getSelectedItem();
        int cantidad = (int) SliderCantidadUnidades.getValue();
        int stockDisponible = obtenerStockProductoSeleccionado(selectedProduct);
        int descuento = (int) cantidadDescuento.getValue();

        if (cantidad > stockDisponible) {
            mensajeStock.setText("No puedes agregar una cantidad mayor al stock disponible");
            return;
        } else if (cantidad <= 0) {
            mensajeStock.setText("No puedes agregar una cantidad negativa o igual a 0");
            return;
        } else if (descuento >= 100) {
            mensajeStock.setText("El descuento no puede ser mayor o igual a 100");
            return;
        }

        agregarProducto(selectedProduct, cantidad, descuento);

        // Restar la cantidad agregada al stock disponible
        int nuevoStock = stockDisponible - cantidad;
        actualizarStockProductoSeleccionado(nuevoStock);
        SliderCantidadUnidades.setValue(0); // Restaurar la cantidad a 0 después de agregar el producto
    }//GEN-LAST:event_botonAgregarProductoMouseClicked

    private void botonHacerPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonHacerPedidoMouseClicked

        PedidoDAO pedidoDAO = new PedidoDAO();
        UsuarioDAO userDAO = new UsuarioDAO();

        int idPedido = pedidoDAO.getNextPedidoId();

        DefaultTableModel model = (DefaultTableModel) tablaContenidoPedido.getModel();
        int rowCount = model.getRowCount();

        String nombreUsuarioStr = vistaTPV.menuUsuario.getText(); // Obtener el nombre de usuario desde la instancia de VistaTPV
        int idUsuario = Integer.parseInt(nombreUsuarioStr);

        for (int i = 0; i < rowCount; i++) {
            // Obtener los datos del producto de la fila de la tabla
            String nombreProducto = (String) model.getValueAt(i, 0);
            int descuento = (int) model.getValueAt(i, 3);
            int unidades = (int) model.getValueAt(i, 1);
            double prezo = (double) model.getValueAt(i, 2);
            double coste = prezo * unidades;

            // Guardar los datos del producto en la base de datos como una fila del pedido
            boolean pedidoGuardado = pedidoDAO.savePedidoProducto(idPedido, nombreProducto, descuento, unidades, prezo, coste);

            if (pedidoGuardado) {
                ProductoDAO productoDAO = new ProductoDAO();
            int idProducto = productoDAO.getIdProductoPorNombre(nombreProducto);
            productoDAO.actualizarStockProducto(idProducto, unidades);
                mensajeStock.setForeground(Color.GREEN);

                mensajeStock.setText("Pedido realizado exitosamente");
            } else {
                mensajeStock.setForeground(Color.RED);
                mensajeStock.setText("No se pudo realizar el pedido");
            }

        }
        pedidoDAO.savePedido(idPedido, idUsuario, obtenerFechaHoraActual());

        // Limpiar la lista de productos agregados
        productosAgregados.clear();

        // Actualizar la base de datos con los cambios en el stock
        restaurarStockEnBaseDeDatos();

        DefaultTableModel modelCopy = new DefaultTableModel();
        modelCopy.setColumnIdentifiers(new Object[]{
            "Producto", "Unidades", "Precio", "Descuento", "IVA" // Agrega más títulos de columnas si es necesario
        });
        for (int i = 0; i < model.getRowCount(); i++) {
            modelCopy.addRow(new Object[]{
                model.getValueAt(i, 0),
                model.getValueAt(i, 1),
                model.getValueAt(i, 2),
                model.getValueAt(i, 3),
                // Aquí asumimos que la columna IVA es la columna 4 en tu tabla original
                
                model.getValueAt(i, 4)
            });
        }
        String total = totalPedidoLabel.getText();
        String iva = etiquetaIvaTotal.getText();

        // Limpiar la tabla y los totales
        model.setRowCount(0);
        subTotalEtiqueta.setText("0");
        etiquetaIvaTotal.setText("0");
        totalPedidoLabel.setText("0");

        // Actualizar el stock del producto seleccionado en la etiqueta stockProducto
        comboProductosItemStateChanged(null);

        // Crear un nuevo JFrame para mostrar la información del pedido
        JFrame pedidoCreadoInfo = new JFrame("Información del pedido");
        pedidoCreadoInfo.setSize(600, 400); // establece el tamaño del JFrame

        // Crear un nuevo JTable y establecer su modelo para ser igual al modelo copiado de tablaContenidoPedido
        JTable tablaPedidoInfo = new JTable();
        tablaPedidoInfo.setModel(modelCopy);

        // Crear nuevos JLabels y establecer su texto para ser igual al total e IVA copiados
        JLabel totalPedidoLabelInfo = new JLabel("Total: " + total);

        // Crear un panel para contener la tabla y los JLabels
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tablaPedidoInfo), BorderLayout.CENTER);
        panel.add(totalPedidoLabelInfo, BorderLayout.SOUTH);

        // Ajustar el tamaño del panel para que sea más pequeño que el JFrame
        panel.setPreferredSize(new Dimension(500, 300));

        // Agregar el panel al JFrame y centrarlo
        pedidoCreadoInfo.getContentPane().setLayout(new GridBagLayout());
        pedidoCreadoInfo.getContentPane().add(panel);

        // Mostrar el JFrame
        pedidoCreadoInfo.setVisible(true);

    }//GEN-LAST:event_botonHacerPedidoMouseClicked

    private void botonCancelarPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCancelarPedidoMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tablaContenidoPedido.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        productosAgregados.clear();

        subTotalEtiqueta.setText("0");
        etiquetaIvaTotal.setText("0");
        totalPedidoLabel.setText("0");

        // Actualizar el stock del producto seleccionado en la etiqueta stockProducto
        comboProductosItemStateChanged(null);
    }//GEN-LAST:event_botonCancelarPedidoMouseClicked

    private void comboProductosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboProductosItemStateChanged
        // TODO add your handling code here:
        if (evt != null && evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedProduct = (String) comboProductos.getSelectedItem();
            ProductoDAO productoDAO = new ProductoDAO();

            try {
                stockDisponible = productoDAO.getStockByProductName(selectedProduct);
                stockProducto.setText(String.valueOf(stockDisponible));
                mensajeStock.setText(""); // Borra el mensaje
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }//GEN-LAST:event_comboProductosItemStateChanged

    private void botonEliminarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarProductoMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tablaContenidoPedido.getSelectedRow();

        if (filaSeleccionada >= 0) {
            DefaultTableModel model = (DefaultTableModel) tablaContenidoPedido.getModel();
            String nombreProducto = (String) model.getValueAt(filaSeleccionada, 0);
            int cantidadEliminada = (int) model.getValueAt(filaSeleccionada, 1);
            model.removeRow(filaSeleccionada);

            int stockInicial = obtenerStockProductoSeleccionado(nombreProducto);
            int stockConProductosEnLaTabla = stockInicial + cantidadEliminada;

            // Actualizar el stock en la etiqueta stockProducto
            stockProducto.setText(String.valueOf(stockConProductosEnLaTabla));

            // Actualizar el stock en la lista de productos agregados
            Producto productoSeleccionado = productosAgregados.get(filaSeleccionada);
            productoSeleccionado.setStock(stockConProductosEnLaTabla);

            // Eliminar el producto de la lista de productos agregados
            productosAgregados.remove(filaSeleccionada);

            mensajeStock.setText(""); // Borra el mensaje

            calcularTotalPedido(); // Recalcular el total
        }
    }//GEN-LAST:event_botonEliminarProductoMouseClicked

    private int obtenerStockProductoSeleccionado(String nombreProducto) {
        for (Producto producto : productosAgregados) {
            if (producto.getNome().equals(nombreProducto)) {
                return producto.getStock();
            }
        }

        // Si el producto no se ha agregado previamente, obtener el stock desde la base de datos
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            return productoDAO.getStockByProductName(nombreProducto);
        } catch (SQLException ex) {
            Logger.getLogger(PedidoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    private void fillComboProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            List<Producto> productos = productoDAO.getAllProductos();
            for (Producto producto : productos) {
                comboProductos.addItem(producto.getNome());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String obtenerFechaHoraActual() {
        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();

        // Formatear la fecha y hora en el formato deseado: "YYYY-MM-DD HH:MM:SS"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private void agregarProducto(String nombreProducto, int cantidad) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            Producto producto = productoDAO.getProductoByNombre(nombreProducto);
            DefaultTableModel model = (DefaultTableModel) tablaContenidoPedido.getModel();
            model.addRow(new Object[]{nombreProducto, cantidad, producto.getPrezo(), producto.getDesconto(), producto.getIva()});

            // Actualizar el stock en la lista de productos agregados
            producto.setStock(producto.getStock() - cantidad);
            productosAgregados.add(producto);

            calcularTotalPedido(); // Recalcular el total
        } catch (SQLException ex) {
            Logger.getLogger(PedidoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void restaurarStockEnBaseDeDatos() {
        ProductoDAO productoDAO = new ProductoDAO();
        for (Producto producto : productosAgregados) {
            productoDAO.actualizarProducto(producto.getId(), "stock", String.valueOf(producto.getStock()));
        }
    }

    private int obtenerStockProductoSeleccionado() {
        String selectedProduct = (String) comboProductos.getSelectedItem();
        ProductoDAO productoDAO = new ProductoDAO();

        try {
            int stock = productoDAO.getStockByProductName(selectedProduct);
            return stock;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private void actualizarStockProductoSeleccionado(int nuevoStock) {
        String selectedProduct = (String) comboProductos.getSelectedItem();
        stockProducto.setText(String.valueOf(nuevoStock));

        for (Producto producto : productosAgregados) {
            if (producto.getNome().equals(selectedProduct)) {
                producto.setStock(nuevoStock);
                break;
            }
        }
    }

    private void setupTimer() {
        int delay = 5000; // 5 segundos
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mensajeStock.setText("");
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.setRepeats(false); // Se ejecuta solo una vez
    }

    private void mostrarMensajeStock(String mensaje) {
        mensajeStock.setForeground(Color.RED);
        mensajeStock.setText(mensaje);
        timer.restart(); // Reinicia el temporizador
    }

    private void calcularTotalPedido() {
    DefaultTableModel model = (DefaultTableModel) tablaContenidoPedido.getModel();
    double subtotal = 0.0;
    double ivaTotal = 0.0;

    for (int i = 0; i < model.getRowCount(); i++) {
        int cantidad = (int) model.getValueAt(i, 1);
        double precio = (double) model.getValueAt(i, 2);
        int descuento = (int) model.getValueAt(i, 3);
        double ivaProducto = (double) model.getValueAt(i, 4);
        double precioConDescuento = calcularPrecioConDescuento(precio, descuento);
        subtotal += cantidad * precioConDescuento;
        ivaTotal += (cantidad * precioConDescuento) * (ivaProducto / 100.0);
    }

    double total = subtotal + ivaTotal;

    subTotalEtiqueta.setText(String.valueOf(subtotal));
    etiquetaIvaTotal.setText(String.valueOf(ivaTotal));
    totalPedidoLabel.setText(String.valueOf(total));
}

private void agregarProducto(String nombreProducto, int cantidad, int descuento) {
    ProductoDAO productoDAO = new ProductoDAO();
    try {
        Producto producto = productoDAO.getProductoByNombre(nombreProducto);
        double precioConDescuento = calcularPrecioConDescuento(producto.getPrezo(), descuento);
        Iva ivaProducto = producto.getIva();
        double ivaAsignado = 0.0;

        if (ivaProducto == Iva.TIPO_REDUCIDO) {
            ivaAsignado = 10.0 / 100.0;
        } else if (ivaProducto == Iva.TIPO_XERAL) {
            ivaAsignado = 21.0 / 100.0;
        } else if (ivaProducto == Iva.TIPO_SUPERREDUCIDO) {
            ivaAsignado = 4.0 / 100.0;
        }
        DefaultTableModel model = (DefaultTableModel) tablaContenidoPedido.getModel();
        model.addRow(new Object[]{nombreProducto, cantidad, producto.getPrezo(), descuento, ivaAsignado, precioConDescuento});

        // Actualizar el stock en la lista de productos agregados
        producto.setStock(producto.getStock() - cantidad);
        productosAgregados.add(producto);

        calcularTotalPedido(); // Recalcular el total
    } catch (SQLException ex) {
        Logger.getLogger(PedidoPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private double calcularPrecioConDescuento(double precio, int descuento) {
    double descuentoPorcentaje = descuento / 100.0;
    return precio - (precio * descuentoPorcentaje);
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner SliderCantidadUnidades;
    private javax.swing.JButton botonAgregarProducto;
    private javax.swing.JButton botonCancelarPedido;
    private javax.swing.JButton botonEliminarProducto;
    private javax.swing.JButton botonHacerPedido;
    private javax.swing.JSpinner cantidadDescuento;
    private javax.swing.JComboBox<String> comboProductos;
    private javax.swing.JLabel etiquetaIvaTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mensajeStock;
    private javax.swing.JLabel stockProducto;
    private javax.swing.JLabel subTotalEtiqueta;
    private javax.swing.JTable tablaContenidoPedido;
    private javax.swing.JLabel totalPedidoLabel;
    // End of variables declaration//GEN-END:variables
}