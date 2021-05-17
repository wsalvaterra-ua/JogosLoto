/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author bil
 */
public class SessaoDeJogoDeLoto {
    private int numeroMax, numeroMin;
    private final ArrayList<Integer> numerosSorteados;
    private final HashMap<Integer,Integer> apostasFeitas;
    public SessaoDeJogoDeLoto(){
         numerosSorteados = new ArrayList<>();
         apostasFeitas = new HashMap<>();
         numeroMax = 90;
         numeroMin = 1;
    }
    public boolean sortearNumero(int numero){
        
        if(numerosSorteados.contains(numero))
            
            return false;
        numerosSorteados.add(randomNum(numeroMin, numeroMax));
        return true;
    }
    public void adicionarAposta(int identificacao, int valorApostado ){
        apostasFeitas.put(identificacao, valorApostado);
    }
    public int terminarJogo(int vencedorID){
        if(numerosSorteados.size()<15)
            return -1;

        int somaApostas = 0;
        for(int apostador : apostasFeitas.keySet())
            if(apostador != vencedorID)
                somaApostas+= apostasFeitas.get(apostador);
            
        return apostasFeitas.get(vencedorID) + (somaApostas/ (apostasFeitas.size()));
    }
    public HashMap<Integer,Integer> terminarJogo(ArrayList<Integer> vencedoresID){
        if(numerosSorteados.size()<15)
            return null;

        int somaApostas = 0;
        for(int apostador : apostasFeitas.keySet())
            if(!vencedoresID.contains(apostador))
                somaApostas+= apostasFeitas.get(apostador);
        
        
        
        HashMap<Integer,Integer> recompensas = new HashMap<>();
        for(int apostador : apostasFeitas.keySet())
            if(vencedoresID.contains(apostador)){
                int recompensa = apostasFeitas.get(apostador) + 
                        (somaApostas/((apostasFeitas.size() - vencedoresID.size()>0 ) ? apostasFeitas.size() - vencedoresID.size() : 1));
                recompensas.put(apostador, recompensa);
            }
        return recompensas;
    }
    
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
