/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoJogador;

import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author bil
 */
public class JLabelCartao extends javax.swing.JLabel{
   
    private Slot_Numero slot_numero;
    private boolean jogoIniciado;
    Tema TEMA;
    public JLabelCartao(Slot_Numero slot_numero,Temas tema){
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
    
    public void mouse_Hover(int acao){
        if(this.slot_numero != null && this.slot_numero.getMarcado())
            return;
        if(acao == 0)
            this.setBackground(this.TEMA.NUMERO_HOVERIN_BACKGROUND);
        if(acao == 1)
            this.setBackground(this.TEMA.NUMERO_HOVEROUT_BACKGROUND);
        
        
        
    }
    public void mouse_Clicked(){
        if(this.jogoIniciado)
            return;
        
        String resultado = (String)JOptionPane.showInputDialog( "Introduza o novo valor para o cartão! Para eliminar este cartão deixe o campo vazio!");
        if(resultado == null)
            return;
        if(resultado.length()>0)
            try {
                Integer.parseInt(resultado);
                if(Integer.valueOf( resultado) <0 || Integer.valueOf( resultado) > 90){
                        JOptionPane.showMessageDialog(this,"Introduza um número inteiro entre 0 e 90","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }
                this.slot_numero = new Slot_Numero(Integer.valueOf(resultado));
                this.setText(resultado);
                this.setOpaque(true);
                return;
            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this,"Introduza um número inteiro entre 0 e 90","Verifique os dados",javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        
        this.slot_numero = null;
        this.setText(" ");
        this.setOpaque(false);
        
    }
    public void marcarJLabel(){
        if(!jogoIniciado)
            return;
        this.setForeground(new java.awt.Color(242, 242, 242));
        this.setBackground(new java.awt.Color(153,153,153));
        
    }
    

    public Slot_Numero getSlot_numero() {
        return slot_numero;
    }

    public void setJogoIniciado(boolean jogoIniciado) {
        this.jogoIniciado = jogoIniciado;
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); 

    }
    
    
    
    
    
}
