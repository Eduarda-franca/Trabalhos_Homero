
package app;

import database.DatabaseInicializar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
      
        DatabaseInicializar.incializar();

      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Gerenciador de Tarefas");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}