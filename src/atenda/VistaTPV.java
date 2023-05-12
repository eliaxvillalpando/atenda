/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atenda;

import atenda.vistas.usuario.DevolucionPanel;
import atenda.vistas.usuario.InicioPanel;
import atenda.vistas.usuario.PedidoPanel;
import atenda.vistas.usuario.UsuarioPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author elias
 */
public class VistaTPV extends javax.swing.JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private PedidoPanel pedidosPanel = new PedidoPanel();
    private InicioPanel inicioPanel = new InicioPanel();
    private DevolucionPanel devolucionPanel = new DevolucionPanel();
    private UsuarioPanel usuarioPanel = new UsuarioPanel();

    /**
     * Creates new form VistaTPV
     */
    public VistaTPV() {
        initComponents();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(cardPanel, BorderLayout.CENTER);

        /**
         * productosPanel = new ProductosPanel(); cardPanel.add(productosPanel,
         * "Productos")
         */
        pedidosPanel = new PedidoPanel();
        cardPanel.add(pedidosPanel, "Pedidos");
        inicioPanel = new InicioPanel();
        cardPanel.add(inicioPanel, "Inicio");
        devolucionPanel = new DevolucionPanel();
        cardPanel.add(devolucionPanel, "Devolucion");
        usuarioPanel = new UsuarioPanel();
        cardPanel.add(usuarioPanel, "Usuario");

        // NoPanel es un JPanel vacío para ocultar todos los paneles
        JPanel noPanel = new JPanel();
        cardPanel.add(noPanel, "NoPanel");

        // Establecer la visualización inicial del panel
        cardLayout.show(cardPanel, "NoPanel");

    }

    private void mostrarPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuInicio = new javax.swing.JMenu();
        menuPedido = new javax.swing.JMenu();
        menuDevolucion = new javax.swing.JMenu();
        menuUsuario = new javax.swing.JMenu();
        menuSalir = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        menuInicio.setText("Inicio");
        menuInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuInicioMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuInicio);

        menuPedido.setText("Pedido");
        menuPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPedidoMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuPedido);

        menuDevolucion.setText("Devolucion");
        menuDevolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDevolucionMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuDevolucion);

        menuUsuario.setText("Usuario");
        menuUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuUsuarioMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuUsuario);

        menuSalir.setText("Salir");
        menuSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSalirMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuSalir);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuInicioMouseClicked
        // TODO add your handling code here:

        System.out.println("Boton presionado correctamente");
        mostrarPanel("Inicio");

    }//GEN-LAST:event_menuInicioMouseClicked

    private void menuPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPedidoMouseClicked
        // TODO add your handling code here:
        System.out.println("Boton presionado correctamente");
        mostrarPanel("Pedidos");
    }//GEN-LAST:event_menuPedidoMouseClicked

    private void menuDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDevolucionMouseClicked
        // TODO add your handling code here:
        System.out.println("Boton presionado correctamente");
        mostrarPanel("Devolucion");
    }//GEN-LAST:event_menuDevolucionMouseClicked

    private void menuUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuUsuarioMouseClicked
        // TODO add your handling code here:
        System.out.println("Boton presionado correctamente");
        mostrarPanel("Usuario");
    }//GEN-LAST:event_menuUsuarioMouseClicked

    private void menuSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSalirMouseClicked
        // TODO add your handling code here:
        new VistaLogin().setVisible(true);
        VistaTPV.this.setVisible(false);    
    }//GEN-LAST:event_menuSalirMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaTPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaTPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaTPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaTPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaTPV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu menuDevolucion;
    private javax.swing.JMenu menuInicio;
    private javax.swing.JMenu menuPedido;
    private javax.swing.JMenu menuSalir;
    private javax.swing.JMenu menuUsuario;
    // End of variables declaration//GEN-END:variables
}
