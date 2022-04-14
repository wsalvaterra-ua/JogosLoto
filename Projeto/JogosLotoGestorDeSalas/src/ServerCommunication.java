/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;


/**
 * Classe que conectará cada cliente ao servidor
 * Sendo responsável por enviar e receber mensagens para  o cliente
 * @author Rui Oliveira e William Salvaterra
 */
public class ServerCommunication extends JogosLotoLivraria.SocketCommunicationStruct{
    private boolean terminouCartao;
    private String cartaoNumerosEncoded;
    private String nomeJogador;
    private Double aposta; 
    private final GSalaGUI GestorGUI;

/**
 *  Método Construtor da Classe.
     * @param s Socket criado para conectar servidor e cliente.
     * @param jogadorID ID atribuida ao jogador
     * @param GestorGUI Gestor De Sala utilizada
     * 
 */  
    public ServerCommunication(Socket s ,int jogadorID,GSalaGUI GestorGUI){
        super(s);
        terminouCartao = false;
        this.aposta = -1.0;
        super.setJogadorID(jogadorID);
        cartaoNumerosEncoded = null;
        
        this.GestorGUI = GestorGUI;
        
    }

 /**
 * Método que será executado numa thread diferente.
 * Este método recebe as mensagens que o cliente envia e interpreta-a.
 * Este método interpreta a aposta e o nome de um jogador , os números do cartao, se o jogador temrinou o seu cartão e se o jogador saiu do jogo.
 */ 
    @Override
    public void run() {
        while(!this.isTerminarJogo()){
            String socketMSGEntrada = null;

            
            try {
                socketMSGEntrada = this.esperarMSG();
            } catch (IOException ex) {
                this.setTerminarJogo(true);
            }
            if(ServerCommunication.decodificar(socketMSGEntrada).containsKey("quitGame") || this.isTerminarJogo()){
                this.setTerminarJogo(true);
                break;
            }
            //primeiramente receber numero de cartao e propriedades da aposta 
            if(socketMSGEntrada != null && this.cartaoNumerosEncoded == null){
                HashMap<String,String> msgns = ServerCommunication.decodificar(socketMSGEntrada);

                if(msgns.containsKey("cartaoNumeros")){
                    this.cartaoNumerosEncoded = msgns.get("cartaoNumeros");
                    this.enviarMSG("numIdentificacao->"+ this.getJogadorID());
                }

                if(msgns.containsKey("apostaNome") && msgns.containsKey("apostaValor") ){
                    this.nomeJogador = msgns.get("apostaNome");
                    this.aposta = Double.valueOf(msgns.get("apostaValor"));
                    GestorGUI.addApostas(this.getJogadorID(), this.aposta);
                }
                socketMSGEntrada = null;
            }else if(socketMSGEntrada != null ){
                    HashMap<String,String> msgns = ServerCommunication.decodificar(socketMSGEntrada);
                    if(msgns.containsKey("terminou") && msgns.containsKey("chave")){
                        this.setChave(msgns.get("chave"))   ;
                        this.terminouCartao = true;
                    }
            }
            try {
                Thread.sleep(ServerCommunication.INTERVALO_ATUALIZACAO);
            } catch (InterruptedException ex) {
                this.setTerminarJogo(true);
            }
        }
        try {
            this.terminarConexao();

        } catch (IOException ex) {}

    }
/**
 * Método que retorna a aposta feita pelo jogador.
     * @return posta feita pelo jogador
 */ 
    public Double getAposta() {
        return aposta;
    }
/**
 * Método que retorna o nome do jogador.
     * @return Nome do jogador
 */ 
    public String getNomeJogador() {
        return nomeJogador;
    }
/**
 * Método que retorna os números do cartão do cliente encriptada
     * @return Números encriptados
 */
    public String getCartaoNumerosEncoded() {
        return cartaoNumerosEncoded;
    }
/**
 * Método que informa se o cliente terminou o cartão
     * @return True se o Jogador Terminou o Cartão, False se o Jogador não terminou cartão.
 */ 
    public boolean terminouCartao(){
        return this.terminouCartao;
    }
}