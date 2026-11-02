package Model;

public class TarefaPrioritaria extends Tarefa {
    private int prioridade; 
    private String prazo;


    public TarefaPrioritaria(String titulo, String descricao, int prioridade, String prazo) {
        super(titulo, descricao);
        this.prioridade = prioridade;
        this.prazo = prazo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public String getPrioridadeTexto() {
        switch (prioridade) {
            case 1: return "ALTA";
            case 2: return "MEDIA";
            case 3: return "BAIXA";
            default: return "DESCONHECIDA";
        }
    }

    @Override
    public String getTipo() {
        return "TAREFA PRIORITARIA";
    }

    @Override
    public String toString() {
        String status = isConcluida() ? "[0] CONCLUIDA" : "[ ] PENDENTE";
        return String.format("%s - %s\n   Descricao: %s\n   Prioridade: %s | Prazo: %s\n   %s", 
            getTipo(), getTitulo(), getDescricao(), getPrioridadeTexto(), prazo, status);
    }
}