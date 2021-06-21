/**
 *  JogosLotoGestorDeSalas Package com classes para criar Jogador de Jogos de Loto
 */
package JogosLotoJogador;

import JogosLotoLivraria.EncriptacaoAES;
import JogosLotoGestorDeSalas.ServerCommunication;
import JogosLotoLivraria.modalWait;
import java.awt.GridBagConstraints;

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
public final class JogadorGUI extends javax.swing.JFrame{

/**
 * Coleção de Linhas  que armazenam por sua vez uma coleção de Labels especiais
 */
    private final ArrayList<ArrayList<JLabelCartao >> LinhasDeLabel;
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
    private boolean jogoIniciado;
/**
 * Tema a ser utilizado no jogo 
 */
    private Tema tema;
    
    private ClientCommunication clientCommunication; 

    private final JLabelCartao jLabelBigAtualNumero;

    private JLabelCartao ultimo_NumeroAcertado;
    
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
        
        
        ultimo_NumeroAcertado = null;
        jLabelBigAtualNumero = new JLabelCartao( tema.TEMA);
        jLabelBigAtualNumero.setFont(new java.awt.Font("Tahoma", 1, 120)); 
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 1);
        getContentPane().add(jLabelBigAtualNumero, gridBagConstraints);
        construirCartao();
    }
    
/**
 * Método que adiciona os números do Cartão e adiciona-os a Interface gráfica
 */
    private void construirCartao(){
        

        jTextAreaLogger.setBackground(tema.PANEL_CONTENT_BACKGROUND);
        
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();
        
        while ( iteradorArrayList.hasNext() ){
            ArrayList<JLabelCartao > colunaJLabel = new ArrayList<>();
            
            HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();
            List<Integer> sortedKeys=new ArrayList(coluna.keySet());
            Collections.sort(sortedKeys);
            int y =0;
            for (int i : sortedKeys) {
                
                JLabelCartao customLabel = new JLabelCartao(coluna.get(i), tema.TEMA , y);
                colunaJLabel.add(customLabel);
                jPanelCartaoContent.add(customLabel);
                y++;
            }
            LinhasDeLabel.add(colunaJLabel);

        }
        this.pack();
    }
   
    private boolean validarJogo(String textoDebug){

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
            if(cartao.verificar_integridade(textoDebug))
                return true;
          
        } 
        return false;
        
    }
     void sortearNumero(int numero){
       if(jogoIniciado){
           //verificar se o numero foi sorteado, marcar o numero no GUI 
            boolean temNumerosNaoMarcados = false;
            HashMap<Integer,Slot_Numero > colunaNumeroMarcado  = this.cartao.MarcarNumeroSorteado(numero);
            if(colunaNumeroMarcado != null){
                System.out.println("marcou um numero" + numero);
                for(JLabelCartao jlabelcartao : this.LinhasDeLabel.get(this.cartao.getLinhasArrayList().indexOf(colunaNumeroMarcado)))
                    if(jlabelcartao.getSlot_numero()!= null)
                        if(jlabelcartao.getSlot_numero()!= null)
                            if(jlabelcartao.getSlot_numero().getNumero() == numero){
                                jLabelBigAtualNumero.setBackground(this.tema.NUMERO_ACERTADO_BACKGROUND);
                                if(ultimo_NumeroAcertado != null)
                                    ultimo_NumeroAcertado.marcarJLabel(false);
                                jlabelcartao.marcarJLabel(true);
                                ultimo_NumeroAcertado = jlabelcartao;
                           }

                jTextAreaLogger.append("< O Número " + numero+ " foi marcado no seu cartão! >\n"); 
                this.pack();
                   
//                verificar se todos os números foram marcados
                Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();
                while ( iteradorArrayList.hasNext() ){
                    HashMap<Integer,Slot_Numero > coluna_IT = iteradorArrayList.next();
                    for (int i : coluna_IT.keySet()) 
                        if(coluna_IT.get(i)!= null)
                            if(coluna_IT.get(i).getMarcado() == false){
                                temNumerosNaoMarcados = true;
                                break;
                            }
                }
                if(temNumerosNaoMarcados == false){
                    jTextAreaLogger.append("< Cartão está completo! >\n");
                    clientCommunication.enviarMSG("terminou->true(&)chave->"+this.clientCommunication.getChaveDecriptar());
                    jogoIniciado = false;
                } 
            }
            else{
                System.out.println("nao foi marcado nmr " + numero);
                jLabelBigAtualNumero.setBackground(this.tema.NUMERO_BACKGROUND);
                if(ultimo_NumeroAcertado != null)
                    ultimo_NumeroAcertado.marcarJLabel(false);
                jTextAreaLogger.append("< O Número " + numero+ " não existe no seu cartão! >\n");
            }
        jLabelBigAtualNumero.setText(Integer.toString(numero));
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
        jButtonNovoCartao = new javax.swing.JButton();
        jButtonTerminarJogo = new javax.swing.JButton();
        jlabelTips = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaLogger = new javax.swing.JTextArea();
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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 30);
        jPanelOpcoes.add(jButtonIniciarJogo, gridBagConstraints);

        jButtonNovoCartao.setText("Novo Cartão ");
        jButtonNovoCartao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonNovoCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoCartaoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanelOpcoes.add(jButtonNovoCartao, gridBagConstraints);

        jButtonTerminarJogo.setText("Terminar Jogo");
        jButtonTerminarJogo.setEnabled(false);
        jButtonTerminarJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTerminarJogoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        jPanelOpcoes.add(jButtonTerminarJogo, gridBagConstraints);

        jlabelTips.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jlabelTips.setText("*Para editar um número clique nele e escolha o novo valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanelOpcoes.add(jlabelTips, gridBagConstraints);

        jTextAreaLogger.setEditable(false);
        jTextAreaLogger.setBackground(this.tema.PANEL_CONTENT_BACKGROUND);
        jTextAreaLogger.setColumns(20);
        jTextAreaLogger.setLineWrap(true);
        jTextAreaLogger.setRows(3);
        jTextAreaLogger.setText(" ");
        jTextAreaLogger.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jScrollPane1.setViewportView(jTextAreaLogger);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 5, 0);
        jPanelOpcoes.add(jScrollPane1, gridBagConstraints);

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

        if(jogoIniciado){
            int reply = JOptionPane.showConfirmDialog(null, "Tens um jogo em progresso.\nSe continuar esta ação irá perder o seu progresso do jogo anterior, deseja prosseguir?", "Jogo em progresso!", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) 
                return;
        }

        //conectar se ao servidor
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR)); 
        jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        String textoDebug  = "ee";
        if(!validarJogo(textoDebug)){
            JOptionPane.showMessageDialog(this,"O cartão não é válido, por favor verifique os números!" + textoDebug,"Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.clientCommunication = new ClientCommunication(this);
        if(!this.clientCommunication.conectar()){
            JOptionPane.showMessageDialog(this,"Não foi possivel estabelecer uma conexão com o servidor, por favor tente novamente","Erro ao conectar-se",javax.swing.JOptionPane.WARNING_MESSAGE);
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 
            jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 
            this.clientCommunication.setTerminarJogo(true);
            this.clientCommunication = null;
            return;
        }
        
        iniciarJogo();
        
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 
        jButtonIniciarJogo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jButtonIniciarJogoActionPerformed
  
    private void iniciarJogo(){
        //gerar chaveDecriptar para decriptar cartão no servidor
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
            this.clientCommunication.addChave(String.valueOf(key));
            
        }
        //encriptar cartao com chaveDecriptar
        String cartaoNumeros = new String();
        
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();
        while ( iteradorArrayList.hasNext() ){
            
            HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();
            List<Integer> sortedKeys=new ArrayList(coluna.keySet());
            Collections.sort(sortedKeys);
            
            for (int i : sortedKeys) 
                if(coluna.get(i) != null)
                   cartaoNumeros+= coluna.get(i).getNumero() +",";
            }
        
        cartaoNumeros = cartaoNumeros.substring(0, cartaoNumeros.length()-1);
        String msg_Encriptada = EncriptacaoAES.encrypt(cartaoNumeros, this.clientCommunication.getChaveDecriptar());
        
        //adicionar aposta e enviar cartao
        JogosLotoLivraria.modaAddlAposta myDialog = new JogosLotoLivraria.modaAddlAposta(this, true);
        myDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        myDialog.setLocationRelativeTo(null);  
        myDialog.setVisible(true);

        if(myDialog.data[0] != null)
            this.clientCommunication.enviarMSG("apostaNome->" + myDialog.data[0] +"(&)apostaValor->"+myDialog.data[1] + "(&)cartaoNumeros->" + msg_Encriptada);
        else
            return;
        
        jTextAreaLogger.append("\nVocê apostou " + myDialog.data[1] + " utilizando o username " +  myDialog.data[0] + "\n");
         String msgRecebida;
         //esperar numero id
        try {

            clientCommunication.setSocketTimeout(ServerCommunication.TEMPO_ESPERA_RESPOSTA);
            msgRecebida = ClientCommunication.decodificar(clientCommunication.esperarMSG()).get("numIdentificacao");
  
        } catch ( java.net.SocketTimeoutException ex ) {
            JOptionPane.showMessageDialog(this,"Foi possível conectar-se ao servidor, porém o mesmo não responde!\nCertifique-se de que o Servidor já não tenha um jogo em progresso",
                    "Sem Resposta",javax.swing.JOptionPane.WARNING_MESSAGE);
            this.clientCommunication.setTerminarJogo(true);
            this.clientCommunication = null;
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"A conexão entre o servidor e o cliente falhou, verifique a sua conexão","Erro de conexão",javax.swing.JOptionPane.WARNING_MESSAGE);
            this.clientCommunication.setTerminarJogo(true);
            this.clientCommunication = null;
            return;
        }
        
        if(msgRecebida !=null){
            try {
             Integer.parseInt(msgRecebida);
            if(Integer.valueOf(msgRecebida) < 1)
              throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,"Ocorreu um erro ao validar a resposta do servidor, por favor tente novamente!","Erro!",javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            this.clientCommunication.setJogadorID( Integer.valueOf(msgRecebida));
        }  

        //esperar que o servidor  avise que o jogo iniciou
            try {
                clientCommunication.setSocketTimeout(0);
                while(true){
                    jTextAreaLogger.append("A aguardar até que o anfitriao inicie o jogo!\n");
                    modalWait modalWait = (new modalWait(this, true, "A aguardar que o jogo inicie...", "Cancelar" , this.clientCommunication));
                    if(modalWait.mensagem_recebida != null){
                        if(ClientCommunication.decodificar(modalWait.mensagem_recebida).containsKey("jogoIniciado") &&
                              ClientCommunication.decodificar(modalWait.mensagem_recebida).get("jogoIniciado").equals("true"))
                            break;
                    }
                    else{
                        if(modalWait.conexao_Falhou)
                            throw new IOException();
                        if(modalWait.mensagem_recebida == null){
                            this.jTextAreaLogger.append("Cancelaste o jogo");
                            this.clientCommunication.setTerminarJogo(true);
                            this.clientCommunication = null;
                            return;
                        }
                    }
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"A conexão entre o servidor e o cliente falhou, verifique a sua conexão","Erro de conexão",javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        
       
        //iterar sobre as labels com numeros e avisa las que o jogo comecou
        Iterator<ArrayList<JLabelCartao >> iteradorArrayListJLabel  = LinhasDeLabel.iterator();

        while ( iteradorArrayListJLabel.hasNext()  ){
            ArrayList<JLabelCartao> colunaJLabel = iteradorArrayListJLabel.next();
            for(JLabelCartao jlabelcartao : colunaJLabel)
                jlabelcartao.setJogoIniciado(true);
        }
        //iniciar efetivamente o jogo
        jlabelTips.setVisible(false);
        jTextAreaLogger.append("<Jogo Iniciado>\n");
        jButtonIniciarJogo.setEnabled(false);
        jButtonNovoCartao.setEnabled(false);
        jButtonTerminarJogo.setEnabled(true);
        jogoIniciado = true;
        clientCommunication.iniciarThread();
        
        
    }
    
    
    
    /**
 * Método executado quando se clica no botão para criar um novo cartão, este método reinicia o jogo com um novo cartão
 */
    private void jButtonNovoCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoCartaoActionPerformed
        //Botão para gerar novo cartão
        
        if(!jButtonIniciarJogo.isEnabled())
            botoesEstadosModelos(0);
        novoJogo();
    }//GEN-LAST:event_jButtonNovoCartaoActionPerformed

    private void jButtonTerminarJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTerminarJogoActionPerformed
        // TODO add your handling code here:
        
        if(jogoIniciado){
            int reply = JOptionPane.showConfirmDialog(null, "Tens um jogo em progresso.\nSe continuar esta ação irá perder o seu progresso do jogo anterior, deseja prosseguir?", "Jogo em progresso!", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) 
                return;
        }
    
 

        this.jogoIniciado = false;
        this.clientCommunication.setTerminarJogo(true);

        
        int reply = JOptionPane.showConfirmDialog(null, "Podes reiniciar o cartão ou mante-lo no estado em que o jogo decorria.\n Desejas reiniciar o cartão?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.NO_OPTION){ 
            botoesEstadosModelos(1);
            return;
        }
        
        resetarNumeros();
        
        
    }//GEN-LAST:event_jButtonTerminarJogoActionPerformed
    
     void novoJogo(){
        this.tema =  new Tema(Temas.values()[Cartao.randomNum(0, 2)]);
         jLabelBigAtualNumero.setTEMA(tema);
         jLabelBigAtualNumero.setText(" ");
         jPanelCartaoContent.setBackground(this.tema.PANEL_CONTENT_BACKGROUND);
         jPanelOpcoes.setBackground(this.tema.PANEL_OPCOES_BACKGROUND);
         this.ultimo_NumeroAcertado = null;
 
        jPanelCartaoContent.removeAll();
        jlabelTips.setVisible(true);
        jPanelCartaoContent.updateUI();
        this.jogoIniciado = false;
        this.LinhasDeLabel.clear();

        
        this.cartao = new Cartao(JogadorGUI.COLUNAS_DIM,JogadorGUI.LINHAS_DIM,JogadorGUI.QTD_NUMEROS_DIM);
        this.construirCartao();
        if(clientCommunication != null)
            this.clientCommunication.isTerminarJogo();
        this.clientCommunication = null;
        
        
    }
     public void resetarNumeros(){
        this.botoesEstadosModelos(0);
        if(this.clientCommunication != null){
            this.clientCommunication.setTerminarJogo(true);
            this.clientCommunication = null;
        }


        this.jogoIniciado = false;
        this.ultimo_NumeroAcertado = null;
        this.jLabelBigAtualNumero.setText(" ");
        Iterator<ArrayList<JLabelCartao >> iteradorArrayListJLabel = LinhasDeLabel.iterator();
        while ( iteradorArrayListJLabel.hasNext()   ){
            ArrayList<JLabelCartao> colunaJLabel = iteradorArrayListJLabel.next();
            for(JLabelCartao jlabelcartao : colunaJLabel)
                jlabelcartao.desmarcarJLabel();
        }
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
    public void botoesEstadosModelos(int estado){
        
        switch(estado){
            case 0:
                jButtonIniciarJogo.setEnabled(true);
                jButtonTerminarJogo.setEnabled(false);
                jButtonNovoCartao.setEnabled(true);
                break;
            case 1:
                jButtonIniciarJogo.setEnabled(false);
                jButtonTerminarJogo.setEnabled(false);
                jButtonNovoCartao.setEnabled(true);
                break;
            default:
                jButtonIniciarJogo.setEnabled(false);
                jButtonTerminarJogo.setEnabled(false);
                jButtonNovoCartao.setEnabled(true);
                break;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIniciarJogo;
    private javax.swing.JButton jButtonNovoCartao;
    private javax.swing.JButton jButtonTerminarJogo;
    private javax.swing.JPanel jPanelCartaoContent;
    private javax.swing.JPanel jPanelOpcoes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaLogger;
    private javax.swing.JLabel jlabelTips;
    // End of variables declaration//GEN-END:variables


}
