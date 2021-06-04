/**
 *  JogosLotoGestorDeSalas Package com classes para criar um gestor de jogos de Loto
 */
package JogosLotoGestorDeSalas;


import JogosLotoJogador.Cartao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *Esta classe cria uma Interface gráfica de um gestor de Salas .
 * 
 * @author William Salvaterra e Rui Oliveira
 */
public class GSalaGUI extends javax.swing.JFrame{
    private final int MIN = 1;
    private final int MAX = 90;
    private DefaultListModel modelNumerosSorteados;   
    private DefaultListModel modelApostas;   
    private SessaoDeJogoDeLoto sessaoDeJogo;
    private Server serversocket;

/**
 * Método construtor da classe que inicia a Interface gráfica do jogo
 * 
 */
    public GSalaGUI() {
        
        initComponents();
        modelNumerosSorteados = new DefaultListModel();
        modelApostas = new DefaultListModel();
        jListTrueNumerosSorteados.setModel(modelNumerosSorteados);
        jListTrueApostas.setModel(modelApostas);
        sessaoDeJogo = new SessaoDeJogoDeLoto(MIN,MAX);

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

        jPanel1 = new javax.swing.JPanel();
        jListNumerosSorteados = new javax.swing.JScrollPane();
        jListTrueNumerosSorteados = new javax.swing.JList<>();
        jButtonTerminarJogo = new javax.swing.JButton();
        jLabelNumerosSorteados = new javax.swing.JLabel();
        jPanelHead = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanelApostas = new javax.swing.JPanel();
        jListApostas = new javax.swing.JScrollPane();
        jListTrueApostas = new javax.swing.JList<>();
        jButtonIniciarJogo = new javax.swing.JButton();
        jLabelApostas = new javax.swing.JLabel();
        jPanelAtualNumeroSorteado = new javax.swing.JPanel();
        jButtonatuallNumeroSorteado = new javax.swing.JButton();
        jLabelatualNumeroSorteado = new javax.swing.JLabel();
        jLabelBigLabelatualNumeroSorteado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogos De Loto - Gestor de Sala");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));
        setSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 0));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jListTrueNumerosSorteados.setBackground(new java.awt.Color(176, 176, 0));
        jListTrueNumerosSorteados.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jListTrueNumerosSorteados.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jListTrueNumerosSorteados.setForeground(new java.awt.Color(0, 1, 0));
        jListTrueNumerosSorteados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListTrueNumerosSorteados.setFocusable(false);
        jListNumerosSorteados.setViewportView(jListTrueNumerosSorteados);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 1, 4);
        jPanel1.add(jListNumerosSorteados, gridBagConstraints);

        jButtonTerminarJogo.setText("Cancelar Jogo");
        jButtonTerminarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonTerminarJogo.setEnabled(false);
        jButtonTerminarJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTerminarJogoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(jButtonTerminarJogo, gridBagConstraints);

        jLabelNumerosSorteados.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNumerosSorteados.setForeground(new java.awt.Color(0, 1, 0));
        jLabelNumerosSorteados.setText("Números Sorteados:");
        jLabelNumerosSorteados.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelNumerosSorteados.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel1.add(jLabelNumerosSorteados, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanelHead.setBackground(new java.awt.Color(51, 0, 204));
        jPanelHead.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelHead.setLayout(new java.awt.GridBagLayout());

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 1, 0));
        jLabelTitulo.setText("Jogo De Loto - Gestor de Sala");
        jPanelHead.add(jLabelTitulo, new java.awt.GridBagConstraints());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 1, 0));
        jLabel2.setText("Sistema de Recompensa para Apostas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 0, 0);
        jPanelHead.add(jLabel2, gridBagConstraints);

        jLabel3.setForeground(new java.awt.Color(0, 1, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("apostaFeita + SomaDe_apostas_nao_vencidas / (nº_de_Vencedores + 1)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 0, 0);
        jPanelHead.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(jPanelHead, gridBagConstraints);

        jPanelApostas.setBackground(new java.awt.Color(0, 153, 102));
        jPanelApostas.setForeground(new java.awt.Color(0, 2, 0));
        jPanelApostas.setLayout(new java.awt.GridBagLayout());

        jListTrueApostas.setBackground(new java.awt.Color(1, 131, 88));
        jListTrueApostas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jListTrueApostas.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jListTrueApostas.setForeground(new java.awt.Color(0, 1, 0));
        jListTrueApostas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListTrueApostas.setToolTipText("");
        jListTrueApostas.setFocusable(false);
        jListApostas.setViewportView(jListTrueApostas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 119;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 1, 2);
        jPanelApostas.add(jListApostas, gridBagConstraints);

        jButtonIniciarJogo.setText("Hospedar Jogo");
        jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonIniciarJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIniciarJogoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        jPanelApostas.add(jButtonIniciarJogo, gridBagConstraints);

        jLabelApostas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelApostas.setForeground(new java.awt.Color(0, 1, 0));
        jLabelApostas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelApostas.setText("Apostas:");
        jLabelApostas.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 1, 0);
        jPanelApostas.add(jLabelApostas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jPanelApostas, gridBagConstraints);

        jPanelAtualNumeroSorteado.setBackground(new java.awt.Color(0, 51, 255));
        jPanelAtualNumeroSorteado.setForeground(new java.awt.Color(0, 1, 0));
        jPanelAtualNumeroSorteado.setLayout(new java.awt.GridBagLayout());

        jButtonatuallNumeroSorteado.setText("Sortear Número");
        jButtonatuallNumeroSorteado.setBorder(null);
        jButtonatuallNumeroSorteado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonatuallNumeroSorteado.setEnabled(false);
        jButtonatuallNumeroSorteado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonatuallNumeroSorteadoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(25, 0, 0, 0);
        jPanelAtualNumeroSorteado.add(jButtonatuallNumeroSorteado, gridBagConstraints);

        jLabelatualNumeroSorteado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelatualNumeroSorteado.setForeground(new java.awt.Color(0, 1, 0));
        jLabelatualNumeroSorteado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelatualNumeroSorteado.setText("Número Sorteado:");
        jLabelatualNumeroSorteado.setToolTipText("");
        jLabelatualNumeroSorteado.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanelAtualNumeroSorteado.add(jLabelatualNumeroSorteado, gridBagConstraints);

        jLabelBigLabelatualNumeroSorteado.setFont(new java.awt.Font("Tahoma", 0, 140)); // NOI18N
        jLabelBigLabelatualNumeroSorteado.setForeground(new java.awt.Color(0, 1, 0));
        jLabelBigLabelatualNumeroSorteado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBigLabelatualNumeroSorteado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanelAtualNumeroSorteado.add(jLabelBigLabelatualNumeroSorteado, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jPanelAtualNumeroSorteado, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
// referencia https://stackoverflow.com/questions/26685326/clearing-a-jlist
    private void jButtonIniciarJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIniciarJogoActionPerformed
       if(serversocket == null){
            try {
                serversocket = new Server(this);
                Thread t = new Thread(serversocket);
                t.start();
                modalWait modalWait = (new modalWait(this, true, "A aguardar que jogadores entrem", "Parar"));
                serversocket.inscricaoDeJogadoresTerminou = true;
                jButtonIniciarJogo.setText("Iniciar Jogo");
                
                return;
            } catch (IOException ex) {
                
                JOptionPane.showMessageDialog(this,  "Não foi possível criar o servidor, certifique-se de que a porta " + ServerCommunication.PORTA +" não está ocupada!","Erro!",javax.swing.JOptionPane.ERROR_MESSAGE); 
                return;
            }
       }


        serversocket.iniciar_jogo();
        jButtonIniciarJogo.setEnabled(false);
        jButtonatuallNumeroSorteado.setEnabled(true);
        jButtonTerminarJogo.setEnabled(true);
        
    }//GEN-LAST:event_jButtonIniciarJogoActionPerformed
    public void estado_BotaoSortear(boolean estado){
        jButtonatuallNumeroSorteado.setEnabled(estado);
        
    }
    private void jButtonatuallNumeroSorteadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonatuallNumeroSorteadoActionPerformed
        //Botão para sortear número random, que ao sortear desativa a opção de adicionar mais apostas 
        
        int numRand = 0;
        while(!sessaoDeJogo.sortearNumero(numRand)){
            numRand = randomNum(MIN, MAX);
            if(sessaoDeJogo.getNumerosSorteados().size()>=MAX){
                JOptionPane.showMessageDialog(this,  "Não existem mais números para ser sorteado","Erro!",javax.swing.JOptionPane.WARNING_MESSAGE);
                jButtonatuallNumeroSorteado.setEnabled(false);
                return;
            }
        }
        
        jLabelBigLabelatualNumeroSorteado.setText(String.valueOf(numRand));
        this.serversocket.enviarMSG("numeroSorteado->" + Integer.toString(numRand));
        modelNumerosSorteados.addElement(numRand);
        
    }//GEN-LAST:event_jButtonatuallNumeroSorteadoActionPerformed

    private void jButtonTerminarJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTerminarJogoActionPerformed
        if(this.serversocket != null){    
        int reply = JOptionPane.showConfirmDialog(null, "Tens um jogo em progresso.\nSe continuar esta ação irá perder o seu progresso da sessão, deseja prosseguir?", "Jogo em progresso!", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.NO_OPTION) 
            return;
            
        }
        if(serversocket != null)
            serversocket.setTerminarJogo(true);
        reiniciar_UI();
        this.sessaoDeJogo = new SessaoDeJogoDeLoto(MIN,MAX);
        this.serversocket = null;


    }//GEN-LAST:event_jButtonTerminarJogoActionPerformed
    private void reiniciar_UI(){
        DefaultListModel listModel = (DefaultListModel) jListTrueApostas.getModel();
        listModel.removeAllElements();
        DefaultListModel listModel2 = (DefaultListModel) jListTrueNumerosSorteados.getModel();
        listModel2.removeAllElements();
        this.modelApostas = listModel;
        this.modelNumerosSorteados = listModel2;

        this.jButtonIniciarJogo.setEnabled(true);
        this.jButtonIniciarJogo.setText("Iniciar Jogo");
        this.jButtonatuallNumeroSorteado.setEnabled(false);
        this.jLabelBigLabelatualNumeroSorteado.setText("");
        this.jButtonTerminarJogo.setEnabled(false);
        this.pack();
        
        
    }
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
            java.util.logging.Logger.getLogger(GSalaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GSalaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GSalaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GSalaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GSalaGUI mainGUI = new GSalaGUI();
                mainGUI.setVisible(true);
                mainGUI.setLocationRelativeTo(null);;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIniciarJogo;
    private javax.swing.JButton jButtonTerminarJogo;
    private javax.swing.JButton jButtonatuallNumeroSorteado;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelApostas;
    private javax.swing.JLabel jLabelBigLabelatualNumeroSorteado;
    private javax.swing.JLabel jLabelNumerosSorteados;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelatualNumeroSorteado;
    private javax.swing.JScrollPane jListApostas;
    private javax.swing.JScrollPane jListNumerosSorteados;
    private javax.swing.JList<String> jListTrueApostas;
    private javax.swing.JList<String> jListTrueNumerosSorteados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelApostas;
    private javax.swing.JPanel jPanelAtualNumeroSorteado;
    private javax.swing.JPanel jPanelHead;
    // End of variables declaration//GEN-END:variables

     // Referencia: https://www.baeldung.com/java-generating-random-numbers-in-range
    private static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public void addApostas(int jogadorNovoID, Double jogadorValorAposta){
            sessaoDeJogo.adicionarAposta(jogadorNovoID, jogadorValorAposta);
            System.out.println("aposta add");
                String nomeJogadorNovo = serversocket.getJogadorNome(jogadorNovoID);
                for(int jogadoresID_it : this.sessaoDeJogo.getApostasFeitas().keySet())
                    if( serversocket.getJogadorNome( jogadoresID_it).equals(nomeJogadorNovo) && jogadoresID_it != jogadorNovoID){
                        modelApostas.addElement(nomeJogadorNovo + "_"+jogadorNovoID + "->" +  jogadorValorAposta);
                        return;
                    }
                modelApostas.addElement(nomeJogadorNovo + "->" +  jogadorValorAposta);
    }       

    public boolean finalizarJogo(ArrayList<String[]> finalistasDadosEntrada){
        if(sessaoDeJogo.getNumerosSorteados().size() < 15)
            return false;

        HashMap<Integer,Integer> finalistasQtdNumerosConfirmados = new HashMap<>();
        
        Iterator<Integer> nmrosorteadosIT =  this.sessaoDeJogo.getNumerosSorteados().iterator();
        System.out.println("Quantidade de Nuemros Sorteados:" + sessaoDeJogo.getNumerosSorteados().size());
        while(nmrosorteadosIT.hasNext() ){
            int numeroSorteado = nmrosorteadosIT.next();
            System.out.println("Número sorteado a ser verificado sorteado :" + numeroSorteado);
            Iterator<String[]> iteradorfinalistas = finalistasDadosEntrada.iterator();

            
            while(iteradorfinalistas.hasNext()){
                String[] finalista = iteradorfinalistas.next();
                Pattern pattern = Pattern.compile("("+numeroSorteado+ "),?", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(finalista[1]);
                try {
                    if(matcher.find()){
                        System.out.println(numeroSorteado + " foi verificado no cartao do jogador" + serversocket.getJogadorNome(Integer.valueOf(finalista[0])));
                          System.out.println("Cartao: " + finalista[1]);
                        int finalistaID = Integer.parseInt(finalista[0]);
                        if(finalistasQtdNumerosConfirmados.containsKey(finalistaID))
                            finalistasQtdNumerosConfirmados.put(finalistaID, finalistasQtdNumerosConfirmados.get(finalistaID)+ 1);
                        else
                            finalistasQtdNumerosConfirmados.put(finalistaID, 1);
                    }else{
                        
                         System.out.println(numeroSorteado + " nao foi verificado no cartao do jogador" + serversocket.getJogadorNome(Integer.valueOf(finalista[0])));
                         System.out.println("Cartao: " + finalista[1]);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("numero nao pode ser convertido para Int em finalizarJogo");
                }
    
            }
            boolean temFinalistaValido = false;
            for(int finalistaID : finalistasQtdNumerosConfirmados.keySet())
                if(finalistasQtdNumerosConfirmados.get(finalistaID) >= 15)
                    temFinalistaValido = true;
            if(temFinalistaValido)
                break;
            if(nmrosorteadosIT.hasNext() == false)
                return false;
        }
        
        ArrayList<Integer> vencedores = new ArrayList<>();
        String mensagemAEnviar= "jogoTerminou->true";
        for(int id : finalistasQtdNumerosConfirmados.keySet()){
            if(finalistasQtdNumerosConfirmados.get(id) >=15){
                vencedores.add(id);
                

            }
        }
        if(vencedores.size() <1)
            return false;
        for(int idFinalista : vencedores)
            mensagemAEnviar = mensagemAEnviar + "(&)"+idFinalista+"->"+ sessaoDeJogo.getScores(vencedores).get(idFinalista) ;
            
        serversocket.enviarMSG(mensagemAEnviar);
        ModalGameScores myDialog = new ModalGameScores(this, true,sessaoDeJogo.getScores(vencedores));
        myDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        myDialog.setLocationRelativeTo(null);  
        myDialog.setVisible(true);
        
        
        return true;
        
    }
}

