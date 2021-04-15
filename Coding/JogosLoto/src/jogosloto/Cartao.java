
package jogosloto;

import java.util.Random;

/**
 * Classe Cartão que contêm a essencia do Jogo Loto, assim que o objeto é instanciado o cartão é construído de acordo com as seguintes regras:
 * Cada linha tem sempre 5 números.
 * 
 * Cada coluna tem entre 0 (zero) e 3 números, inclusive.
 * Cada coluna apenas pode ter números da dezena correspondente à sua
 * posição, i.e., a primeira coluna pode ter números entre 1 e 9, inclusive,
 * a segunda coluna pode ter números entre 10 e 19, inclusive, e assim
 * sucessivamente até à última coluna que pode ter números entre 80 e
 * 90, inclusive.
 * 
 * Está classe para além de gerar o Cartão de Jogos de Loto, é também possivel  utilizar métodos como Marcar número sorteado ou 
 * Desmarcar os mesmos
 * @author Rui Oliveira e William Salvaterra
 */
public class Cartao {
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
 * Array multidimensional que servirá para armazenar os Slot_Numeros gerados.
 */
    private final Slot_Numero [][] slot;
/**
 * Array  que contém a quantidade de números que ainda não foram marcados em cada linha
 */
    private final int[] slots_disponiveis;
/**
 * Constrói um cartão para Jogo de Loto com os seguintes requisitos:
 * 
 * Cada coluna tem entre 0 (zero) e 3 números, inclusive.
 * Cada coluna apenas pode ter números da dezena correspondente à sua
 * posição, i.e., a primeira coluna pode ter números entre 1 e 9, inclusive,
 * a segunda coluna pode ter números entre 10 e 19, inclusive, e assim
 * sucessivamente até à última coluna que pode ter números entre 80 e
 * 90, inclusive.
 */ 
    public Cartao() {
        linhas_dim = 3;
        slot_numero_dim = 5;
        colunas_dim = 9;
        slot = new Slot_Numero[linhas_dim][colunas_dim];
        slots_disponiveis = new int[linhas_dim];
        for(int i = 0 ; i<linhas_dim; i++)  slots_disponiveis[i] = slot_numero_dim;
        for(int i = 0 ; i<linhas_dim; i++){
            int espacos_vazios_disponiveis = colunas_dim - slot_numero_dim ;
            int espacos_numeros_disponiveis = slot_numero_dim;
            for( int j = 0 ; j < colunas_dim; j++){

                int resultRand =randomNum(0, 2);
                if(espacos_vazios_disponiveis> 0 &&  resultRand == 0 || espacos_numeros_disponiveis <1 && espacos_vazios_disponiveis> 0)
                    espacos_vazios_disponiveis--;
                else{
                    slot[i][j] = new Slot_Numero(randomNum( (j * 10 + ((j==0) ? 1:0 )) ,
                            (j * 10 + ((j==colunas_dim-1) ? 10:9 ) )));
                    espacos_numeros_disponiveis--;
                }
            }
        }
    }
/**
 * Constrói um cartão para Jogo de Loto com um número variavel de linhas, colunas e números por linha:
 * 
 * Cada coluna apenas pode ter números da dezena correspondente à sua
 posição, i.e., a primeira coluna pode ter números entre 1 e 9, inclusive,
 a segunda coluna pode ter números entre 10 e 19, inclusive, e assim
 sucessivamente até a ultima coluna que pode ter entre n*10 e n * 10+10
     * @param linhas_dim Quantidade de linhas a ser gerada
     * @param slot_numero_dim Quantidade de números por linha. 
     * @param colunas_dim Quantidade de Colunas a ser gerada
 */ 
    public Cartao(int linhas_dim, int slot_numero_dim , int colunas_dim){
        this.linhas_dim = linhas_dim; 
        this.slot_numero_dim = slot_numero_dim;
        this.colunas_dim = colunas_dim;
        
        slot = new Slot_Numero[linhas_dim][colunas_dim];
        slots_disponiveis = new int[linhas_dim];
        for(int i = 0 ; i<linhas_dim; i++)  slots_disponiveis[i] = slot_numero_dim;
        for(int i = 0 ; i<linhas_dim; i++){
            int espacos_vazios_disponiveis = colunas_dim - slot_numero_dim ;
            int espacos_numeros_disponiveis = slot_numero_dim;
            for( int j = 0 ; j < colunas_dim; j++){

                int resultRand =randomNum(0, 2);
                if(espacos_vazios_disponiveis> 0 &&  resultRand == 0 || espacos_numeros_disponiveis <1 && espacos_vazios_disponiveis> 0)
                    espacos_vazios_disponiveis--;
                else{
                    slot[i][j] = new Slot_Numero(randomNum( (j * 10 + ((j==0) ? 1:0 )) ,
                            (j * 10 + ((j==colunas_dim-1) ? 10:9 ) )));
                    espacos_numeros_disponiveis--;
                }
            }
        }
    }
/**
 * Getter para propriedade Slot_Numero
 *@return Array com os números do cartão.
 */
    public Slot_Numero[][] getSlot() {
        return slot;
    }
    
/**
 * Getter para propriedade slots_disponiveis
 *@return Array com a quantidade de números ainda não marccados em cada linha.
 */
    public int[] getSlots_disponiveis() {
        return slots_disponiveis;
    }
/**
 * Método para Desmarcar todos os números do Cartão.
 */
   public void DesMarcarNumeros(){
        for(int i = 0 ; i<linhas_dim; i++){
            for( int j = 0 ; j < colunas_dim; j++)
                if(slot[i][j] != null) slot[i][j].setMarcado(false);
            slots_disponiveis[i] = slot_numero_dim;
        }
   }
/**
 * Método que procura no array slot[][] um Slot_Numero que contenha o número passado como argumento, o primeiro número encontrado é marcado 
 * e o método retorna um valor True caso um número seja encontrado e False caso não.
 * @param  numeroSorteado Número a ser sorteado e marcado
 * @return  True ou False 
 */
    public boolean MarcarNumeroSorteado(int numeroSorteado){
        for(int i = 0 ; i<linhas_dim; i++){
            for( int j = 0 ; j < colunas_dim; j++){
                if(slot[i][j] != null)
                    if(!slot[i][j].getMarcado() && slot[i][j].getNumero() == numeroSorteado ){
                        slot[i][j].setMarcado(true);
                        slots_disponiveis[i]--;
                        if(slots_disponiveis[i]<1) System.out.println("Aviso: Linha "+ (i+1) + " está completa!");
                        return true;
                    } 
            }
            
        }
        return false;
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
