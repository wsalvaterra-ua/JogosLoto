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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bil
 */
public class ClientCommunication implements ActionListener{
    
    private Socket socket;
    private static final int PORTA = 80;
    private static final String ENDERECO = "localhost";
    private PrintWriter saida;
    private BufferedReader entrada;
    private ArrayList<String> MSGEntrada;
    public ClientCommunication(){

        
    }
    public final boolean conectar() {
        try {
            socket = new Socket(ENDERECO,PORTA);
            saida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    public static HashMap<String,String> decodificar(String inpt_){

        HashMap<String,String> dadosFiltrados = new HashMap();
        if(inpt_ == null)
            return dadosFiltrados;
        String[] dados = inpt_.split(";");
        
        for(int i = 0; i<dados.length; i++)
            if(dados[i].length()==2)
                dadosFiltrados.put(dados[i].split(":")[0],dados[i].split(":")[1]);
        return dadosFiltrados;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            while(entrada.ready()){
                String texto = entrada.readLine();
                if(texto != null)
                    MSGEntrada.add(texto);
            }
        } catch (IOException ex) {
            
        }
        
        
    }
    
    
    public void terminarConexao() throws IOException {
        entrada.close();
        saida.close();
        socket.close();
    }
    public void enviarMSG(String msg) {
        saida.println(msg);
    }
    public String esperarMSG() throws IOException{
        
        return entrada.readLine();
        
    }
    public String lerMSGs(){
        if(MSGEntrada.size()<1)
            return null;
        
        String msg = MSGEntrada.get(0);
        MSGEntrada.remove(0);
        return msg;
    }
    
    
}
