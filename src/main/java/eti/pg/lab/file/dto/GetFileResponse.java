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
public class GetFileResponse {
    private int id;
    private String title;
    private String description;
    private String author;
    private String filename;
    private byte[] data;

    public static Function<FileDB, GetFileResponse> entityToDtoMapper(){
        return request -> GetFileResponse.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .author(request.getAuthor())
                .filename(request.getFilename())
                .data(request.getData())
                .build();
    }
}
