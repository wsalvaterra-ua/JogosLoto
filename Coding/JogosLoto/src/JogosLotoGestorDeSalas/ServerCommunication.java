/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import JogosLotoJogador.ClientCommunication;
import static JogosLotoJogador.SocketCommunicationStruct.ENDERECO;
import static JogosLotoJogador.SocketCommunicationStruct.PORTA;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bil
 */
public class ServerCommunication extends JogosLotoJogador.SocketCommunicationStruct implements Runnable{
    private boolean terminouCartao;
    public boolean terminarJogo;
    int jogadorID;
    Timer timer;
    String cartaoNumerosEncoded;
    String nomeJogador;
    Double aposta ; 
    private final GSalaGUI GestorGUI;
    boolean jogoIniciou;
    String chaveDecriptar;
 
    public ServerCommunication(Socket s ,int jogadorID,GSalaGUI GestorGUI){
        super();
        terminouCartao = false;
        this.socket = s;
        this.aposta = -1.0;
        this.jogadorID = jogadorID;
        cartaoNumerosEncoded = null;
        timer = new Timer(true);
        this.GestorGUI = GestorGUI;

        
        
//                 private javax.swing.Timer timerSocket;
//        timerSocket = new javax.swing.Timer(1000, this);
//        timerSocket.start(); 
        
    }
    public boolean iniciarComunicacao(){
                try {
            saida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            Thread t = new Thread(this);
            t.start();
            return true;
            
        } catch (IOException ex) {
            return false;
        }

    }
    
    @Override
    public void run() {
        String texto = null;
        
        if(terminarJogo)
            try {
                this.terminarConexao();
                timer.cancel();
                return;
            } catch (IOException ex) {
                System.out.println("Nao foi possivel terminar a conexao");
                
            }
        
        
        if(this.cartaoNumerosEncoded == null && !jogoIniciou)
            try {
                texto = entrada.readLine();
                System.out.println("Mensagem Entrada inicial: "+ texto);
                HashMap<String,String> msgns = ServerCommunication.decodificar(texto);
                System.out.println(msgns.keySet());
                
                if(msgns.containsKey("cartao")){
                    System.out.println("contem chave cartao");
                    this.cartaoNumerosEncoded = msgns.get("cartao");
                    this.enviarMSG("numIdentificacao->"+ this.jogadorID);
                    timer.schedule( this, 200);
                }

                if(msgns.containsKey("apostaNome"))
                    this.nomeJogador = msgns.get("apostaNome");
                if(msgns.containsKey("apostaValor") ){
                    this.aposta = Double.valueOf(msgns.get("apostaValor"));
                    GestorGUI.addApostas(jogadorID, Double.NEGATIVE_INFINITY);
                }
            } catch ( IOException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try {
            
            while(entrada.ready()){
                texto = entrada.readLine();
                System.out.println("Mensagem Entrada: "+ texto);
                if(texto != null){
                    HashMap<String,String> msgns = ServerCommunication.decodificar(texto);
                    if(this.cartaoNumerosEncoded == null ){
//                        socket.setSoTimeout(2000);
                        
                        if(msgns.containsKey("cartao")){
                            this.cartaoNumerosEncoded = msgns.get("cartao");
                            this.enviarMSG("numIdentificacao->"+ this.jogadorID);
                            timer.schedule( this, 200);
                        }

                        if(msgns.containsKey("apostaNome") && !jogoIniciou)
                            this.nomeJogador = msgns.get("apostaNome");
                        if(msgns.containsKey("apostaValor") && !jogoIniciou){
                            this.aposta = Double.valueOf(msgns.get("apostaValor"));
                            GestorGUI.addApostas(jogadorID, Double.NEGATIVE_INFINITY);
                        }
                        
                    }else{
                        if(msgns.containsKey("terminou") && msgns.containsKey("chave")){
                            this.terminouCartao = true;
                            this.chaveDecriptar = msgns.get("chave");
                        }
                    }
                    
                }
                    
            }
        } catch (IOException ex) {
            System.out.println("Conexao Falhou");
        }

    }
    
    public void setTerminarJogo(boolean terminarJogo) {
        this.terminarJogo = terminarJogo;
    }
    public boolean terminouCartao(){
        return this.terminouCartao;
        
    }
    
    
}
