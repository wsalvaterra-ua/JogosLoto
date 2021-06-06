/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoJogador;

import JogosLotoGestorDeSalas.ModalGameScores;
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
import javax.swing.JDialog;

import javax.swing.JOptionPane;

/**
 *
 * @author bil
 */
public class ClientCommunication extends SocketCommunicationStruct{
    private boolean temErro;
    private boolean terminarJogo; 
    //compartilhar idJogador
    private final JogadorGUI GUIJogo;
    public String chave;
    public int numIdentificacao;
    public ClientCommunication(JogadorGUI GUIJogo){
        super();
        MSGEntrada = new ArrayList<>();
        temErro = false;
        terminarJogo = false;
        this.GUIJogo = GUIJogo;
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
    public void run() {
        try {
            while(!temErro  && !terminarJogo){
                String inpt_ = entrada.readLine();
                if(inpt_ != null){
                    HashMap<String,String> dados = ClientCommunication.decodificar(inpt_);

                    if(dados.containsKey("numeroSorteado"))
                        try {
                            this.GUIJogo.sortearNumero(Integer.parseInt(dados.get("numeroSorteado")));
                        } catch (NumberFormatException n) {
                        }
                    
                    
                    
                    if(dados.containsKey("jogoTerminou")){
                        String nomeDoJogador = null;
                        HashMap<String, Double> vencedores = new HashMap<>();
                        System.out.println("dados de jogoterminou:" + dados.keySet());
                        if(dados.get("jogoTerminou").equals("true")){
                            this.terminarJogo = true;
                            //estrutura de dado de vencedor a receber: ID,recompensa,Nome
                            if(dados.containsKey("vencedores"))
                                for(String vencedorIT : dados.get("vencedores").split(";")){
                                    String[] vencedor = vencedorIT.split(",");
                                    if(vencedor.length == 3)
                                        try {
                                            if(vencedor[2].length()<1)
                                                throw new NumberFormatException();
                                            vencedores.put(vencedor[2], Double.parseDouble(vencedor[1]));
                                            if( Integer.valueOf(vencedor[0]) == this.numIdentificacao )
                                                nomeDoJogador = vencedor[2];
                                        } catch (NumberFormatException e) {
                                        }
                                }
                            
                            
                            ModalGameScores myDialog = new ModalGameScores(GUIJogo, true,vencedores , nomeDoJogador);
                            myDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            myDialog.setLocationRelativeTo(GUIJogo);  
                            myDialog.setVisible(true);
                      
                            switch(myDialog.acao){
                                case 0:
                                    GUIJogo.botoesEstadosModelos(0);
                                    GUIJogo.resetarNumeros();
                                    break;
                                case 1:
                                    GUIJogo.botoesEstadosModelos(0);
                                    GUIJogo.novoJogo();
                                    break;
                                case 2:
                                    GUIJogo.botoesEstadosModelos(1);
                                    break;
                                case 3:
                                    GUIJogo.dispose();
                                    break;
                                default:
                                    GUIJogo.botoesEstadosModelos(1);
                                    break;
                                
                            }
                      
                        }
                    } 
                }     
                Thread.sleep(ClientCommunication.INTERVALO_ATUALIZACAO);
            }
        } catch (IOException | InterruptedException ex) {
            temErro = true;
        } 
        if(temErro){
            JOptionPane.showMessageDialog(GUIJogo,"Houve um erro de conexão! Jogo será recomeçado!","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
            GUIJogo.novoJogo();
        }
        try {
            this.terminarConexao();
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void terminarJogo(){
        
        this.terminarJogo = true;
    }


}
