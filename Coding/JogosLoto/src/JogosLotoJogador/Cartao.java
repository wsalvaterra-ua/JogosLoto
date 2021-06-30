
package JogosLotoJogador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Classe Cartão que contêm funções e métodos para criar e Gerir um cartão com números para Jogos de Loto

 * @author Rui Oliveira e William Salvaterra
 */
public final class Cartao {
/**
 * Quantidade de linhas a ser gerada
 */
  private final int linhas_dim;
/**
 * Quantidade de números por linha. 
 */
    private final int slot_numero_dim;
/**
 * Quantidade de Colunas a ser gerada
 */
    private final int colunas_dim;
/**
 *  Arraylist que serve para armazenar os as linhas de numeros gerados.
 */
    
     private ArrayList<HashMap<Integer,Slot_Numero >> LinhasArrayList;

/**
 * Inicializa um Cartão com números para Jogo de Loto:
 * 
     * @param colunas Número de Colunas a serem criadas
     * @param linhas Número de Linhas a serem criadas
     * @param slotsComNumeros Número de Colunas a serem ocupadas por cada linha
 */ 
    public Cartao(int colunas,int linhas ,int slotsComNumeros) {
        this.LinhasArrayList = new  ArrayList<>();
        linhas_dim = linhas;
        slot_numero_dim = slotsComNumeros;
        colunas_dim = colunas;

        for(int i = 0; i<linhas_dim;i++){
            HashMap<Integer,Slot_Numero > coluna = new HashMap< >();
            int espacos_vazios_disponiveis = colunas_dim - slot_numero_dim ;
            int espacos_numeros_disponiveis = slot_numero_dim;
            
            for( int j = 0 ; j < colunas_dim; j++){ 
                int resultRand =randomNum(0, 2);
                int randNum =  randomNum(getColumnMin(j), getColumnMax(j));
                if(espacos_vazios_disponiveis> 0 &&  resultRand == 0 || espacos_numeros_disponiveis <1 && espacos_vazios_disponiveis> 0){
                    coluna.put(randNum ,null);
                    espacos_vazios_disponiveis--;
                }else{
                    
                    while(LinhasArrayList.size() >= 1){
                        int p;
                        boolean temNumerosIguais  = false;
                        randNum = randomNum(getColumnMin(j), getColumnMax(j));
                  
                        for(p = 0 ;  p < LinhasArrayList.size() ; p++)
                            if(LinhasArrayList.get(p).containsKey(randNum))
                                temNumerosIguais = true;
                                
                        if(!temNumerosIguais)
                            break;
                        
                    }  
                    coluna.put(randNum,new Slot_Numero(randNum));
                    espacos_numeros_disponiveis--;
                    
                }
            }
            LinhasArrayList.add(coluna);
        }
        
    }




/**
 * Método que procura no array slot[][] um Slot_Numero que contenha o número passado como argumento, o primeiro número encontrado é marcado 
 * e o método retorna um valor True caso um número seja encontrado e False caso não.
 * @param  numeroSorteado Número a ser sorteado e marcado
 * @return  a linha em que o numero foi marcado ou null e nenhum número for marcado 
 */
    public HashMap<Integer,Slot_Numero >  MarcarNumeroSorteado(int numeroSorteado){
        
       if(numeroSorteado <  getColumnMin(0) || numeroSorteado >  getColumnMax(colunas_dim - 1)){
           return null;
        }
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = this.LinhasArrayList.iterator();
        while ( iteradorArrayList.hasNext() ){
            HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();

                if( coluna.containsKey(numeroSorteado))
                    if (coluna.get(numeroSorteado) != null)
                        if(!coluna.get(numeroSorteado).getMarcado())
                            if(coluna.get(numeroSorteado).getNumero() == numeroSorteado){
                                coluna.get(numeroSorteado).setMarcado(true);
                                return coluna;
                            }
                
            
        }
        
        return null;            
    }


/**
 * Getter para  propriedade colunas_dim
 * @return  Valor da propriedade colunas_dim
 */
    public int getColunas_dim() {
        return colunas_dim;
    }
/**
 * Getter para  propriedade linhas_dim
 * @return  Valor da propriedade linhas_dim
 */
    public int getLinhas_dim() {
        return linhas_dim;
    }
/**
 * Getter para  propriedade slot_numero_dim
 * @return  Valor da propriedade slot_numero_dim
 */
    public int getSlot_numero_dim() {
        return slot_numero_dim;
    }
/**
 *  retorna A Coleção de Linhas do Cartão
 * @return  Coleção de Linhas do Cartão
 */
    public ArrayList<HashMap<Integer, Slot_Numero>> getLinhasArrayList() {
        return LinhasArrayList;
    }
    
    
/**
 * Função que returna um número aleatório entre dois números passados como argumento
 * Referencia: https://www.baeldung.com/java-generating-random-numbers-in-range
 * @param  min número minimo(inclusive) a ser gerado aleatoriamente
 * @param  max número máximo(inclusive) a ser gerado aleatoriamente
 * 
 * @return  número aleatório 
 */
    public static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
/**
 *  retorna o número máximo permitido no dado index
     * @param j número do index(começa em 0)
 * @return  o número máximo permitido no dado index
 */
    public int getColumnMax(int j){
        return (j * 10 + ((j==colunas_dim-1) ? 10:9 ) );
    }
/**
 *  retorna o número máximo permitido no dado index
     * @param j número do index(começa em 0)
     * @param maxColuna index da ultima colunas no cartão
 * @return  o número máximo permitido no dado index
 */
    public static int getColumnMax(int j, int maxColuna){
        return (j * 10 + ((j==maxColuna-1) ? 10:9 ) );
    }
    
/**
 *  retorna o número mínimo permitido no dado index
     * @param j número do index(começa em 0)
 * @return  o número mínimo permitido no dado index
 */
    public static int getColumnMin(int j){
        return  (j * 10 + ((j==0) ? 1:0 ));
    }
    
/**
 *  Função para verificar se o Cartão cumpre as regras do Cartão de Jogo de Loto
 * @return  True se cartão for íntegro, False se cartão não cumprir as regras de um jogo de Loto
 */
    public boolean verificar_integridade(){
        
      return  verificar_integridade(new StringBuilder(""));
    }
/**
 *  Função para verificar se o Cartão cumpre as regras do Cartão de Jogo de Loto
     * @param textoDebug StringBuilder para ser alterado por referencia que conterá as mensagens de erro que o método detetar
 * @return  True se cartão for íntegro, False se cartão não cumprir as regras de um jogo de Loto
 */
    public boolean verificar_integridade(StringBuilder textoDebug){
        final int  espacos_vazios_permitidos = colunas_dim - slot_numero_dim ;
        final int espacos_numeros_permitidos = slot_numero_dim;
        
        for(int ln = 0; ln< linhas_dim; ln++){
            int espacos_vazios_usados = 0;
            int espacos_numeros_usados = 0;
            List<Integer> sortedKeys=new ArrayList(LinhasArrayList.get(ln).keySet());
            Collections.sort(sortedKeys);
            int c = 0;//Variavel para saber o Index da coluna do numero  para calcular o seu maximo e seu minimo
            
            for (int key : sortedKeys) {
                if(LinhasArrayList.get(ln).get(key) != null){
                    
                    if(!(LinhasArrayList.get(ln).get(key).getNumero() >= getColumnMin(c) && (LinhasArrayList.get(ln).get(key).getNumero() <= getColumnMax(c)  )))
                        return false;
                    for(int b = 0; b < linhas_dim; b++)
                        if (LinhasArrayList.get(b) != LinhasArrayList.get(ln) && LinhasArrayList.get(b).containsKey(key)  && LinhasArrayList.get(b).get(key) != null){
                            textoDebug.append( textoDebug + "O número " + LinhasArrayList.get(b).get(key).getNumero() + " repete-se na coluna " + (c + 1) + "!\n" );
                            return false;
                        }
                    espacos_numeros_usados++;
                }else
                    espacos_vazios_usados++;
                c++;
            }
            if(espacos_numeros_usados != espacos_numeros_permitidos || espacos_vazios_usados != espacos_vazios_permitidos){
                textoDebug.append(textoDebug + "A linha " + (ln+1) +  " tem uma quantidade inválida de espaços vazios e números!\n");
                
                return false;
            }
        }
      return true;
    }
}
