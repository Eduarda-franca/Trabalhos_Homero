package Model;

public abstract class BaseTarefa {
    protected int id; 
    protected String titulo;
    protected String descricao;
    protected boolean concluida;

    public BaseTarefa(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public abstract String getTipo();

    @Override
    public String toString() {

        String status = concluida ? "[X] CONCLUÍDA" : "[ ] PENDENTE";
        
        return String.format("ID: %d | %s - %s\n   Descricao: %s\n   %s", 
            id, getTipo(), titulo, descricao, status);
    }
}