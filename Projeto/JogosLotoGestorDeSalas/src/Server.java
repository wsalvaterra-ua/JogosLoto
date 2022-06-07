/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import JogosLotoLivraria.modalWait;
import JogosLotoLivraria.readConfig;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
  
/**
 * Classe que atua como intermediário entre os clientes conectados e o GUI.
 *  
 * 
 * @author Rui Oliveira e William Salvaterra
 */
public class Server implements Runnable
{
    
    private boolean terminarJogo;
/**
 * Propriedade que tira o servidor do modo de aceitação de novos clientes
 */
    protected boolean inscricaoDeJogadoresTerminou;

    private final GSalaGUI GestorGUI;
    private final HashMap<Integer,ServerCommunication> jogadoresSocket;

    private final ServerSocket serverSocket;
/**
 * Construtor da Classe.Um servidor é criado.
     * @param gestorDeSala Gestor de Sala a ser utilizado
 */
    public Server( GSalaGUI gestorDeSala) throws IOException{
        super();
        
        GestorGUI = gestorDeSala;
        serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(readConfig.getPorta()));

        terminarJogo = false;
        inscricaoDeJogadoresTerminou  = false;

        jogadoresSocket = new HashMap<>();
    }
/**
 * Método que será executado noutro Thread, este método receberá os clientes no servidor, e verficará se algum Cliente completou o seu cartão ou saiu do jogo.
 */  
    @Override
    public void run() {
        synchronized(jogadoresSocket)
        {
            receberClientes();
            //fechar conexao caso nenhum utiliador tenha se ingressado
            if(jogadoresSocket.size()<1){
                try {
                    this.serverSocket.close();
                } catch (IOException ex) {
                    
                }
                return;
            }   
            while(!terminarJogo){
                ArrayList<String[]> finalistas = new ArrayList<>();
                
                //tecnica para criar iterador para hashmap
               int[] jogadores_IT = new int[jogadoresSocket.size()];
               {
                    int i = 0;
                    for (int c : jogadoresSocket.keySet()) {
                         jogadores_IT[i++] = c;
                    }
                }
                //fim de tecnica para criar iterador para hashmap
               boolean jaEsperouVencedores = false;
                for(int i: jogadores_IT){
                    ServerCommunication cliente = jogadoresSocket.get(i);

                    if(cliente.terminouCartao() && cliente.isTerminarJogo() == false){
                        try {

                            //decriptar os numeros dos cartoes
                            String numerosDecript = JogosLotoLivraria.EncriptacaoAES.decrypt(cliente.getCartaoNumerosEncoded(), cliente.getChaveDecriptar());
                            if(numerosDecript != null)
                            {
                                if(numerosDecript.split(",").length == 15){
                                    finalistas.add(new String[]{Integer.toString(cliente.getJogadorID()),numerosDecript});
                                    GestorGUI.estado_BotaoSortear(false);
                                    //tempo para que os outros clientes se atualizem caso queiram se declarar como vencedores também
                                    if(!jaEsperouVencedores){
                                        modalWait modalWait = (new modalWait(this.GestorGUI, true, "A Processar Possíveis Vencedores", ServerCommunication.TEMPO_ESPERA_RESPOSTA));

                                        if(modalWait.getTempoRestante() > 0){

                                            Thread.sleep(modalWait.getTempoRestante());
                                        }
                                        jaEsperouVencedores = true;
                                    }
                                }
                            }
                        } catch (InterruptedException ex) {
                                for(int b: jogadoresSocket.keySet())
                                    jogadoresSocket.get(b).setTerminarJogo(true);
                                this.terminarJogo = true;
                        }
                   }
                    if(cliente.isTerminarJogo()){
                        jogadoresSocket.remove(cliente.getJogadorID());
                        GestorGUI.clearApostas();
                        for (int b : jogadoresSocket.keySet()) 
                           GestorGUI.addApostas(b,jogadoresSocket.get(b).getAposta());
                        
                    }
                }

                if(finalistas.size() > 0)
                    if(this.GestorGUI.finalizarJogo(finalistas)){
                        this.terminarJogo = true;
                    }else{
    
                        String mensagem = "O(s) jogador(es) ";
                        
                        for(int i=0; i < finalistas.size();i++){
                            ServerCommunication jogador = jogadoresSocket.get(Integer.valueOf(finalistas.get(i)[0]));
                            mensagem+= jogador.getNomeJogador() + ", ";
                            mensagem+=" tentou(aram) terminar o jogo mas não têm/tem números sorteados suficientes no cartão para o fazer,"
                                    + "consequentemente foram expulsos do jogo.";
                            jogador.enviarMSG("cheated->true");
                            jogador.setTerminarJogo(true);
                            jogadoresSocket.remove(Integer.valueOf(finalistas.get(i)[0]));
                            
                        }
                        GestorGUI.clearApostas();
                        for (int b : jogadoresSocket.keySet()) 
                            GestorGUI.addApostas(b,jogadoresSocket.get(b).getAposta());
                        JOptionPane.showMessageDialog(this.GestorGUI, mensagem,
                                        "Tentativa de Trapaça", JOptionPane.WARNING_MESSAGE);
                        GestorGUI.estado_BotaoSortear(true);
                    }

                //sleep para ciclo nao trabalhar excessivamente
                try {
                    Thread.sleep(2*ServerCommunication.INTERVALO_ATUALIZACAO);
                } catch (InterruptedException ex) {
                    this.terminarJogo = true;
                }
            }
            this.enviarMSG("jogoTerminou->true");
            for(int b: jogadoresSocket.keySet())
                jogadoresSocket.get(b).setTerminarJogo(true);
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   }

/**
 *Método que envia uma mensagem para todos os sockets conectados
     * @param msg Mensagem a ser enviada
 */  
    public void enviarMSG(String msg) {
        for(int i: jogadoresSocket.keySet())
            jogadoresSocket.get(i).enviarMSG(msg);
    }
    

/**
 * Método que envia informa a todos os clientes conectados de que o jogo iniciou.
 */  
    public void iniciar_jogo(){
        this.enviarMSG("jogoIniciado->true");
    }
 /**
 * Método que retorna o nome de um utilizador .
     * @param jogadorID ID do Jogador alvo
     * @return Nome do Jogaodr
 */  
    public String getJogadorNome(int jogadorID){
        if(!this.jogadoresSocket.containsKey(jogadorID)){
            return null;
        }
        return this.jogadoresSocket.get(jogadorID).getNomeJogador();
        
    }
    private void receberClientes() {
// referencia: https://www.baeldung.com/java-measure-elapsed-time


            try {
                serverSocket.setSoTimeout(400);
            } catch (SocketException ex) {
               
                JOptionPane.showMessageDialog(this.GestorGUI,  "Ocorreu um erro, por favor inicie um novo jogo!","Erro!",javax.swing.JOptionPane.ERROR_MESSAGE); 
                return;
            }
            int lastID = 1;
            while( !inscricaoDeJogadoresTerminou){
                try {

                    Socket socket;
                //tecnica para criar iterador para hashmap
                    int[] jogadores_IT = new int[jogadoresSocket.size()];
                   {
                        int i = 0;
                        for (int c : jogadoresSocket.keySet()) {
                             jogadores_IT[i++] = c;
                        }
                    }
                //fim de tecnica para criar iterador para hashmap
                //remover aposta do jogo caso cliente tenha disconectado
                    for (int c : jogadores_IT){ 
                        if(jogadoresSocket.get(c).isTerminarJogo()){
                            jogadoresSocket.remove(c);
                            GestorGUI.clearApostas();
         
                            for (int b : jogadoresSocket.keySet()) 
                               GestorGUI.addApostas(b,jogadoresSocket.get(b).getAposta());
                        }
                    }
                    //aceitar cliente novo 
                    socket = serverSocket.accept();
                    if(inscricaoDeJogadoresTerminou) break;

                    int jogadorID = lastID++;
                    ServerCommunication  cliente_novo = new ServerCommunication(socket, jogadorID, GestorGUI);
                    
                    if(cliente_novo.conectar()){
                        jogadoresSocket.put(jogadorID , cliente_novo);
                        cliente_novo.iniciarThread();
                    }
                    
                } catch (IOException ex) {
                    
                }
         
            }
    }
/**
 * Método que altera o estado do jogo para Terminado ou Não.
     * @param tterminarJogo Novo Estado do Jogo
 */ 
    public void setTerminarJogo(boolean tterminarJogo) {
        this.terminarJogo = tterminarJogo;
    }
}
  
