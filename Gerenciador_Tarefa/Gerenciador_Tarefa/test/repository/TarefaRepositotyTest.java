package repository; 

import Model.Tarefa;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TarefaRepositotyTest { 

    private TarefaRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new TarefaRepository(); 
    }

    @Test
    public void testSalvarEConsultar() throws SQLException {
        Tarefa t = new Tarefa("Teste DAO", "Testando insert");
        repository.salvar(t);
        
        ArrayList<Tarefa> lista = repository.listar();
        assertFalse(lista.isEmpty(), "A lista não deveria estar vazia após salvar");
        
        boolean encontrou = false;
        for (Tarefa tarefaBanco : lista) {
            if (tarefaBanco.getId() == t.getId()) {
                assertEquals("Teste DAO", tarefaBanco.getTitulo());
                encontrou = true;
                break;
            }
        }
        assertTrue(encontrou, "A tarefa salva não foi encontrada na listagem.");
    }

    @Test
    public void testConclusaoTarefa() throws SQLException {
        Tarefa t = new Tarefa("Finalizar Trabalho", "Entregar para o professor");
        repository.salvar(t); 
        
        int idGerado = t.getId(); 
        repository.atualizarStatus(idGerado, true);
        
        ArrayList<Tarefa> lista = repository.listar();
        for (Tarefa tarefaBanco : lista) {
            if (tarefaBanco.getId() == idGerado) {
                assertTrue(tarefaBanco.isConcluida(), "A tarefa deveria estar concluída no banco");
                break;
            }
        }
    }
}