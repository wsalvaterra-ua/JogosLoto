/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que possui métodos e propriedades para gerir e armazenar dados de uma sessão de jogos de Loto
 * @author William Salvaterra e Rui Oliveira
 */
public class SessaoDeJogoDeLoto {
    private final int numeroMax;
    private final int numeroMin;
    private final ArrayList<Integer> numerosSorteados;
    private final HashMap<Integer,Double> apostasFeitas;
    
/**
 *Construtor da Classe
     * @param min número minimo a ser sorteado
     * @param max número máximo a ser sorteado
 */
    public SessaoDeJogoDeLoto( int min, int max){
         numerosSorteados = new ArrayList<>();
         apostasFeitas = new HashMap<>();
         numeroMin = min;
         numeroMax = max;
    }
/**
 * Adiciona um número a lista de números sorteados
 * @author William Salvaterra e Rui Oliveira
     * @param numero a ser adicionado na lista de números sorteados
     * @return True se número foi adicionado na lista e False se nao for adicionado
 */
    public boolean sortearNumero(int numero){
        if(numerosSorteados.contains(numero))
            return false;
        if(numero < numeroMin || numero > numeroMax)
            return false;
        numerosSorteados.add(numero);
        return true;
    }
/**
 * Este método permite adicionar uma aposta à lista de apostas
 * 
     * @param identificacao  número de identificação da aposta
     * @param valorApostado valor apostado
 */
    public void adicionarAposta(int identificacao, Double valorApostado ){
        apostasFeitas.put(identificacao, valorApostado);
    }
/**
 * Este método retorna os vencedores e as suas respetivas recompensas
 * Cada jogador que apostou  e ganhou recebe uma recompensa baseando na seguinte formula;
 *  ValorApostado + SomaDeApostasDeJogadoresQuePerderam/NºDeApostasQueGanharam + 1
 * 
     * @param vencedoresID recebe a identificação dos vencedores
     * @return HashMap contendo a identificação do vencedor e o valor  atribuido a si como premiação
 */
    public HashMap<Integer,Double> getScores(ArrayList<Integer> vencedoresID){
       
        if(numerosSorteados.size()<15)
            return null;

        int somaApostas = 0;
        int totalVencedores = 0;
        
        HashMap<Integer,Double> recompensas = new HashMap<>();
        
        //vencedores que nao apostaram nada ganha 0
         for(int vencedor : vencedoresID){
             if(apostasFeitas.containsKey(vencedor) )
                 totalVencedores++;
             else
                 recompensas.put(vencedor,0.0);
             
         }
        for(int apostador : apostasFeitas.keySet())
            if(!vencedoresID.contains(apostador))
                somaApostas+= apostasFeitas.get(apostador);
        
        for(int apostador : apostasFeitas.keySet())
            if(vencedoresID.contains(apostador)){
                Double recompensa = apostasFeitas.get(apostador) + (somaApostas/(totalVencedores  + 1 ));
                recompensas.put(apostador, recompensa);
            }
        return recompensas;
    }
/**
 * Método que retorna uma lista contendo os números sorteados
     * @return  retorna uma lista contendo os números sorteados
 */
    public ArrayList<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }
/**
 * Método que retorna uma lista contendo as apostas feitas
 * 
     * @return  retorna uma lista contendo as apostas feitas
 */
    public HashMap<Integer, Double> getApostasFeitas() {
        return apostasFeitas;
    }
    
    
    

    
    
}
