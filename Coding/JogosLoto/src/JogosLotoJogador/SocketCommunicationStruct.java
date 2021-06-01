/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoJogador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bil
 */
public abstract class SocketCommunicationStruct  extends TimerTask{
    
    protected Socket socket;
    public static final int PORTA = 5056;
    public static final String ENDERECO = "localhost";
    protected PrintWriter saida;
    protected BufferedReader entrada;
    protected ArrayList<String> MSGEntrada;
    private javax.swing.Timer timerSocket;
    public SocketCommunicationStruct() {

        
    }

    
    public static HashMap<String,String> decodificar(String inpt_){
        
        
        HashMap<String,String> dadosFiltrados = new HashMap();
        if(inpt_ == null)
            return dadosFiltrados;
        String[] dados = inpt_.split("(&)");
        System.out.println(dados);
        
        for(int i = 0; i<dados.length; i++)
            if((dados[i].split("->")).length==2)
                dadosFiltrados.put(dados[i].split("->")[0],dados[i].split("->")[1]);
        return dadosFiltrados;
    }


    public void terminarConexao() throws IOException {
        System.out.println("terminou coexao com o socket");
        entrada.close();
        saida.close();
        socket.close();
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
    public String lerMSGs(){
        if(MSGEntrada.size()<1)
            return null;
        
        String msg = MSGEntrada.get(0);
        MSGEntrada.remove(0);
        return msg;
    }

    
}
