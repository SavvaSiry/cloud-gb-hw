import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientFilesViewer implements Initializable {

    public ListView<String> listViewClient;
    private String clientPaths = ("C:\\Users\\sss_user\\IdeaProjects\\CloudClient\\ClientFiles");

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<String> clientFiles = Files.list(Paths.get(clientPaths))
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());

        listViewClient.getItems().clear();
        listViewClient.getItems().addAll(clientFiles);
    }
}
