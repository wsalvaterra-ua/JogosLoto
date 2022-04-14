/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoLivraria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *Classe  com as propriedades básicas que tanto o lado servidor quanto o lado cliente precisam, uma socket, uma chave para decriptar e uma ID
 * @author William Salvaterra e Rui Oliveira
 *
 */
public abstract class SocketCommunicationStruct implements Runnable{
    
    private Socket socket;
    /**
     * Número de Porta da porta a ser utilizada  default
    */
    private static final int PORTA = 5056;
    /**
     * Endereço do Servidor a ser utilizado
    */
    private static final String ENDERECO = "localhost";
    private PrintWriter saida;
    private BufferedReader entrada;
    private boolean terminarJogo;
    private String chaveDecriptar;
    private int jogadorID;
    
/**
 * Invervalo de tempo em que os programas irão buscar novas mensagens nos sockets
 */ 
    public  static int INTERVALO_ATUALIZACAO = 200;
/**
 *Tempo que um programa irá esperar mensagem do servido
 */ 
    public  static int TEMPO_ESPERA_RESPOSTA = 2000;
/**
 * Construtor da classe
     * @param socket Socket a ser utilizado pela classe
 */ 

    public SocketCommunicationStruct(Socket socket) {
        this.socket = socket;
        terminarJogo = false;
        this.chaveDecriptar = null;  
    }
/**
 * Construtor da classe
 */ 
    public SocketCommunicationStruct() {
        this.socket = null;
        terminarJogo = false;
        this.chaveDecriptar = null;
    }
/**
 * Classe que conecta o socket ao servidor
 * Este cria um socket novo se o mesmo não tiver sido passado através do construtor
 */ 
    public synchronized boolean conectar(){
        try {
            if(socket == null)
                socket = new Socket(this.ENDERECO(),PORTA);
            saida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
/**
 * Método que inicia uma nova thread
 */ 
    public synchronized void iniciarThread(){
        Thread t = new Thread(this);
        t.start();
    }
/**
 * Método que dá um tempo limite para o socket receber uma nova mensagem
     * @param tempo Tempo desejado
 */ 
    public synchronized void setSocketTimeout(int tempo) throws SocketException{
        this.socket.setSoTimeout(tempo);
        
    }
    
/**
 *  Método que decodifica uma mensagem no formato : Chave->valor(&)Chave2->valor2
     * @param inpt_ Mensagem a ser decodificada
     * @return Retorna a mensagem decodificada em Hashmap
 */ 
    public static HashMap<String,String> decodificar(String inpt_){
        HashMap<String,String> dadosFiltrados = new HashMap();
        if(inpt_ == null)
            return dadosFiltrados;
        String[] dados = inpt_.split("\\(&\\)");
        for(int i = 0; i<dados.length; i++)
            if((dados[i].split("->")).length==2)
                dadosFiltrados.put(dados[i].split("->")[0],dados[i].split("->")[1]);
        return dadosFiltrados;
    }
/**
 * Termina Conexão do Socket
     * @throws java.io.IOException
 */ 
    public synchronized void terminarConexao() throws IOException {
        entrada.close();
        saida.close();
        socket.close();
    }
/**
 * Envia dada mensagem para o outro par do socket
     * @param msg Mensagem a ser enviada
 */ 
    public void enviarMSG(String msg) {
        saida.println(msg);
    }
/**
 *  Este método bloqueia o código até que o outro lado da conexão envie uma mensagem
     * @return Mensagem Recebida
 */ 
    public String esperarMSG() throws IOException{
        String msg =  entrada.readLine();
        return msg;
    }
/**
 * Este método altera o estado do jogo para terminado ou nao
     * @param terminarJogo Novo estado do jogo
 */ 
    public synchronized void setTerminarJogo(boolean terminarJogo) {
        this.terminarJogo = terminarJogo;
    }
/**
 * Método que retorna informacao se jogador terminou jogo ou não
     * @return  true se jogo tiver terminado
 */ 
    public boolean isTerminarJogo() {
        return terminarJogo;
    }
/**
 * Método que atribui a chave para decriptar os números
     * @param chave Chave a ser atribuida
 */ 
    public synchronized void setChave(String chave){
        if(this.chaveDecriptar == null)
            this.chaveDecriptar = chave;
    }
 
/**
 * Método que retorna chave para decriptar numeros do cartao
     * @return Retorna chave
 */ 
    public String getChaveDecriptar() {
        return chaveDecriptar;
    }
/**
 * Método que atribui uma ID ao jogador
     * @param jogadorID ID do jogador
 */ 
    public synchronized void setJogadorID(int jogadorID) {
        this.jogadorID = jogadorID;
    }
/**
 * Método que retorna o ID do jogador
     * @return ID do jogaodr
 */ 
    public int getJogadorID() {
        return jogadorID;
    }
    
    
    public int PORTA() {
  
        String file ="config.conf";

        int iterated_lines= 0;
        BufferedReader reader;
           try {
                reader = new BufferedReader(new FileReader(file));
                String currentLine = reader.readLine();

                            // String to be scanned to find the pattern.
                String pattern = "^(porta)\\s*=\\s*(\\d+)$";
          //        String pattern = "^([a-z]+)\\\\s*=\\\\s*(\\\\S+)$";
                // Create a Pattern object
                iterated_lines++;
                Pattern r = Pattern.compile(pattern);

                // Now create matcher object.
                Matcher m = r.matcher(currentLine.toLowerCase());
                reader.close();
                if (m.find( )) {
                    return Integer.valueOf(m.group(2));
                }else {
                   return SocketCommunicationStruct.PORTA ;
                }

           } catch (FileNotFoundException ex) {
                       return SocketCommunicationStruct.PORTA ;
           } catch (IOException ex) {
                return SocketCommunicationStruct.PORTA;
           }
}
    
        public static String ENDERECO()  {
        
        String file = "config.conf";
        File ficheiro = new File(file);
                
                
        Scanner sc = null;
        try {
            sc = new Scanner(ficheiro);
        } catch (FileNotFoundException ex) {
            return SocketCommunicationStruct.ENDERECO;
        }

        int iterated_lines = 0;
        // String to be scanned to find the pattern.
        String pattern = "^(endereco)\\s*=\\s*(\\S+)$";
        //        String pattern = "^([a-z]+)\\\\s*=\\\\s*(\\\\S+)$";
        // Create a Pattern object
        String currentLine = sc.nextLine();
        while (iterated_lines < 30) {
            
//            iterated_lines++;
            Pattern r = Pattern.compile(pattern);
            // Now create matcher object.
            Matcher m = r.matcher(currentLine.toLowerCase());

            if (m.find()) {
                return (m.group(2));
            }
            currentLine = sc.nextLine();
        }
        sc.close();
        return SocketCommunicationStruct.ENDERECO;

    }



    
}
