/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoJogador;

/**
 *
 * @author bil
 */
public  class Tema {
    public final java.awt.Color PANEL_CONTENT_BACKGROUND;
    public final java.awt.Color PANEL_OPCOES_BACKGROUND;
    public final java.awt.Color NUMERO_BACKGROUND;
    public final java.awt.Color NUMERO_HOVERIN_BACKGROUND;
    public final java.awt.Color NUMERO_HOVEROUT_BACKGROUND;
    public final Temas TEMA;
    public Tema(Temas tema){
        this.TEMA = tema;

        switch(tema){
    
            case DETROIT:
                PANEL_CONTENT_BACKGROUND = new java.awt.Color(239,30,30);
                PANEL_OPCOES_BACKGROUND =  new java.awt.Color(0,102,51);
                 NUMERO_BACKGROUND =  new java.awt.Color(255,153,0);
                NUMERO_HOVERIN_BACKGROUND = new java.awt.Color(206,125,3);
                NUMERO_HOVEROUT_BACKGROUND =  new java.awt.Color(255,153,0);
                break;
            case FUTURAMA:
                PANEL_CONTENT_BACKGROUND = new java.awt.Color(68,72,189);
                PANEL_OPCOES_BACKGROUND = new java.awt.Color(153,153,255);
                 NUMERO_BACKGROUND =  new java.awt.Color(0,153,51);
                NUMERO_HOVERIN_BACKGROUND = new java.awt.Color(2,94,33);
                NUMERO_HOVEROUT_BACKGROUND =  new java.awt.Color(0,153,51);
                 break;
            default:
                
                PANEL_CONTENT_BACKGROUND =  new java.awt.Color(255, 255, 0);
                PANEL_OPCOES_BACKGROUND =  new java.awt.Color(255, 102, 102);
                 NUMERO_BACKGROUND =  new java.awt.Color(102, 0, 204);
                NUMERO_HOVERIN_BACKGROUND =  new java.awt.Color(65, 3, 127);
                NUMERO_HOVEROUT_BACKGROUND =  new java.awt.Color(102, 0, 204);  
            
        }
        
    }
}
