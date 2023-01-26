package eti.pg.lab.file.controller;

import com.google.common.io.Files;
import eti.pg.lab.file.dto.CreateFileDBRequest;
import eti.pg.lab.file.dto.GetFileResponse;
import eti.pg.lab.file.entity.FileDB;
import eti.pg.lab.file.repository.FileDBRepository;
import eti.pg.lab.file.service.FileDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/files")
public class FileDBController {
    private FileDBService service;

    @Autowired
    public FileDBController(FileDBService service){
        this.service = service;
    }
    @CrossOrigin
    @GetMapping(value = "{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GetFileResponse> getFile(@PathVariable("id") int id){
        return null;
    }

    @CrossOrigin
    @PostMapping(value="{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@PathVariable(value="id") int id,
                                        @RequestPart(value="file") MultipartFile file,
                                        @RequestPart(value="data") CreateFileDBRequest json_data,
                                         UriComponentsBuilder builder) throws IOException{

        // create file object from dto
        FileDB fileToSave = CreateFileDBRequest.dtoToEntityMapper().apply(json_data);

        // get data from file itself and set according files
        String fileName = file.getOriginalFilename();
        fileToSave.setFilename(fileName);
        fileToSave.setData(file.getBytes());

        service.save(fileToSave);
        return ResponseEntity
                .created(builder
                        .pathSegment("api", "files", "{id}")
                        .buildAndExpand(fileToSave.getId()).toUri())
                .build();
    }
}
