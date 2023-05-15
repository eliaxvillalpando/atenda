package atenda.vista;

import atenda.modelo.Iva;
import atenda.controlador.ModeloDAO;
import atenda.modelo.Producto;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.Timer;

public class ProductosPanel extends javax.swing.JPanel {

    private ModeloDAO productoDAO;
    private TableRowSorter<TableModel> sorter;
    private Timer mensajeTimer;

    /**
     * Creates new form Productos
     */
    public ProductosPanel() {
        initComponents();
        inicializarMensajeTimer();
        productoDAO = new ModeloDAO();
        actualizarTabla();
        configurarBusqueda();
        botonActualizar.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        buscarField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductosAdmin = new javax.swing.JTable();
        botonEliminarProducto = new javax.swing.JButton();
        botonNuevoProducto = new javax.swing.JButton();
        mensajeProductoAdminLabel = new javax.swing.JLabel();
        botonActualizar = new javax.swing.JButton();
        mensajeProductosPanelAdmin = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("Buscar");

        buscarField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarFieldActionPerformed(evt);
            }
        });

        tablaProductosAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Coste", "Prezo", "Desconto", "IVA", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductosAdmin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaProductosAdminPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductosAdmin);

        botonEliminarProducto.setText("Borra");
        botonEliminarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEliminarProductoMouseClicked(evt);
            }
        });
        botonEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarProductoActionPerformed(evt);
            }
        });

        botonNuevoProducto.setText("Novo");
        botonNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoProductoActionPerformed(evt);
            }
        });

        botonActualizar.setText("Gardar");
        botonActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonActualizarMouseClicked(evt);
            }
        });

        jLabel2.setText("%");

        jLabel3.setText("%");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonEliminarProducto)
                        .addGap(18, 18, 18)
                        .addComponent(botonNuevoProducto)
                        .addGap(18, 18, 18)
                        .addComponent(botonActualizar)
                        .addGap(63, 63, 63)
                        .addComponent(mensajeProductoAdminLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(474, 474, 474)
                        .addComponent(mensajeProductosPanelAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(buscarField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(buscarField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mensajeProductosPanelAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mensajeProductoAdminLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botonEliminarProducto)
                        .addComponent(botonNuevoProducto)
                        .addComponent(botonActualizar)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buscarFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarFieldActionPerformed
        // TODO add your handling code here:
        String textoABuscar = buscarField.getText();
        if (textoABuscar.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoABuscar, 1)); // Filtrar por la columna "nome" (índice 1)
        }


    }//GEN-LAST:event_buscarFieldActionPerformed

    private void botonEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonEliminarProductoActionPerformed

    private void botonNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoProductoActionPerformed
        // TODO add your handling code here:

        AltaProductoJFrame altaProducto = new AltaProductoJFrame(this);
        altaProducto.setVisible(true);


    }//GEN-LAST:event_botonNuevoProductoActionPerformed

    private void tablaProductosAdminPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaProductosAdminPropertyChange
        // TODO add your handling code here:
        if (evt.getPropertyName().equals("tableCellEditor")) {
            botonActualizar.setEnabled(true);

        }

    }//GEN-LAST:event_tablaProductosAdminPropertyChange

    private void botonActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonActualizarMouseClicked
        // TODO add your handling code here:
        int fila = tablaProductosAdmin.getSelectedRow();
        int columna = tablaProductosAdmin.getSelectedColumn();

        if (fila != -1 && columna != -1) {
            DefaultTableModel model = (DefaultTableModel) tablaProductosAdmin.getModel();
            Object valor = model.getValueAt(fila, columna);

            if (valor != null) {
                int id = (int) model.getValueAt(fila, 0);
                String nombreColumna = tablaProductosAdmin.getColumnName(columna);
                String valorActual = valor.toString();

                ModeloDAO productoDAO = new ModeloDAO();
                boolean actualizacionExitosa = productoDAO.actualizarProducto(id, nombreColumna, valorActual);

                if (actualizacionExitosa) {
                    mensajeProductoAdminLabel.setText("Productos actualizados");
                    actualizarTabla();
                    mensajeTimer.restart();
                } else {
                    mensajeProductoAdminLabel.setText("Error al actualizar");
                    mensajeTimer.restart();
                }
            }
        }


    }//GEN-LAST:event_botonActualizarMouseClicked

    private void botonEliminarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarProductoMouseClicked
        // TODO add your handling code here:
        int fila = tablaProductosAdmin.getSelectedRow();

        if (fila != -1) {
            DefaultTableModel model = (DefaultTableModel) tablaProductosAdmin.getModel();
            int id = (int) model.getValueAt(fila, 0);

            Producto producto = new Producto();
            producto.setId(id);

            ModeloDAO productoDAO = new ModeloDAO();
            boolean eliminacionExitosa = productoDAO.eliminarProducto(producto);

            if (eliminacionExitosa) {
                mensajeProductoAdminLabel.setText("Producto eliminado");
                model.removeRow(fila); // Elimina la fila de la tabla
            } else {
                mensajeProductoAdminLabel.setText("Producto no fue posible eliminar");
            }

            // Restarte el timer para que el mensaje desaparezca después de un tiempo
            mensajeTimer.restart();
        }
    }//GEN-LAST:event_botonEliminarProductoMouseClicked

    private void actualizarTabla() {
        ModeloDAO productoDAO = new ModeloDAO();

    try {
        List<Producto> productos = productoDAO.getAllProductos();
        DefaultTableModel model = (DefaultTableModel) tablaProductosAdmin.getModel();
        model.setRowCount(0); // Limpia la tabla antes de rellenarla

        for (Producto producto : productos) {
            double ivaAsignado = 0.0;

            if (producto.getIva() == Iva.TIPO_REDUCIDO) {
                ivaAsignado = 10.0 / 100.0;
            } else if (producto.getIva() == Iva.TIPO_XERAL) {
                ivaAsignado = 21.0 / 100.0;
            } else if (producto.getIva() == Iva.TIPO_SUPERREDUCIDO) {
                ivaAsignado = 4.0 / 100.0;
            }
            Object[] row = new Object[]{
                producto.getId(),
                producto.getNome(),
                producto.getCoste(),
                producto.getPrezo(),
                producto.getDesconto(),
                ivaAsignado,
                producto.getStock()
            };
            model.addRow(row);
        }
    } catch (SQLException e) {
        // Mostrar el error en el mensajeProductoAdminLabel
        mensajeProductoAdminLabel.setText("Error al actualizar la tabla de productos");
    }
    }

    public void actualizarTablaDesdeOtroPanel() {
        actualizarTabla();
    }

    private void configurarBusqueda() {
        DefaultTableModel model = (DefaultTableModel) tablaProductosAdmin.getModel();
        sorter = new TableRowSorter<>(model);
        tablaProductosAdmin.setRowSorter(sorter);
    }

    private void inicializarMensajeTimer() {
        mensajeTimer = new Timer(5000, (ActionEvent e) -> {
            mensajeProductoAdminLabel.setText("");
        });
        mensajeTimer.setRepeats(false); // Para que el Timer se ejecute una sola vez
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonActualizar;
    private javax.swing.JButton botonEliminarProducto;
    private javax.swing.JButton botonNuevoProducto;
    private javax.swing.JTextField buscarField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mensajeProductoAdminLabel;
    private javax.swing.JLabel mensajeProductosPanelAdmin;
    private javax.swing.JTable tablaProductosAdmin;
    // End of variables declaration//GEN-END:variables
}
