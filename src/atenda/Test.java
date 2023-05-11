package atenda;

import javax.swing.JFrame;

public class Test extends javax.swing.JPanel {

    /**
     * Creates new form Test
     */
    public Test() {
        initComponents();
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }

    private static void createAndShowGUI() {
        // Create and set up the window
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        Test testPanel = new Test();
        testPanel.setOpaque(true); // content panes must be opaque
        frame.setContentPane(testPanel);

        // Display the window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Test window");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("NVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKENVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKENVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKENVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\n\n\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKENVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKENVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE\nNVRNWVWNOEWINVOEWINVOIWENOIJCEKWFJKE");
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Abrir Pagina");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jButton1)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TestJPanel jPanel = new TestJPanel();
        // Get the top-level container (usually a JFrame or JDialog)
        java.awt.Container topLevelContainer = this.getTopLevelAncestor();

        // Check if the top-level container is a JFrame
        if (topLevelContainer instanceof JFrame) {
            // Cast the top-level container to JFrame and replace its content pane
            JFrame frame = (JFrame) topLevelContainer;
            frame.setContentPane(jPanel);
            frame.pack();
            frame.revalidate();
            frame.repaint();
        } else {
            // Handle other cases, e.g., if the top-level container is a JDialog
            // or if you want to show an error message or a fallback behavior
        }

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
