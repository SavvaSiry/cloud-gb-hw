package server.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.Commands;
import model.Message;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import model.User;
import server.DataBaseModule;

public class SerialHandler extends SimpleChannelInboundHandler<Message> {

    final private String root = "./server/serverFiles/";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {

        if (message.getCommand().equals(Commands.SEND_FILE)) {
            writeToFile(ctx, message);
        } else if (message.getCommand().equals(Commands.GET_FILE)) {
            getFile(ctx, message);
        } else if (message.getCommand().equals(Commands.REFRESH)) {
            refreshFiles(ctx, message);
        } else if (message.getCommand().equals(Commands.AUTH)) {
            auth(ctx, message);
        }
    }

    private void writeToFile(ChannelHandlerContext ctx, Message message) throws IOException {
        if (!Files.isDirectory(Paths.get(root + message.getAuthor()))) {
            Files.createDirectories(Paths.get(root + message.getAuthor()));
        }
        Path file = Files.createFile(Paths.get(root + message.getAuthor() + "/" + message.getFileName()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()));
        writer.write(message.getText());
        writer.close();
        refreshFiles(ctx, message);
    }

    private void refreshFiles(ChannelHandlerContext ctx, Message message) throws IOException {
        List<String> clientFiles = Files.list(Paths.get(root + message.getAuthor()))
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());

        ctx.writeAndFlush(Message.builder()
                .author("Server")
                .command(Commands.REFRESH)
                .text(clientFiles.toString())
                .build());
    }

    private void getFile(ChannelHandlerContext ctx, Message message) throws IOException {
        StringBuilder text = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(root + message.getAuthor() + "/" + message.getFileName()));
        while (reader.ready()) {
            text.append(reader.readLine()).append("\n");
        }
        ctx.writeAndFlush((Message.builder()
                .command(Commands.GET_FILE)
                .fileName(message.getFileName())
                .createdAt(LocalDateTime.now())
                .text(text.toString())
                .author("server")
                .build()));
    }

    private void auth(ChannelHandlerContext ctx, Message message){
        User user = DataBaseModule.findUserByName(message.getAuthor());
        if(user != null){
            if(user.getPass().equals(message.getText())){
                ctx.writeAndFlush(Message.builder()
                .author("server")
                .command(Commands.AUTH)
                .text("yes")
                .build());
            }
            else{
                ctx.writeAndFlush(Message.builder()
                        .author("server")
                        .command(Commands.AUTH)
                        .text("no")
                        .build());
            }
        }else{
            try {
                User newuser = new User(message.getAuthor(), message.getText());
                DataBaseModule.save(newuser);
                ctx.writeAndFlush(Message.builder()
                        .author("server")
                        .command(Commands.AUTH)
                        .text("yes")
                        .build());
            }catch (Exception e){
                ctx.writeAndFlush(Message.builder()
                        .author("server")
                        .command(Commands.AUTH)
                        .text("no")
                        .build());
            }
        }
    }
}
