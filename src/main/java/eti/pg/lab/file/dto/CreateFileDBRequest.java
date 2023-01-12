package eti.pg.lab.file.dto;


import eti.pg.lab.file.entity.FileDB;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateFileDBRequest {
    private int id;
    private String title;
    private String description;
    private String author;

    public static Function<CreateFileDBRequest, FileDB> dtoToEntityMapper(){
        return request -> FileDB.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .author(request.getAuthor())
                .build();
    }
}
