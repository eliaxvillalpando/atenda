package atenda.admin.vistas;


import atenda.admin.negocio.Producto;
import atenda.admin.negocio.ProductoDAO;
import java.awt.Color;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AltaProductoJFrame extends javax.swing.JFrame {
    
     private ProductosPanel productosPanel;

    public AltaProductoJFrame(ProductosPanel productosPanel) {
        initComponents();
        this.productosPanel = productosPanel;
        //cerrar ventana sin cerrar el programa
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nomeProductoField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                limpiarMensaje();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limpiarMensaje();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limpiarMensaje();
            }

            private void limpiarMensaje() {
                mensajeProductoNuevo.setText("");
                nomeProductoField.setBackground(Color.WHITE);
                costoProductoField.setBackground(Color.WHITE);
                precioVentaProductoField.setBackground(Color.WHITE);
                stockProductoField.setBackground(Color.WHITE);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nomeProductoField = new javax.swing.JTextField();
        costoProductoField = new javax.swing.JSpinner();
        precioVentaProductoField = new javax.swing.JSpinner();
        descontoProductoField = new javax.swing.JSpinner();
        ivaProductoField = new javax.swing.JSpinner();
        botonLimpiarAltaProducto = new javax.swing.JButton();
        botonCrearProducto = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        stockProductoField = new javax.swing.JSpinner();
        mensajeProductoNuevo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Alta de novo produto");

        jLabel2.setText("Nome:");

        jLabel3.setText("Prezo de coste:");

        jLabel4.setText("Prezo de venta:");

        jLabel5.setText("Desconto:");

        jLabel6.setText("IVA");

        costoProductoField.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        precioVentaProductoField.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        botonLimpiarAltaProducto.setText("Limpar");
        botonLimpiarAltaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonLimpiarAltaProductoMouseClicked(evt);
            }
        });

        botonCrearProducto.setText("Crear");
        botonCrearProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonCrearProductoMouseClicked(evt);
            }
        });

        jLabel7.setText("Stock");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonLimpiarAltaProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonCrearProducto)
                        .addGap(239, 239, 239))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mensajeProductoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 57, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomeProductoField)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ivaProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(costoProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(precioVentaProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(descontoProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(55, 55, 55))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stockProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(mensajeProductoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(147, 147, 147)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(ivaProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nomeProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(costoProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(precioVentaProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(descontoProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(stockProductoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCrearProducto)
                    .addComponent(botonLimpiarAltaProducto))
                .addGap(99, 99, 99))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCrearProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCrearProductoMouseClicked
        //Crear un nuevo producto
        // Verificar si alguno de los campos requeridos está vacío
        boolean faltanDatos = false;
        if (nomeProductoField.getText().isEmpty()) {
            nomeProductoField.setBackground(Color.RED);
            faltanDatos = true;
        }
        if ((Double) costoProductoField.getValue() == 0) {
            costoProductoField.setBackground(Color.RED);
            faltanDatos = true;
        }
        if ((Double) precioVentaProductoField.getValue() == 0) {
            precioVentaProductoField.setBackground(Color.RED);
            faltanDatos = true;
        }
        if ((Integer) stockProductoField.getValue() == 0) {
            stockProductoField.setBackground(Color.RED);
            faltanDatos = true;
        }

        // Si faltan datos, mostrar el mensaje y salir
        if (faltanDatos) {
            mensajeProductoNuevo.setText("Faltan datos");
            return;
        }

        // Crear un nuevo Producto con los datos ingresados en los campos
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNome(nomeProductoField.getText());
        nuevoProducto.setCoste((Double) costoProductoField.getValue());
        nuevoProducto.setPrezo((Double) precioVentaProductoField.getValue());
        nuevoProducto.setDesconto((Integer) descontoProductoField.getValue());
        nuevoProducto.setStock((Integer) stockProductoField.getValue());
        nuevoProducto.setIva((Integer) ivaProductoField.getValue());
        //nuevoProducto.setIva(Iva.fromInt((Integer) ivaProductoField.getValue())); // Necesita implementar Iva.fromInt

        nuevoProducto.setBaixa(false); // Asumiendo que baixa inicial es false

        // Crear una nueva instancia de ProductoDAO y guardar el nuevo Producto
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.save(nuevoProducto);
            System.out.println("Producto guardado" + nuevoProducto.toString());
            // Limpiar los campos para el próximo ingreso
            nomeProductoField.setText("");
            costoProductoField.setValue(0);
            precioVentaProductoField.setValue(0);
            descontoProductoField.setValue(0);
            ivaProductoField.setValue(0);
            stockProductoField.setValue(0);
            mensajeProductoNuevo.setText("Producto agregado correctamente");
            startTimer();
            productosPanel.actualizarTablaDesdeOtroPanel();
            
        } catch (Exception e) {
            // En caso de error, mostrar el mensaje de error
            mensajeProductoNuevo.setText("Error al crear producto");
            startTimer();
        }

    }//GEN-LAST:event_botonCrearProductoMouseClicked

    private void botonLimpiarAltaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonLimpiarAltaProductoMouseClicked
        // TODO add your handling code here:
        nomeProductoField.setText("");
        costoProductoField.setValue(0);
        precioVentaProductoField.setValue(0);
        descontoProductoField.setValue(0);
        ivaProductoField.setValue(0);
        stockProductoField.setValue(0);
    }//GEN-LAST:event_botonLimpiarAltaProductoMouseClicked

    /**
     * @param args the command line arguments
     */
    private void startTimer() {
        Timer timer = new Timer(5000, e -> mensajeProductoNuevo.setText(""));
        timer.setRepeats(false);
        timer.start();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCrearProducto;
    private javax.swing.JButton botonLimpiarAltaProducto;
    private javax.swing.JSpinner costoProductoField;
    private javax.swing.JSpinner descontoProductoField;
    private javax.swing.JSpinner ivaProductoField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel mensajeProductoNuevo;
    private javax.swing.JTextField nomeProductoField;
    private javax.swing.JSpinner precioVentaProductoField;
    private javax.swing.JSpinner stockProductoField;
    // End of variables declaration//GEN-END:variables
}
