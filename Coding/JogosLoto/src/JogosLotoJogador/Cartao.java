
package JogosLotoJogador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
 *  Arraylist que serve para armazenar os as linhas de numeros gerados.
 */
    
     ArrayList<HashMap<Integer,Slot_Numero >> LinhasArrayList;

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
        this.LinhasArrayList = new  ArrayList<>();
        linhas_dim = 3;
        slot_numero_dim = 5;
        colunas_dim = 9;

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
 * Método para Desmarcar todos os números do Cartão.
 */
   public void DesMarcarNumeros(){
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = this.LinhasArrayList.iterator();
        while ( iteradorArrayList.hasNext() ){
            HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();
            for (int i : coluna.keySet()) 
                if( coluna.get(i) != null)
                    coluna.get(i).setMarcado(false);
            }
        
   }
/**
 * Método que procura no array slot[][] um Slot_Numero que contenha o número passado como argumento, o primeiro número encontrado é marcado 
 * e o método retorna um valor True caso um número seja encontrado e False caso não.
 * @param  numeroSorteado Número a ser sorteado e marcado
 * @return  True ou False 
 */
    public HashMap<Integer,Slot_Numero >  MarcarNumeroSorteado(int numeroSorteado){
        
        
       
       if(numeroSorteado <  getColumnMin(0) || numeroSorteado >  getColumnMax(linhas_dim - 1))
           return null;
        
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = this.LinhasArrayList.iterator();
        while ( iteradorArrayList.hasNext() ){
            HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();
            if (coluna.containsKey(numeroSorteado))
                if( coluna.containsKey(numeroSorteado))
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
    private static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    
    public boolean editar_cartao(int linha, int valorAnterior, int valorNovo){
        if( LinhasArrayList.get(linha) == null)
            return false;
      
        if(valorNovo <  getColumnMin(0) || valorNovo >  getColumnMax(linhas_dim - 1))
            return false;
        
        LinhasArrayList.get(linha).put(valorAnterior,new Slot_Numero(valorNovo));
        if(valorNovo < 1)
            LinhasArrayList.get(linha).put(valorAnterior,null);
        System.out.println("Linha: " + linha + " Valor Antigo: " + valorAnterior + " Valor Novo: " + valorNovo +" editado com sucesso" );
        return true;
    }

    private int getColumnMax(int j){
        return (j * 10 + ((j==colunas_dim-1) ? 10:9 ) );
    }
    private int getColumnMin(int j){
        return  (j * 10 + ((j==0) ? 1:0 ));
    }
    
    
    public boolean verificar_integridade(){
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
                        if (LinhasArrayList.get(b) != LinhasArrayList.get(ln) && LinhasArrayList.get(b).containsKey(key) )
                            return false;
                    
                    espacos_numeros_usados++;
                }else
                    espacos_vazios_usados++;
                c++;
            }
            if(espacos_numeros_usados != espacos_numeros_permitidos || espacos_vazios_usados != espacos_vazios_permitidos)
                return false;
        }
      return true;

            
        
        
    }
}
