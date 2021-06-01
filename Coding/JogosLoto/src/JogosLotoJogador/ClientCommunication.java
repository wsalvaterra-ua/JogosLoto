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
public class ClientCommunication extends SocketCommunicationStruct implements ActionListener{
    
    public ClientCommunication(){
        super();
        MSGEntrada = new ArrayList<>();
    }
    public  boolean conectar() {
        try {
            socket = new Socket(ENDERECO,PORTA);
            saida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException ex) {
            return false;
        }
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

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
