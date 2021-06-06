/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoLivraria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import java.util.HashMap;


/**
 *
 * @author bil
 */
public abstract class SocketCommunicationStruct implements Runnable{
    
    private Socket socket;
    public static final int PORTA = 5056;
    public static final String ENDERECO = "localhost";
    private PrintWriter saida;
    private BufferedReader entrada;
    protected boolean terminarJogo;
    protected String chaveDecriptar;
    protected int jogadorID;
    public  static int INTERVALO_ATUALIZACAO = 200;
    public  static int TEMPO_ESPERA_RESPOSTA = 2000;
    
    public SocketCommunicationStruct(Socket socket) {
        this.socket = socket;
        terminarJogo = false;
        this.chaveDecriptar = null;
        
    }
    public SocketCommunicationStruct() {
        this.socket = null;
        terminarJogo = false;
        this.chaveDecriptar = null;
    }

    public synchronized boolean conectar(){
        try {
            if(socket == null)
                socket = new Socket(ENDERECO,PORTA);
            saida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    public synchronized void iniciarThread(){
        Thread t = new Thread(this);
        t.start();
    }
    public synchronized void setSocketTimeout(int tempo) throws SocketException{
        this.socket.setSoTimeout(tempo);
        
    }
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
    
    public synchronized void terminarConexao() throws IOException {
        entrada.close();
        saida.close();
        socket.close();
        System.out.println("terminou coexao com o socket");
    }
    public void enviarMSG(String msg) {
        System.out.println("Enviar MSG:"+ msg);
        if(saida == null)
            System.out.println("saida ta nulo");
        saida.println(msg);
    }
    public String esperarMSG() throws IOException{
        String msg =  entrada.readLine();
        System.out.println(msg);
        return msg;
    }

    public synchronized PrintWriter saida() {
        return saida;
    }

    public synchronized BufferedReader entrada() {
        return entrada;
    }

    public synchronized void setTerminarJogo(boolean terminarJogo) {
        this.terminarJogo = terminarJogo;
    }

    public boolean isTerminarJogo() {
        return terminarJogo;
    }
    public synchronized void addChave(String chave){
        if(this.chaveDecriptar == null)
            this.chaveDecriptar = chave;
    }
    
    public String getChaveDecriptar() {
        return chaveDecriptar;
    }

    public synchronized void setJogadorID(int jogadorID) {
        this.jogadorID = jogadorID;
    }

    public int getJogadorID() {
        return jogadorID;
    }
    
    
  

    
}
