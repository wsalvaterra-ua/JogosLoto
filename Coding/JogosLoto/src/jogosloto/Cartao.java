/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosloto;

import java.util.Random;

/**
 *
 * @author bilss
 */
public class Cartao {
    
    int linhas_dim = 3;
    int slot_numero_dim = 5;
    int colunas_dim = 9;
    
    Slot_Numero [][] slot;
    int[] slots_disponiveis;
    
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
                    slot[i][j] = new Slot_Numero(randomNum( (j * 10 + ((j==colunas_dim-1) ? 0:1 )) , (j * 10 + ((j==colunas_dim-1) ? 10:9 ) )));
                    espacos_numeros_disponiveis--;
                }
            }
        }
    }

    public Slot_Numero[][] getSlot() {
        return slot;
    }

    public int[] getSlots_disponiveis() {
        return slots_disponiveis;
    }
    
   public void DesMarcarNumeros(){
        for(int i = 0 ; i<linhas_dim; i++){
            for( int j = 0 ; j < colunas_dim; j++)
                if(slot[i][j] != null) slot[i][j].setMarcado(false);
            slots_disponiveis[i] = slot_numero_dim;
        }
   }
   
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

    
    private static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
