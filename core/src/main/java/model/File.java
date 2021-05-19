package model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String pass;
    private String owner;
}
