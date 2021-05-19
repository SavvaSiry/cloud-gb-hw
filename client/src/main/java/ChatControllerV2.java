import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.Commands;
import model.Message;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
public class ChatControllerV2 implements Initializable {
    public ListView<String> listViewServer;
    public ListView<String> listViewClient;
    public TextField input;
    private String clientPath = ("./client/clientFiles/");
    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void get(ActionEvent actionEvent) throws IOException {
        String filename = listViewServer.getSelectionModel().getSelectedItem();
        Message message = Net.sendMessage(Message.builder()
                .command(Commands.GET_FILE)
                .fileName(filename)
                .createdAt(LocalDateTime.now())
                .author("user")
                .build());
        if (message.equals(Commands.GET_FILE)) getFile(message);
    }

    public void send(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            try {
                String filename = listViewClient.getSelectionModel().getSelectedItem();
                StringBuilder text = new StringBuilder();
                BufferedReader reader = new BufferedReader(new FileReader(clientPath + filename));
                while (true) {
                    if (!reader.ready()) break;
                    text.append(reader.readLine()).append("\n");
                }
                Message message = Net.sendMessage(Message.builder()
                        .command(Commands.SEND_FILE)
                        .fileName(filename)
                        .createdAt(LocalDateTime.now())
                        .text(text.toString())
                        .author("user")
                        .build());
                log.info("File " + filename + " was send.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void getFile(Message message) throws IOException {
       /* if (!Files.isDirectory(Paths.get(clientPath))) {
            Files.createDirectories(Paths.get(clientPath));
        }*/
        Path file = Files.createFile(Paths.get(clientPath + "/" + message.getFileName()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()));
        writer.write(message.getText());
        writer.close();
        clientView();
    }

    public void clientView() throws IOException {
        List<String> clientFiles = Files.list(Paths.get(clientPath))
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
        Platform.runLater(() -> {
            listViewClient.getItems().clear();
            listViewClient.getItems().addAll(clientFiles);
        });
    }

    private void serverView() throws IOException, ClassNotFoundException {
        clientView();
        Message message = (Message) Net.getIs().readObject();
        StringBuilder stringBuilder = new StringBuilder(message.getText());
        stringBuilder.deleteCharAt(message.getText().length() - 1).deleteCharAt(0);
        List<String> list = Arrays.asList(stringBuilder.toString().split(", "));
        Platform.runLater(() -> {
            listViewServer.getItems().clear();
            listViewServer.getItems().addAll(list);
        });
    }

    public void refresh(ActionEvent actionEvent) throws IOException {
        try{
        Message message = Net.sendMessage(Message.builder()
                .command(Commands.REFRESH)
                .createdAt(LocalDateTime.now())
                .author("user")
                .build());
        if (message.equals(Commands.REFRESH)) serverView();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}