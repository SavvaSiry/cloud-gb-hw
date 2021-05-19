import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String name;
        Parent parent = FXMLLoader.load(getClass().getResource("authorization.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
