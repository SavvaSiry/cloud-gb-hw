import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.Commands;
import model.Message;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Net {
    @Getter
    private static ObjectDecoderInputStream is;
    @Getter
    private static ObjectEncoderOutputStream os;
    private String clientPath = ("./client/clientFiles/");
    static {
        try {
            Socket socket = new Socket("localhost", 8189);
            os = new ObjectEncoderOutputStream(socket.getOutputStream());
            is = new ObjectDecoderInputStream(socket.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Message sendMessage(Message mes){
        try {
            os.writeObject(mes);
            Message message = (Message) is.readObject();
            return message;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
