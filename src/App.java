import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("views/LoginScene.fxml"));
        Scene scene = new Scene(root);

        Image image = new Image("assets/logo.png");
        stage.getIcons().add(image);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Akademi");
        stage.setScene(scene);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
   
}
