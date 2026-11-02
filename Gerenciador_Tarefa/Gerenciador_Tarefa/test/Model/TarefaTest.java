package Model; // 👈 Ajustado

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TarefaTest {

    private Tarefa tarefa;

    @BeforeEach
    public void setUp() {
        
        tarefa = new Tarefa("Estudar Java", "Focar em Orientação a Objetos");
    }

    @Test
    public void testCriacaoTarefa() {
        
        assertEquals("Estudar Java", tarefa.getTitulo());
        assertEquals("Focar em Orientação a Objetos", tarefa.getDescricao());
        assertFalse(tarefa.isConcluida(), "A tarefa deve começar como Pendente");
    }

    @Test
    public void testValidacaoDadosTarefa() {
       
        tarefa.setDescricao("Focar em Testes Unitários");
        assertEquals("Focar em Testes Unitários", tarefa.getDescricao());
        
        
        assertEquals("TAREFA", tarefa.getTipo());
    }
}