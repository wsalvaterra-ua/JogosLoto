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
            if(jogadoresSocket.size()<1){
                System.out.println("Nnenhum cliente ingressou se");
                try {
                    this.serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }   
            while(!terminarJogo){
                ArrayList<String[]> finalistas = new ArrayList<>();
               int[] jogadores_IT = new int[jogadoresSocket.size()];
               {
                    int i = 0;
                    for (int c : jogadoresSocket.keySet()) {
                         jogadores_IT[i++] = c;
                    }
                }

                for(int i: jogadores_IT){
                    ServerCommunication cliente = jogadoresSocket.get(i);

                    if(cliente.terminouCartao() && cliente.isTerminarJogo() == false){
                        try {


                            String numerosDecript = JogosLotoLivraria.EncriptacaoAES.decrypt(cliente.getCartaoNumerosEncoded(), cliente.getChaveDecriptar());
                            if(numerosDecript == null)
                                System.out.println("Chave de Encriptacao Incorreta");
                            else{
                                if(numerosDecript.split(",").length == 15){
                                    finalistas.add(new String[]{Integer.toString(cliente.getJogadorID()),numerosDecript});
                                    GestorGUI.estado_BotaoSortear(false);
                                    //tempo para que os outros clientes se atualizem caso queiram se declarar como vencedores também
                                    modalWait modalWait = (new modalWait(this.GestorGUI, true, "A Processar Possíveis Vencedores", ServerCommunication.TEMPO_ESPERA_RESPOSTA));

                                    if(modalWait.getTempoRestante() > 0){
                                        System.out.println("tempo maior k 20");
                                        System.out.println(modalWait.getTempoRestante());
                                        Thread.sleep(modalWait.getTempoRestante());

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
                    if(cliente.isTerminarJogo())
                        jogadoresSocket.remove(i);
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
                    Thread.sleep(ServerCommunication.INTERVALO_ATUALIZACAO);
                } catch (InterruptedException ex) {
                    this.terminarJogo = true;
                }
            }
            this.enviarMSG("jogoTerminou->true");
            System.out.println("terminou");
            for(int b: jogadoresSocket.keySet())
                jogadoresSocket.get(b).setTerminarJogo(true);

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
    

    public void receberClientes() {
// referencia: https://www.baeldung.com/java-measure-elapsed-time


            try {
                serverSocket.setSoTimeout(400);
            } catch (SocketException ex) {
               
                JOptionPane.showMessageDialog(this.GestorGUI,  "Ocorreu um erro, por favor inicie um novo jogo!","Erro!",javax.swing.JOptionPane.ERROR_MESSAGE); 
                return;
            }
            while( !inscricaoDeJogadoresTerminou){
                try {

                    Socket socket;

                    System.out.println("a espera" );

                    socket = serverSocket.accept();
                    if(inscricaoDeJogadoresTerminou) break;

                    int jogadorID = jogadoresSocket.size()+1;
                    ServerCommunication  cliente_novo = new ServerCommunication(socket, jogadorID, GestorGUI);

                    if(cliente_novo.conectar()){
                        jogadoresSocket.put(jogadorID , cliente_novo);
                        cliente_novo.iniciarThread();
                    }



                } catch (IOException ex) {
                    
                }
         
            }
            
            System.out.println("Parou de aceitar novas conexoes");
            
 
            
    }

    public void setTerminarJogo(boolean tterminarJogo) {
        this.terminarJogo = tterminarJogo;
    }

 


}
  
