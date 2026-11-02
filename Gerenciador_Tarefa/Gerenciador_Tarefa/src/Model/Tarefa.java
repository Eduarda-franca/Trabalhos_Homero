
package Model;

public class Tarefa extends BaseTarefa {
    

    public Tarefa(String titulo, String descricao) {
        super(titulo, descricao);
    }

    @Override
    public String getTipo() {
        return "TAREFA";
    }
}