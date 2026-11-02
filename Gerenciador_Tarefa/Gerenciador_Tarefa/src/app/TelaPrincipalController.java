package app;

import Model.Tarefa;
import repository.TarefaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TelaPrincipalController {

    @FXML private TableView<Tarefa> tabelaTarefas;
    @FXML private TableColumn<Tarefa, Integer> colId;
    @FXML private TableColumn<Tarefa, String> colTitulo;
    @FXML private TableColumn<Tarefa, String> colDescricao;
    @FXML private TableColumn<Tarefa, String> colStatus;

    private ObservableList<Tarefa> listaObservavel = FXCollections.observableArrayList();
    private TarefaRepository repository = new TarefaRepository();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        colStatus.setCellValueFactory(cellData -> {
            boolean concluida = cellData.getValue().isConcluida();
            return new javafx.beans.property.SimpleStringProperty(concluida ? "Concluída" : "Pendente");
        });

        tabelaTarefas.setItems(listaObservavel);
        
       
        carregarTarefas(); 
    }

    public void carregarTarefas() {
        try {
            listaObservavel.clear(); 
            listaObservavel.addAll(repository.listar()); 
        } catch (SQLException e) {
            System.out.println("Erro ao carregar as tarefas do banco.");
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirCadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TarefaForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Nova Tarefa");
            stage.setScene(new Scene(root));
            
            
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(tabelaTarefas.getScene().getWindow());
            
            stage.showAndWait(); 
            
            
            carregarTarefas(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editarTarefa(ActionEvent event) {
        Tarefa selecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        
        if (selecionada == null) {
            System.out.println("Selecione uma tarefa para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TarefaForm.fxml"));
            Parent root = loader.load();
            
            
            TelaCadastroController controller = loader.getController();
            controller.setTarefa(selecionada);
            
            Stage stage = new Stage();
            stage.setTitle("Editar Tarefa");
            stage.setScene(new Scene(root));
            
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(tabelaTarefas.getScene().getWindow());
            
            stage.showAndWait(); 
            
            carregarTarefas(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void concluirTarefa(ActionEvent event) {
        Tarefa selecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            try {
                repository.atualizarStatus(selecionada.getId(), true);
                carregarTarefas();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void removerTarefa(ActionEvent event) {
        Tarefa selecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            try {
                repository.deletar(selecionada.getId());
                carregarTarefas();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}