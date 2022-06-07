/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JogosLotoLivraria;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bil
 */
public class readConfig {
    /**
     * Número de Porta da porta a ser utilizada  default
    */
    private static final int PORTA = 5056;
    /**
     * Endereço do Servidor a ser utilizado
    */
    private static final String ENDERECO = "localhost";
    
    
    public static int getPorta() {

        String result = readConfig.getConf("(porta)\\s*=\\s*([0-9]{1,5})");
             
        if(result == null || Integer.valueOf(result) > 65535)
            return readConfig.PORTA;
        return Integer.valueOf(result);
            
  

    }

    
        public static String getEndereco()  {
        
      
        String pattern = "(ip)\\s*=\\s*((?:[0-9]{1,3}\\.){3}[0-9]{1,3})";
        String result = getConf(pattern);
        if(result == null)
            return readConfig.ENDERECO;
        return result;

    }
        
        
    /**
    * O jogador deve escolher o cartão invés de ter de ser o programa a faze-lo
    */
    public static boolean cardsIteratible() {

        String pattern = "(escolhercartao)\\s*=\\s*(true)";
        return readConfig.getConf(pattern) != null;

    }

    public static boolean hideBigLabel(){
        
        String pattern = "(showbiglabel)\\s*=\\s*(false)";
        return readConfig.getConf(pattern) != null;
        
    }

        private static String getConf(String pattern){
        String file = "config.conf";
        File ficheiro = new File(file);
        
        Scanner sc;
        try {
            sc = new Scanner(ficheiro);
        } catch (FileNotFoundException ex) {
            return null;
        }

        String currentLine;
            
        while (sc.hasNext()) {
                        currentLine = sc.nextLine();
            Pattern r = Pattern.compile(pattern);
            // Now create matcher object.
            Matcher m = r.matcher(currentLine.toLowerCase());

            if (m.find()) 
                return (m.group(2));
        }
        sc.close();
        return null;
        
    }
}
