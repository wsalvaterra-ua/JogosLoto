/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import JogosLotoLivraria.modalWait;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
  
// Server class
public class Server implements Runnable
{
    
    private boolean terminarJogo;
    protected boolean inscricaoDeJogadoresTerminou;

    private final GSalaGUI GestorGUI;
    private final HashMap<Integer,ServerCommunication> jogadoresSocket;

    ServerSocket serverSocket;
    
    public Server( GSalaGUI gestorDeSala) throws IOException{
        super();
        
        GestorGUI = gestorDeSala;
        serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(ServerCommunication.PORTA));

        terminarJogo = false;
        inscricaoDeJogadoresTerminou  = false;

        jogadoresSocket = new HashMap<>();
    }
    
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
                            if(numerosDecript == null)
                                System.out.println("Chave de Encriptacao Incorreta");
                            else{
                                if(numerosDecript.split(",").length == 15){
                                    finalistas.add(new String[]{Integer.toString(cliente.getJogadorID()),numerosDecript});
                                    GestorGUI.estado_BotaoSortear(false);
                                    //tempo para que os outros clientes se atualizem caso queiram se declarar como vencedores também
                                    if(!jaEsperouVencedores){
                                        modalWait modalWait = (new modalWait(this.GestorGUI, true, "A Processar Possíveis Vencedores", ServerCommunication.TEMPO_ESPERA_RESPOSTA));

                                        if(modalWait.getTempoRestante() > 0){
                                            System.out.println("tempo maior k 20");
                                            System.out.println(modalWait.getTempoRestante());
                                            Thread.sleep(modalWait.getTempoRestante());
                                        }
                                        jaEsperouVencedores = true;
                                    }
                                }else
                                    System.out.println("quantidade de numeros no cartao errada: " + numerosDecript.split(",").length);
                            }
                        } catch (InterruptedException ex) {
                                for(int b: jogadoresSocket.keySet())
                                    jogadoresSocket.get(b).setTerminarJogo(true);
                                this.terminarJogo = true;
                                System.out.println("temrinou jogo durante o timer checar clientes");
                        }
                        System.out.println("finallistas:" + finalistas.size());
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
                        for( ServerCommunication jogador : this.jogadoresSocket.values())
                            mensagem+= jogador.getNomeJogador() + ", ";
                        mensagem+=" tentaram terminar o jogo mas não têm números sorteados suficientes no cartão para o fazer";
                        System.out.println(mensagem);
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
            System.out.println("terminou");
            for(int b: jogadoresSocket.keySet())
                jogadoresSocket.get(b).setTerminarJogo(true);
            System.out.println("verificar cena que progrma envia msg e dps trmina termina imediatamente");
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   }


    public void enviarMSG(String msg) {
        for(int i: jogadoresSocket.keySet())
            jogadoresSocket.get(i).enviarMSG(msg);
    }
    


    public void iniciar_jogo(){
        this.enviarMSG("jogoIniciado->true");
    }
    public String getJogadorNome(int jogadorID){
        if(!this.jogadoresSocket.containsKey(jogadorID)){
            System.out.println("nome vazio?");
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

    public void setTerminarJogo(boolean tterminarJogo) {
        this.terminarJogo = tterminarJogo;
    }

 


}
  
