package eti.pg.lab.file.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Entity
@Table(name="files")
public class FileDB implements Serializable {
    @Id
    @Column(unique = true)
    private int id;

    @Column(name= "title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="author")
    private String author;

    @Column(name="filename")
    private String filename;

    @Lob
    private byte[] data;

}
