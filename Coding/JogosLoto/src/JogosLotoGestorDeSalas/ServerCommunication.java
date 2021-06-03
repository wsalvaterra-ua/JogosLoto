/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bil
 */
public class ServerCommunication extends JogosLotoJogador.SocketCommunicationStruct implements Runnable{
    private boolean terminouCartao;
    public boolean terminarJogo;
    private int jogadorID;
    private String cartaoNumerosEncoded;
    private String nomeJogador;
    private Double aposta ; 
    private final GSalaGUI GestorGUI;
    private String chaveDecriptar;
 
    public ServerCommunication(Socket s ,int jogadorID,GSalaGUI GestorGUI){
        super();
        terminouCartao = false;
        this.socket = s;
        this.aposta = -1.0;
        this.jogadorID = jogadorID;
        cartaoNumerosEncoded = null;

        this.GestorGUI = GestorGUI;
        
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
        while(!terminarJogo){
            String socketMSGEntrada = null;

            
            try {
                socketMSGEntrada = entrada.readLine();
            } catch (IOException ex) {
                this.terminarJogo = true;
            }
            if(this.cartaoNumerosEncoded == null){
                System.out.println("Mensagem Entrada inicial: "+ socketMSGEntrada);
                HashMap<String,String> msgns = ServerCommunication.decodificar(socketMSGEntrada);

                if(msgns.containsKey("cartaoNumeros")){
                    this.cartaoNumerosEncoded = msgns.get("cartaoNumeros");
                    this.enviarMSG("numIdentificacao->"+ this.jogadorID);
                }

                if(msgns.containsKey("apostaNome") && msgns.containsKey("apostaValor") ){
                    this.nomeJogador = msgns.get("apostaNome");
                    this.aposta = Double.valueOf(msgns.get("apostaValor"));
                    GestorGUI.addApostas(jogadorID, this.aposta);
                }
                socketMSGEntrada = null;
            }

                if(socketMSGEntrada != null){
                    System.out.println("Mensagem Entrada: "+ socketMSGEntrada);
  
                        HashMap<String,String> msgns = ServerCommunication.decodificar(socketMSGEntrada);

                        if(msgns.containsKey("terminou") && msgns.containsKey("chave")){
                            
                            this.chaveDecriptar = msgns.get("chave");
                            this.terminouCartao = true;

                        }
                    
                }
                
            try {
                Thread.sleep(ServerCommunication.INTERVALO_ATUALIZACAO);
            } catch (InterruptedException ex) {
                this.terminarJogo = true;
            }

        }
        try {
            System.out.println("Terminou jogo");
            this.terminarConexao();

        } catch (IOException ex) {
           
            System.out.println("erro ao terminar");
        }

    }

    public Double getAposta() {
        return aposta;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public int getJogadorID() {
        return jogadorID;
    }
    
    

    public String getChaveDecriptar() {
        return chaveDecriptar;
    }

    public String getCartaoNumerosEncoded() {
        return cartaoNumerosEncoded;
    }
    
 
    public boolean terminouCartao(){
        return this.terminouCartao;
        
    }
    
    
}
