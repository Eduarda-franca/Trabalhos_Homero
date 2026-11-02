package app;

import Model.Tarefa;
import Model.TarefaPrioritaria;
import repository.TarefaRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TelaCadastroController {

    @FXML private TextField txtTitulo;
    @FXML private TextArea txtDescricao;
    @FXML private CheckBox chkPrioritaria;

    private TarefaRepository repository = new TarefaRepository();
    private Tarefa tarefaParaEditar = null; 

  
    public void setTarefa(Tarefa tarefa) {
        this.tarefaParaEditar = tarefa;
        
        
        txtTitulo.setText(tarefa.getTitulo());
        txtDescricao.setText(tarefa.getDescricao());
        
        
        if (tarefa instanceof TarefaPrioritaria) {
            chkPrioritaria.setSelected(true);
        }
    }

    @FXML
    public void salvarTarefa(ActionEvent event) {
        String titulo = txtTitulo.getText();
        String descricao = txtDescricao.getText();
        
        if (titulo == null || titulo.trim().isEmpty()) {
            System.out.println("O título não pode estar vazio!");
            return;
        }

        try {
            if (tarefaParaEditar == null) {
                
                Tarefa novaTarefa;
                if (chkPrioritaria.isSelected()) {
                    novaTarefa = new TarefaPrioritaria(titulo, descricao, 1, "Sem prazo");
                } else {
                    novaTarefa = new Tarefa(titulo, descricao);
                }
                repository.salvar(novaTarefa);
                System.out.println("Tarefa criada com sucesso!");
            } else {
               
                repository.editarTarefa(tarefaParaEditar.getId(), titulo, descricao, tarefaParaEditar.isConcluida());
                System.out.println("Tarefa editada com sucesso!");
            }

            fecharJanela();

        } catch (SQLException e) {
            System.out.println("Erro ao salvar/editar no banco.");
            e.printStackTrace();
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }
}