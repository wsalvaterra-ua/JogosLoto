/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoJogador;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * Esta Classe herda a classe JLabel e tem propriedades e métodos criadas para facilitar o uso de Jlabels num Jogo de Loto
 * @author bil
 */
public class JLabelCartao extends javax.swing.JLabel{
/**
 * Objeto Slot_Número a ser utilizada pela Instancia
 */
    private Slot_Numero slot_numero;
/**
 * Indica se jogo foi iniciado ou não. 
 */
    private boolean jogoIniciado;
/**
 * Tema a ser utilizado no jogo 
 */
    private Tema TEMA;
    private  final int localizacaoColuna;

    
    
     public JLabelCartao(Temas tema){
        super();
        localizacaoColuna = -1;

        this.TEMA = new Tema(tema);
        this.setBackground(this.TEMA.NUMERO_BACKGROUND);
        this.setFont(new java.awt.Font("Tahoma", 0, 53)); // NOI18N
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 4));
        this.setOpaque(false);
        this.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        this.setText("00");
           
        this.setOpaque(true);
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
              mouse_Hover(0);

        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {

            mouse_Hover(1);

        }

    });
     }
/**
 * Construtor do objeto  JLabelCartão
     * @param slot_numero objeto Slot_Numero a ser utilizado
     * @param tema Tema a ser utilizado durante o jogo.
 */
    public JLabelCartao(Slot_Numero slot_numero,Temas tema, int localizacaoColuna){
        super();
        this.TEMA = new Tema(tema);
        this.slot_numero = slot_numero;
        this.jogoIniciado = false;
        this.setBackground(this.TEMA.NUMERO_BACKGROUND);
        this.setFont(new java.awt.Font("Tahoma", 0, 53)); // NOI18N
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 4));
        this.setOpaque(false);
        this.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        this.localizacaoColuna = localizacaoColuna;
        
        if(slot_numero != null){
            this.setText(Integer.toString(slot_numero.getNumero()));
           
            this.setOpaque(true);
        }
        else
            this.setText(" ");

        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); 
        this.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            mouse_Clicked();

        }
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
              mouse_Hover(0);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            mouse_Hover(1);
        }

    });
     
    }

    public void setTEMA(Tema TEMA) {
        this.TEMA = TEMA;
        this.setBackground(this.TEMA.NUMERO_BACKGROUND);
    }
    private void mouse_Hover(int acao){
        if(this.slot_numero != null && this.slot_numero.getMarcado())
            return;
        if(acao == 0)
            this.setBackground(this.TEMA.NUMERO_HOVERIN_BACKGROUND);
        if(acao == 1)
            this.setBackground(this.TEMA.NUMERO_HOVEROUT_BACKGROUND);
    }

    private void mouse_Clicked(){
        if(this.jogoIniciado)
            return;
        
        String resultado = (String)JOptionPane.showInputDialog( "Introduza o novo valor para o cartão! Para eliminar este cartão deixe o campo vazio!");
        if(resultado == null)
            return;
        if(resultado.length()>0)
            try {
                Integer.parseInt(resultado);
                if(Integer.valueOf( resultado) <1 || Integer.valueOf( resultado) > 90){
                        JOptionPane.showMessageDialog(this,"Somente são válidos números inteiros entre 1 e 90","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }
                if(Integer.valueOf( resultado) < Cartao.getColumnMin(localizacaoColuna) || Integer.valueOf( resultado) > Cartao.getColumnMax(localizacaoColuna,JogadorGUI.COLUNAS_DIM)){
                    JOptionPane.showMessageDialog(this,"Nesta coluna só são válidos números entre " + Cartao.getColumnMin(localizacaoColuna)  + " e " + Cartao.getColumnMax(localizacaoColuna,JogadorGUI.COLUNAS_DIM) ,"Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(this.slot_numero !=null)
                     this.slot_numero.setNumero(Integer.valueOf(resultado));
                else 
                    this.slot_numero = new Slot_Numero(Integer.valueOf(resultado));
                this.setText(resultado);
                this.setOpaque(true);
                return;
            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this,"Introduza um número inteiro entre 1 e 90","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        
        this.slot_numero = null;
        this.setText(" ");
        this.setOpaque(false);
        
    }
/**
 * Método que define o número contido nesta instancia como marcado e alterando as cores do objeto de forma a deixar , 
 */
    public void marcarJLabel(boolean numeroNovo){
        if(!jogoIniciado)
            return;
        this.slot_numero.setMarcado(true);
        if(numeroNovo){
            this.setBackground(this.TEMA.NUMERO_ACERTADO_BACKGROUND);
            this.setForeground(this.TEMA.NUMERO_MARCADO_FOREGROUND);
            return;
        }
            this.setBackground(this.TEMA.NUMERO_MARCADO_BACKGROUND);
    }
    
    /**
 * Método que altera a Label para o estado inicial antes do jogo ser iniciado
 * 
 */
    public void resetarJLabel(){
        this.jogoIniciado = false;
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.setBackground(this.TEMA.NUMERO_BACKGROUND);
        this.setForeground(Color.BLACK);
        if(this.slot_numero != null)
            this.slot_numero.setMarcado(false);
    
    }
    
/**
 * Método retorna o Slot_Numero contido nesta instancia
     * @return  o Slot_Numero contido nesta instancia
 */
    public Slot_Numero getSlot_numero() {
        return slot_numero;
    }
/**
 * Método que define a propriedade jogoIniciado como True ou False
     * @param jogoIniciado novo estado da propriedade da classe jogoIniciado
 */
    public void setJogoIniciado(boolean jogoIniciado) {
        this.jogoIniciado = jogoIniciado;
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 

    }
    
}
