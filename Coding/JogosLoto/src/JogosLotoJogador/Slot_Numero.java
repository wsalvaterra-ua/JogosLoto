
package JogosLotoJogador;

/**
 * Classe que armazenará o valor dos números do Cartão e o seu estado atual especificamente  caso estejam marcados ou não.
 * @author Rui Oliveira e William Salvaterra
 */
public class Slot_Numero {
    private boolean marcado;
    private final int numero;
/**
 * Inicializa as propriedades do objeto, recebe como argumento um número
 * @param numero número a ser atribuido ao objeto
 */
    public Slot_Numero(int numero){
        marcado = false;
        this.numero = numero;
    }
/**
 * Getter para a propriedade marcado
 * @return Boolean se está ou não marcado
 */
    public boolean getMarcado(){
        return marcado;
    }
/**
 * Setter para a propriedade marcado
 * @param marcado Novo valor para a propriedade marcado
 */
    public void setMarcado(boolean marcado){
        this.marcado = marcado;
    }
/**
 * Getter para a propriedade numero
 * @return Retorna propriedade Numero
 */ 
    public int getNumero(){
        return numero;
    }
}

