/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;



import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;


/**
 *
 * @author bil
 */
public class ServerCommunication extends JogosLotoLivraria.SocketCommunicationStruct{
    private boolean terminouCartao;
    private String cartaoNumerosEncoded;
    private String nomeJogador;
    private Double aposta; 
    private final GSalaGUI GestorGUI;

    
    public ServerCommunication(Socket s ,int jogadorID,GSalaGUI GestorGUI){
        super(s);
        terminouCartao = false;
        this.aposta = -1.0;
        this.jogadorID = jogadorID;
        cartaoNumerosEncoded = null;

        this.GestorGUI = GestorGUI;
        
    }

    
    @Override
    public void run() {
        while(!terminarJogo){
            String socketMSGEntrada = null;

            
            try {
                socketMSGEntrada = entrada().readLine();
            } catch (IOException ex) {
                this.terminarJogo = true;
            }
            //primeiramente receber numero de cartao e propriedades da aposta 
            if(socketMSGEntrada != null && this.cartaoNumerosEncoded == null){
                System.out.println("Mensagem Entrada inicial: " + socketMSGEntrada);
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
            }else if(socketMSGEntrada != null ){
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

        } catch (IOException ex) {}

    }

    public Double getAposta() {
        return aposta;
    }
    public String getNomeJogador() {
        return nomeJogador;
    }

    public String getCartaoNumerosEncoded() {
        return cartaoNumerosEncoded;
    }
    public boolean terminouCartao(){
        return this.terminouCartao;
    }
    
    
}