/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Classe com propriedades que armazenam cores de acordo com o tema escolhido pelo utilizador
 * @author  William Salvaterra e Rui Oliveira
 */
public  class Tema {
/**
 * Cor de Fundo  do painel que contem os números
 */
    public final java.awt.Color PANEL_CONTENT_BACKGROUND;
/**
 * Cor de Fundo  do painel de opções
 */
    public final java.awt.Color PANEL_OPCOES_BACKGROUND;
/**
 * Cor de Fundo  dos JLabel dos números
 */
    public final java.awt.Color NUMERO_BACKGROUND;
/**
 * Cor de Fundo  ao fazer HOver in nos números
 */
    public final java.awt.Color NUMERO_HOVERIN_BACKGROUND;
/**
 * Cor de Fundo  ao fazer HOver out nos números
 */
    public final java.awt.Color NUMERO_HOVEROUT_BACKGROUND;
/**
 * Cor de Fundo  do ultimo número acertado
 */
    public final java.awt.Color NUMERO_ACERTADO_BACKGROUND;
/**
 * Cor de Fundo  de um numero já marcado
 */
    public final java.awt.Color NUMERO_MARCADO_BACKGROUND;
/**
 * Cor de Texto de um numero já marcado
 */
    public final java.awt.Color NUMERO_MARCADO_FOREGROUND;
/**
 *Tema a ser utilizado
 */
    public final Temas TEMA;
    
/**
 * Construtor que cria as cores a serem utilizadas de acordo com o tema escolhido pelo utilizador
     * @param tema Tema a ser utilizado
 */
    public Tema(Temas tema){
        this.TEMA = tema;

        switch(tema){
    
            case DETROIT:
                PANEL_CONTENT_BACKGROUND = new java.awt.Color(239,30,30);
                PANEL_OPCOES_BACKGROUND =  new java.awt.Color(0,102,51);
                 NUMERO_BACKGROUND =  new java.awt.Color(255,153,0);
                NUMERO_HOVERIN_BACKGROUND = new java.awt.Color(206,125,3);
                NUMERO_HOVEROUT_BACKGROUND =  new java.awt.Color(255,153,0);
                NUMERO_ACERTADO_BACKGROUND = new java.awt.Color(102,102,0);
                NUMERO_MARCADO_BACKGROUND = new java.awt.Color(153,153,153);
                NUMERO_MARCADO_FOREGROUND = new java.awt.Color(242, 242, 242);
                break;
            case FUTURAMA:
                PANEL_CONTENT_BACKGROUND = new java.awt.Color(68,72,189);
                PANEL_OPCOES_BACKGROUND = new java.awt.Color(153,153,255);
                 NUMERO_BACKGROUND =  new java.awt.Color(0,153,51);
                NUMERO_HOVERIN_BACKGROUND = new java.awt.Color(2,94,33);
                NUMERO_HOVEROUT_BACKGROUND =  new java.awt.Color(0,153,51);
                NUMERO_ACERTADO_BACKGROUND = new java.awt.Color(102,102,0);
                NUMERO_MARCADO_BACKGROUND = new java.awt.Color(153,153,153);
                NUMERO_MARCADO_FOREGROUND = new java.awt.Color(242, 242, 242);
                 break;
            default:
                
                PANEL_CONTENT_BACKGROUND =  new java.awt.Color(255, 255, 0);
                PANEL_OPCOES_BACKGROUND =  new java.awt.Color(255, 102, 102);
                 NUMERO_BACKGROUND =  new java.awt.Color(102, 0, 204);
                NUMERO_HOVERIN_BACKGROUND =  new java.awt.Color(65, 3, 127);
                NUMERO_HOVEROUT_BACKGROUND =  new java.awt.Color(102, 0, 204);
                NUMERO_ACERTADO_BACKGROUND = new java.awt.Color(102,102,0);
                NUMERO_MARCADO_BACKGROUND = new java.awt.Color(153,153,153);
                NUMERO_MARCADO_FOREGROUND = new java.awt.Color(242, 242, 242);
        }
        
    }
}
