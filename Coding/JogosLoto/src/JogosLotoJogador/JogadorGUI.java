/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoJogador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author bil
 */
public final class JogadorGUI extends javax.swing.JFrame implements JogoLoto {


    public final ArrayList<ArrayList<JLabelCartao >> LinhasDeLabel;
    public Cartao cartao;
    public boolean jogoIniciado;
    public Tema tema;
    public JogadorGUI() {
        super();
        
         this.tema =  new Tema(Temas.values()[Cartao.randomNum(0, 2)]);
        initComponents();
        this.jogoIniciado = false;
        this.LinhasDeLabel = new ArrayList<>();
        this.cartao = new Cartao(JogadorGUI.COLUNAS_DIM,JogadorGUI.LINHAS_DIM,JogadorGUI.QTD_NUMEROS_DIM);
        
        construirCartao();
    }
    
    @Override
    public void construirCartao(){
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();
        
        while ( iteradorArrayList.hasNext() ) 
        {
            ArrayList<JLabelCartao > colunaJLabel = new ArrayList<>();
            
            HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();
            List<Integer> sortedKeys=new ArrayList(coluna.keySet());
            Collections.sort(sortedKeys);
            for (int i : sortedKeys) {
                JLabelCartao customLabel = new JLabelCartao(coluna.get(i), tema.TEMA);
                colunaJLabel.add(customLabel);
                jPanelCartaoContent.add(customLabel);
            }
            LinhasDeLabel.add(colunaJLabel);

        }
        this.pack();
        
        
        
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

        jPanelOpcoes = new javax.swing.JPanel();
        jButtonSortearNumeo = new javax.swing.JButton();
        jLabelJogoStatus = new javax.swing.JLabel();
        jButtonNovoCartao = new javax.swing.JButton();
        jlabelTips = new javax.swing.JLabel();
        jPanelCartaoContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogo de Loto");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelOpcoes.setBackground(this.tema.PANEL_OPCOES_BACKGROUND);
        jPanelOpcoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        jPanelOpcoes.setLayout(new java.awt.GridBagLayout());

        jButtonSortearNumeo.setText("Introduzir Número Sorteado");
        jButtonSortearNumeo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSortearNumeo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSortearNumeoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 0, 30);
        jPanelOpcoes.add(jButtonSortearNumeo, gridBagConstraints);

        jLabelJogoStatus.setText("< Jogo Não Inicializado >");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 8, 0);
        jPanelOpcoes.add(jLabelJogoStatus, gridBagConstraints);

        jButtonNovoCartao.setText("Novo Cartão ");
        jButtonNovoCartao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonNovoCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoCartaoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 0, 30);
        jPanelOpcoes.add(jButtonNovoCartao, gridBagConstraints);

        jlabelTips.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jlabelTips.setText("*Para editar um número clique nele e escolha o novo valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        jPanelOpcoes.add(jlabelTips, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        getContentPane().add(jPanelOpcoes, gridBagConstraints);

        jPanelCartaoContent.setBackground(this.tema.PANEL_CONTENT_BACKGROUND);
        jPanelCartaoContent.setLayout(new java.awt.GridLayout(3, 9, 2, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jPanelCartaoContent, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSortearNumeoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSortearNumeoActionPerformed

        if(!jogoIniciado){   

            Iterator<ArrayList<JLabelCartao >> iteradorArrayListJLabel = LinhasDeLabel.iterator();
            Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = this.cartao.getLinhasArrayList().iterator();
            
            while ( iteradorArrayListJLabel.hasNext() &&  iteradorArrayList.hasNext()  ){
                ArrayList<JLabelCartao> colunaJLabel = iteradorArrayListJLabel.next();
                HashMap<Integer,Slot_Numero > colunaCartao = iteradorArrayList.next();
                colunaCartao.clear();

                for(JLabelCartao jlabelcartao : colunaJLabel){
                    int i = colunaJLabel.indexOf(jlabelcartao);
                    int min = Cartao.getColumnMin(i);
                    int max =  Cartao.getColumnMax(i, COLUNAS_DIM);
                    if(jlabelcartao.getSlot_numero() != null){
                        if(jlabelcartao.getSlot_numero().getNumero() < min || jlabelcartao.getSlot_numero().getNumero() > max){
                            JOptionPane.showMessageDialog(this,"O cartão não é válido, por favor verifique as posições!","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        colunaCartao.put(jlabelcartao.getSlot_numero().getNumero(),jlabelcartao.getSlot_numero());

                    }
                    else
                        colunaCartao.put( Cartao.randomNum(Cartao.getColumnMin(i), Cartao.getColumnMax(i, COLUNAS_DIM)), null);
                }
            }
            if(!cartao.verificar_integridade()){
                JOptionPane.showMessageDialog(this,"O cartão não é válido, por favor verifique as posições!","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }else{
                this.jogoIniciado = true;
                iteradorArrayListJLabel = LinhasDeLabel.iterator();

                while ( iteradorArrayListJLabel.hasNext()  ){
                    ArrayList<JLabelCartao> colunaJLabel = iteradorArrayListJLabel.next();
                    for(JLabelCartao jlabelcartao : colunaJLabel)
                        jlabelcartao.setJogoIniciado(true);
                }
                jlabelTips.setVisible(false);
                jLabelJogoStatus.setText("<Jogo Iniciado>");
            }
        }


       if(jogoIniciado){
           String resultado = (String)JOptionPane.showInputDialog( "Introduza o número sorteado!");
           if(resultado == null)
               return;

           try {
               Integer.parseInt(resultado);
               if(Integer.valueOf( resultado) <Cartao.getColumnMin(0) || Integer.valueOf( resultado) > cartao.getColumnMax(JogadorGUI.COLUNAS_DIM)){
                       JOptionPane.showMessageDialog(this,"Introduza um número inteiro entre "+ Cartao.getColumnMin(0) +  " e "+ cartao.getColumnMax(JogadorGUI.COLUNAS_DIM),"Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                       return;
               }
               HashMap<Integer,Slot_Numero > colunaNumeroMarcado  = this.cartao.MarcarNumeroSorteado(Integer.valueOf( resultado));
               if(colunaNumeroMarcado != null){

                   for(JLabelCartao jlabelcartao : this.LinhasDeLabel.get(this.cartao.getLinhasArrayList().indexOf(colunaNumeroMarcado)))
                       if(jlabelcartao.getSlot_numero()!= null)
                           if(jlabelcartao.getSlot_numero()!= null)
                               if(jlabelcartao.getSlot_numero().getNumero() == Integer.valueOf(resultado))
                                       jlabelcartao.marcarJLabel();
                   jLabelJogoStatus.setText("< O Número " + resultado + " foi marcado no seu cartão! >");

               }
               else
                   jLabelJogoStatus.setText("< O Número " + resultado + " não existe no seu cartão! >");

           } catch (NumberFormatException e) {
               JOptionPane.showMessageDialog(this,"Introduza um número inteiro entre 0 e 90","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
           }

       }
        
                
    }//GEN-LAST:event_jButtonSortearNumeoActionPerformed

    private void jButtonNovoCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoCartaoActionPerformed
        // TODO add your handling code here:
        if(jogoIniciado){
            int reply = JOptionPane.showConfirmDialog(null, "Tens um jogo em progresso.\nSe continuar esta ação irá perder o seu progresso do jogo anterior, deseja prosseguir?", "Jogo em progresso!", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) 
                return;
        }
         this.tema =  new Tema(Temas.values()[Cartao.randomNum(0, 2)]);
         jPanelCartaoContent.setBackground(this.tema.PANEL_CONTENT_BACKGROUND);
         jPanelOpcoes.setBackground(this.tema.PANEL_OPCOES_BACKGROUND);

 
        jPanelCartaoContent.removeAll();
        jLabelJogoStatus.setText("< Jogo Não Inicializado >");
        jlabelTips.setVisible(true);
        jPanelCartaoContent.updateUI();
        this.jogoIniciado = false;
        this.LinhasDeLabel.clear();
        this.cartao = new Cartao(JogadorGUI.COLUNAS_DIM,JogadorGUI.LINHAS_DIM,JogadorGUI.QTD_NUMEROS_DIM);
        this.construirCartao();
    }//GEN-LAST:event_jButtonNovoCartaoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        System.out.println(javax.swing.UIManager.getInstalledLookAndFeels());
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JogadorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JogadorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JogadorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JogadorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JogadorGUI().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNovoCartao;
    private javax.swing.JButton jButtonSortearNumeo;
    private javax.swing.JLabel jLabelJogoStatus;
    private javax.swing.JPanel jPanelCartaoContent;
    private javax.swing.JPanel jPanelOpcoes;
    private javax.swing.JLabel jlabelTips;
    // End of variables declaration//GEN-END:variables
}
