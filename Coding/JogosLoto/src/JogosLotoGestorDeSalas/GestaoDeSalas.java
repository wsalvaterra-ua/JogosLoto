/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author bil
 */
public class GestaoDeSalas {
    
 
    
//      public static void main(String[] args) {
//            JDialog frame = new modaAddlAposta(null, true);
//      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//      frame.setSize(560, 200);      
//      frame.setLocationRelativeTo(null);  
//      frame.setVisible(true);
//   }


   
        /**
 * Função que returna um número aleatório entre dois números passados como argumento
 * Referencia: https://www.baeldung.com/java-generating-random-numbers-in-range
 * @param  min número minimo(inclusive) a ser gerado aleatoriamente
 * @param  max número máximo(inclusive) a ser gerado aleatoriamente
 * 
 * @return  número aleatório 
 */
    private static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    
}
