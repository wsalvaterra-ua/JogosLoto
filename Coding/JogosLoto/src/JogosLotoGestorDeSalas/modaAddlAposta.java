/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import java.math.BigInteger;
import javax.swing.JOptionPane;

/**
 *
 * @author bil
 */
public class modaAddlAposta extends javax.swing.JDialog {
    public String[] data;
    /**
     * Creates new form addApostaModal
     */
    public modaAddlAposta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.data = new String[2];
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        jTextIdentificacao = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jValor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicionar Aposta");
        setBackground(new java.awt.Color(51, 0, 204));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setBackground(new java.awt.Color(147, 54, 220));
        mainPanel.setToolTipText("");
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(147, 54, 220));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        mainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 300, -1));

        jPanel3.setBackground(new java.awt.Color(147, 54, 220));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel3.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, -1));

        btnOk.setText("Confirmar");
        btnOk.setHideActionText(true);
        btnOk.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnOk.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        jPanel3.add(btnOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        mainPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 210, 30));
        mainPanel.add(jTextIdentificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 260, -1));

        jLabel1.setText("Indroduza o número de identificação do Jogador:");
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 290, 31));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("*Identificação deve ser um número natural menor que 2.147.483.647");
        mainPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, -1));
        mainPanel.add(jValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 260, -1));

        jLabel2.setText("Introduza o valor apostado pelo Jogador");
        mainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 272, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("*Valor deve ser um número maior que 0");
        mainPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 190));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        boolean validadeID = false;
        boolean validadeValor = false;
        
        
        try {
            Double.parseDouble(jValor.getText());
            if(Double.valueOf(jValor.getText()) >0)
                validadeValor = true;
        } catch (NumberFormatException e) {
            validadeValor = false;
        }
        try {
            Integer.parseInt(jTextIdentificacao.getText());
            if(Integer.valueOf(jTextIdentificacao.getText()) >0)
                validadeID = true;
        } catch (NumberFormatException e) {
            validadeID = false;
        }
        if(!validadeID && !validadeValor)
            JOptionPane.showMessageDialog(this,"Os dados introduzidos não são válidos","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE); 
        else if(!validadeID)
            JOptionPane.showMessageDialog(this,"Verifique o valor introduzido no campo de identificação!","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
        else if(!validadeValor)
            JOptionPane.showMessageDialog(this,"Verifique o valor introduzido no campo valor!","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);  
       
        else if(validadeID && validadeValor ){
            data[0] = jTextIdentificacao.getText();
            data[1] = jValor.getText();
            dispose();
        }
         
     
     
     
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        
            data[0] = null;
            dispose();
        
        
    }//GEN-LAST:event_btnCancelActionPerformed


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
            java.util.logging.Logger.getLogger(modaAddlAposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modaAddlAposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modaAddlAposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modaAddlAposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                modaAddlAposta dialog = new modaAddlAposta(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextIdentificacao;
    private javax.swing.JTextField jValor;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}