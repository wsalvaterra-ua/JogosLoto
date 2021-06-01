/**
 *  JogosLotoGestorDeSalas Package com classes para criar Jogador de Jogos de Loto
 */
package JogosLotoJogador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *Jogo De Loto com Interface gráfica em que o utilizador tem um Cartão com 3 linhas, e 9 colunas e 5 números por linha
 * @author bil
 */
public final class JogadorGUI extends javax.swing.JFrame implements ActionListener{

/**
 * Coleção de Linhas  que armazenam por sua vez uma coleção de Labels especiais
 */
    public final ArrayList<ArrayList<JLabelCartao >> LinhasDeLabel;
/**
 * Cartão utilizado durante o Jogo
 */
    public Cartao cartao;
/**
 * Quantidade de linhas a ser gerada
 */
    public static int  COLUNAS_DIM = 9;
/**
 * Quantidade de Colunas a ser gerada
 */
    public static int LINHAS_DIM = 3;
/**
 * Quantidade de números por linha. 
 */
    public static  int QTD_NUMEROS_DIM = 5;
/**
 * Indica se jogo foi iniciado ou não. 
 */
    public boolean jogoIniciado;
/**
 * Tema a ser utilizado no jogo 
 */
    public Tema tema;
    
    private ClientCommunication clientCommunication; 
    private javax.swing.Timer timerSocket;
    private javax.swing.Timer timerJogo;
    private String chave;
    private int numIdentificacao;
/**
 * Contrói o JogosDeLoto com interface gráfica com um cartão com 3 linhas, 5 colunas e 5 números em posições aleatórias por linha
 */


    public JogadorGUI() {
        super();
        
         this.tema =  new Tema(Temas.values()[Cartao.randomNum(0, 2)]);
         //Cores dos JPaneis sao declaradas no método initComponents
        initComponents();
        this.jogoIniciado = false;
        this.LinhasDeLabel = new ArrayList<>();
        this.cartao = new Cartao(JogadorGUI.COLUNAS_DIM,JogadorGUI.LINHAS_DIM,JogadorGUI.QTD_NUMEROS_DIM);
        
        construirCartao();
    }
    
/**
 * Método que adiciona os números do Cartão e adiciona-os a Interface gráfica
 */
    public void construirCartao(){
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();
        
        while ( iteradorArrayList.hasNext() ){
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
   
    private boolean validarJogo(){

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
                    int max =  Cartao.getColumnMax(i, COLUNAS_DIM );
                    if(jlabelcartao.getSlot_numero() != null){
                        if(jlabelcartao.getSlot_numero().getNumero() < min || jlabelcartao.getSlot_numero().getNumero() > max){
                            
                            return false;
                        }
                        colunaCartao.put(jlabelcartao.getSlot_numero().getNumero(),jlabelcartao.getSlot_numero());

                    }
                    else
                        colunaCartao.put( Cartao.randomNum(Cartao.getColumnMin(i), Cartao.getColumnMax(i, COLUNAS_DIM )), null);
                }
            }
            if(cartao.verificar_integridade())
                return true;
          
        } 
        return false;
        
    }
    private void sortearNumero(int numero){
       if(jogoIniciado){
            boolean temNumerosNaoMarcados = false;
            HashMap<Integer,Slot_Numero > colunaNumeroMarcado  = this.cartao.MarcarNumeroSorteado(numero);
            if(colunaNumeroMarcado != null){
                System.out.println("marcou um numero" + numero);
               for(JLabelCartao jlabelcartao : this.LinhasDeLabel.get(this.cartao.getLinhasArrayList().indexOf(colunaNumeroMarcado)))
                   if(jlabelcartao.getSlot_numero()!= null)
                       if(jlabelcartao.getSlot_numero()!= null)
                           if(jlabelcartao.getSlot_numero().getNumero() == numero)
                                   jlabelcartao.marcarJLabel();
                jLabelJogoStatus.setText("< O Número " + numero+ " foi marcado no seu cartão! >"); 
                   
                Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();

                while ( iteradorArrayList.hasNext() ){
                    HashMap<Integer,Slot_Numero > coluna_IT = iteradorArrayList.next();


                    for (int i : coluna_IT.keySet()) 
                        if(coluna_IT.get(i)!= null)
                            if(coluna_IT.get(i).getMarcado() == false)
                                temNumerosNaoMarcados = true;
                }
                if(temNumerosNaoMarcados == false){
                    System.out.println("terminou o jogo");
                    clientCommunication.enviarMSG("terminou->"+this.numIdentificacao +"(&)chave->"+this.chave);
                    jogoIniciado = false;

                }
            }
            else{
                System.out.println("nao foi marcado nmr " + numero);
                jLabelJogoStatus.setText("< O Número " + numero+ " não existe no seu cartão! >");
            }

        }else System.out.println("Esta a marcar numeros mas jogo nao foi iniciado");
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
        jButtonIniciarJogo = new javax.swing.JButton();
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

        jButtonIniciarJogo.setText("Iniciar Jogo");
        jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonIniciarJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIniciarJogoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 0, 30);
        jPanelOpcoes.add(jButtonIniciarJogo, gridBagConstraints);

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
        jPanelCartaoContent.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
/**
 * Método executado quando se clica no botão para sortear um número, que pede ao utilizador que introduza o número a ser sorteado
 */
    private void jButtonIniciarJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIniciarJogoActionPerformed
       //Botão para iniciar o jogo
        if(jogoIniciado)
           return;
        
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR)); 
        jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        this.clientCommunication = new ClientCommunication();
        if(!this.clientCommunication.conectar()){
            JOptionPane.showMessageDialog(this,"Não foi possivel estabelecer uma conexão com o servidor, por favor tente novamente","Erro ao conectar-se",javax.swing.JOptionPane.WARNING_MESSAGE);
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 
            jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 
            return;
        }
        
        if(!validarJogo()){
             JOptionPane.showMessageDialog(this,"O cartão não é válido, por favor verifique os números!","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        iniciarJogo();
      
 
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 
        jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 


            

    }//GEN-LAST:event_jButtonIniciarJogoActionPerformed
    private void iniciarJogo(){

        char[] key = new char[Cartao.randomNum(6,15)];
        for(int i = 0; i< key.length;i++){
            switch(Cartao.randomNum(1,3)){
                
                case 1:
                    key[i] = (char)Cartao.randomNum(48,57);
                    break;
                case 2:
                    key[i] = (char)Cartao.randomNum(65,90);
                    break;
                case 3:
                    key[i] = (char)Cartao.randomNum(97,122);
                    break; 
            }
            chave = String.valueOf(key);
            
        }
        
        String cartaoNumeros = "cartaoNumeros->";
        
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();
        while ( iteradorArrayList.hasNext() ){
            
            HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();
            List<Integer> sortedKeys=new ArrayList(coluna.keySet());
            Collections.sort(sortedKeys);
            
            for (int i : sortedKeys) 
                cartaoNumeros+= coluna.get(i) +",";
            }
        
        cartaoNumeros = cartaoNumeros.substring(0, cartaoNumeros.length()-2);
        String msg_Encriptada = EncriptacaoAES.encrypt(cartaoNumeros, chave);
        
        
        modaAddlAposta myDialog = new modaAddlAposta(this, true);
        myDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        myDialog.setLocationRelativeTo(null);  
        myDialog.setVisible(true);
        if(myDialog.data[0] != null)
            this.clientCommunication.enviarMSG("apostaNome->" + myDialog.data[0] +"(&)apostaValor->"+myDialog.data[1] + "(&)cartao->" + msg_Encriptada);
        else
            this.clientCommunication.enviarMSG("cartao->" + msg_Encriptada);

         String msgRecebida;
         
        try {
            System.out.println("espera numID");
            msgRecebida = ClientCommunication.decodificar(clientCommunication.esperarMSG()).get("numIdentificacao");
  
        } catch (IOException ex) {

            JOptionPane.showMessageDialog(this,"A conexão entre o servidor e o cliente falhou, verifique a sua conexão","Erro de conexão",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(msgRecebida !=null){

            if(msgRecebida.equals("false")){
                JOptionPane.showMessageDialog(this,"O Anfitrião rejeitou o seu cartão","Erro!",javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            numIdentificacao = Integer.valueOf(msgRecebida);
            System.out.println("numID: " + numIdentificacao  );
        }  

        
        
        
        
            
        while(true){
       
            try {
                System.out.println("Aguarde até que o anfitriao inicie o jogo!");
                msgRecebida = ClientCommunication.decodificar(clientCommunication.esperarMSG()).get("jogoIniciado");
                System.out.println();
                if(msgRecebida.equals("true"))
                    break;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"A conexão entre o servidor e o cliente falhou, verifique a sua conexão","Erro de conexão",javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
       
        
        Iterator<ArrayList<JLabelCartao >> iteradorArrayListJLabel = LinhasDeLabel.iterator();
        iteradorArrayListJLabel = LinhasDeLabel.iterator();

        while ( iteradorArrayListJLabel.hasNext()  ){
            ArrayList<JLabelCartao> colunaJLabel = iteradorArrayListJLabel.next();
            for(JLabelCartao jlabelcartao : colunaJLabel)
                jlabelcartao.setJogoIniciado(true);
        }
        jlabelTips.setVisible(false);
        jLabelJogoStatus.setText("<Jogo Iniciado>");

        jogoIniciado = true;
        timerSocket = new javax.swing.Timer(1000, clientCommunication);
        timerSocket.start();        
        timerJogo = new javax.swing.Timer(1000, this);
        timerJogo.start();        

        
        
    }
    
    
    
    /**
 * Método executado quando se clica no botão para criar um novo cartão, este método reinicia o jogo com um novo cartão
 */
    private void jButtonNovoCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoCartaoActionPerformed
        //Botão para gerar novo cartão
        if(jogoIniciado){
            int reply = JOptionPane.showConfirmDialog(null, "Tens um jogo em progresso.\nSe continuar esta ação irá perder o seu progresso do jogo anterior, deseja prosseguir?", "Jogo em progresso!", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) 
                return;
        }
        novoJogo();
    }//GEN-LAST:event_jButtonNovoCartaoActionPerformed
    
    private void novoJogo(){
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
    private javax.swing.JButton jButtonIniciarJogo;
    private javax.swing.JButton jButtonNovoCartao;
    private javax.swing.JLabel jLabelJogoStatus;
    private javax.swing.JPanel jPanelCartaoContent;
    private javax.swing.JPanel jPanelOpcoes;
    private javax.swing.JLabel jlabelTips;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        HashMap<String,String> dados = ClientCommunication.decodificar(this.clientCommunication.lerMSGs());
        
        System.out.println(dados.toString());
        if(dados.containsKey("jogoTerminou")){
            System.out.println("dados de jogoterminou:" + dados.keySet());
            if(dados.get("jogoTerminou").equals("true")){
                if(dados.containsKey(Integer.toString(this.numIdentificacao)))
                    System.out.println("Parabens, como vencedor ganhaste uma recompensa de" + dados.get(Integer.toString(this.numIdentificacao)));
                else
                    System.out.println("Jogo terminou e nao foste um dos vencedores");
                int reply = JOptionPane.showConfirmDialog(null, "Deseja comecar um novo jogo??", "Jogo Terminou!", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.NO_OPTION) 
                    return;

                novoJogo();
            }

        }

            
        if(dados.containsKey("numeroSorteado")){
            try {
                sortearNumero(Integer.parseInt(dados.get("numeroSorteado")));
//                System.out.println("um numero foi sorteado , será que pertence ao seu cartao?" + dados.get("numeroSorteado"));
            } catch (NumberFormatException n) {
                System.out.println("numero sorteado nao é numero");
            }
            
        }
        
    }
}
