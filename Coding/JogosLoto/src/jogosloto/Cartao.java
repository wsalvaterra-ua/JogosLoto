
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
    int linhas_dim = 3;
/**
 * Quantidade de números por linha. 
 */
    int slot_numero_dim = 5;
/**
 * Quantidade de Colunas a ser gerada
 */
    int colunas_dim = 9;
/**
 * Array multidimensional que servirá para armazenar os Slot_Numeros gerados.
 */
    Slot_Numero [][] slot;
/**
 * Array  que contém a quantidade de números que ainda não foram marcados em cada linha
 */
    int[] slots_disponiveis;
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

        slot = new Slot_Numero[linhas_dim][colunas_dim];
        slots_disponiveis = new int[]{slot_numero_dim,slot_numero_dim,slot_numero_dim};
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
 * @param  min número minimo(inclusive) a ser gerado aleatoriamente
 * @param  max número máximo(inclusive) a ser gerado aleatoriamente
 * @return  número aleatório 
 */
    private static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
