/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosloto;

/**
 *
 * @author rui
 */
public class Slot_Numero {
    boolean marcado;
    int numero;
    
    public Slot_Numero(int numero){
        marcado = false;
        this.numero = numero;
    }
    
    public boolean getMarcado(){
        return marcado;
    }
    
    public void setMarcado(boolean marcado){
        this.marcado = marcado;
    }
    
    public int getNumero(){
        return numero;
    }
}
