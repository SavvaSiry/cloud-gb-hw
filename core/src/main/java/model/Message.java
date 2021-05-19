package model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Message implements Serializable {

    private Commands command;
    private String fileName;
    private String text;
    private String author;
    private LocalDateTime createdAt;

}