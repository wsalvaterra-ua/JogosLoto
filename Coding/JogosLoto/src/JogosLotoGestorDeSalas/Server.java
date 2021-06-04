/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import static java.lang.Thread.sleep;
import java.text.*;
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
           Set<Integer> jogadores_IT = jogadoresSocket.keySet();
            for(int i: jogadores_IT){
                ServerCommunication cliente = jogadoresSocket.get(i);
                
                if(cliente.terminouCartao() && cliente.terminarJogo == false){
                    try {

    
                        String numerosDecript = JogosLotoJogador.EncriptacaoAES.decrypt(cliente.getCartaoNumerosEncoded(), cliente.getChaveDecriptar());
                        if(numerosDecript == null)
                            System.out.println("Chave de Encriptacao Incorreta");
                        else{
                            finalistas.add(new String[]{Integer.toString(cliente.getJogadorID()),numerosDecript});
                            GestorGUI.estado_BotaoSortear(false);
                        //tempo para que os outros clientes se atualizem caso queiram se declarar como vencedores também
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException ex) {
                            for(int b: jogadoresSocket.keySet())
                                jogadoresSocket.get(b).terminarJogo = true;
                            this.terminarJogo = true;
                            System.out.println("temrinou jogo durante o timer checar clientes");
                    }
                    System.out.println("finallistas:" + finalistas.size());
               }
                if(cliente.terminarJogo)
                    jogadoresSocket.remove(i);
                
                    
            }

            if(finalistas.size() > 0)
                if(this.GestorGUI.finalizarJogo(finalistas)){
                    for(int b: jogadoresSocket.keySet())
                        jogadoresSocket.get(b).terminarJogo = true;
                    this.terminarJogo = true;
                     break;
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
                for(int b: jogadoresSocket.keySet())
                    jogadoresSocket.get(b).terminarJogo = true;
                this.terminarJogo = true;
            }
        }
        this.enviarMSG("jogoTerminou->true");
        System.out.println("terminou");
        for(int b: jogadoresSocket.keySet())
            jogadoresSocket.get(b).terminarJogo = true;

        try {
            this.serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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

                    if(cliente_novo.iniciarComunicacao()) 
                        jogadoresSocket.put(jogadorID , cliente_novo);




                } catch (IOException ex) {
                    
                }
         
            }
            
            System.out.println("Parou de aceitar novas conexoes");
            
 
            
    }

    public void setTerminarJogo(boolean tterminarJogo) {
        this.terminarJogo = tterminarJogo;
    }

 


}