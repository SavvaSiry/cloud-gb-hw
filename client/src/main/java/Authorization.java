import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Commands;
import model.Message;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Authorization {

    public Button logIn;
    public TextField password;
    public TextField client;

    public void logIn(ActionEvent actionEvent) throws IOException {
        String user = client.getText();

        Message message = Net.sendMessage(Message.builder()
                .command(Commands.AUTH)
                .author(user)
                .text(password.getText())
                .build());

        if (message.getText().equals("yes")) {
            Stage stage = (Stage) logIn.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Cloud");
            stage.setScene(new Scene(root1));
            stage.show();
        } else {
            System.out.println("false");
        }
    }

}
