/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import JogosLotoLivraria.SocketCommunicationStruct;
import JogosLotoLivraria.ModalGameScores;

import java.io.IOException;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

import javax.swing.JOptionPane;

/**
 * Classe que irá comunicar com o Servidor.

 * @author Rui Oliveira e William Salvaterra
 */
public class ClientCommunication extends SocketCommunicationStruct{
    private boolean temErro;

   
    private final JogadorGUI GUIJogo;
    /**
 * Método Construtor da Classe
     * @param GUIJogo JogadorGUI a ser utilizado
 */
    public ClientCommunication(JogadorGUI GUIJogo){
        super();
        temErro = false;
        this.GUIJogo = GUIJogo;
    }

 
/**
 * Método que é executado numa Thread diferente, fica a espera para receber mensagens do servidor. 
 * Essas informaçoes podem ser: Número Sorteado, idDoUtilizador,e a informaçao de que o jogo terminou em conjunto com os seus vencedores.
 * 
 */
    @Override
    public void run() {
        try {
            while(!temErro  && !this.isTerminarJogo()){
                String inpt_ = this.esperarMSG();
                if(this.isTerminarJogo())
                    break;
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
                        if(dados.get("jogoTerminou").equals("true")){
                            this.setTerminarJogo(true);
                            //estrutura de dado de vencedor a receber: ID,recompensa,Nome
                            Double valorpremio = -1.0;
                            if(dados.containsKey("vencedores"))
                                for(String vencedorIT : dados.get("vencedores").split(";")){
                                    String[] vencedor = vencedorIT.split(",");
                                    if(vencedor.length == 3)
                                        try {
                                            if(vencedor[2].length()<1)
                                                throw new NumberFormatException();
                                            vencedores.put(vencedor[2], Double.parseDouble(vencedor[1]));
                                            if( Integer.valueOf(vencedor[0]) == this.getJogadorID() ){
                                                nomeDoJogador = vencedor[2];
                                                valorpremio = Double.parseDouble(vencedor[1]);
                                            }
                                               
                                        } catch (NumberFormatException e) {
                                        }
                                }
                            if(valorpremio>0.0){
                                JOptionPane.showMessageDialog(GUIJogo,  "Parabéns!\nO jogo terminou e tu ganhaste " + valorpremio +".","Ganhaste!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            }else if( valorpremio==0.0){
                                JOptionPane.showMessageDialog(GUIJogo,  "Parabéns!\nO jogo terminou e tu ganhaste.","Ganhaste!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            }
                            else if( valorpremio<0.0){
                                JOptionPane.showMessageDialog(GUIJogo,  "Jogo terminou e infelizmente não ganhaste.","Perdeste!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            }
                            ModalGameScores myDialog = new ModalGameScores(GUIJogo, true,vencedores , nomeDoJogador);
                            myDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            myDialog.setLocationRelativeTo(GUIJogo);  
                            myDialog.setVisible(true);
                      
                            switch(myDialog.acao){
                                case 0:
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
            JOptionPane.showMessageDialog(GUIJogo,"Houve um erro de conexão! Jogo será recomeçado! Conecte-se novamente ao servidor","Erro de Conexão",javax.swing.JOptionPane.WARNING_MESSAGE);
            GUIJogo.resetarNumeros();
        }
        try {
            this.terminarConexao();
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
