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
    
    
    public SessaoDeJogoDeLoto( int min, int max){
         numerosSorteados = new ArrayList<>();
         apostasFeitas = new HashMap<>();
         numeroMin = min;
         numeroMax = max;
    }
    public boolean sortearNumero(int numero){
        if(numerosSorteados.contains(numero))
            return false;
        if(numero < numeroMin || numero > numeroMax)
            return false;
        numerosSorteados.add(numero);
        return true;
    }
    public void adicionarAposta(int identificacao, int valorApostado ){
        apostasFeitas.put(identificacao, valorApostado);
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
                int recompensa = apostasFeitas.get(apostador) + (somaApostas/((apostasFeitas.size() - vencedoresID.size() ) + 1 ));
                recompensas.put(apostador, recompensa);
            }
        return recompensas;
    }

    public ArrayList<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }
    
    

    
    
}
