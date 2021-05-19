package model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String pass;

    public User() {
    }

    public User(String author, String text) {
        this.name = author;
        this.pass = text;
    }
}
