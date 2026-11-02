
package Excessoes;
    
public class EntradaInvalidaExcessao extends Exception {
    
    public EntradaInvalidaExcessao(String mensagem) {
        super(mensagem);
    }

    public EntradaInvalidaExcessao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}