package eti.pg.lab.file.controller;

import eti.pg.lab.file.dto.CreateFileDBRequest;
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

    //@GetMapping("{id}")
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Void> createFile(@RequestBody CreateFileDBRequest request, UriComponentsBuilder builder){

        FileDB fileDB = CreateFileDBRequest.dtoToEntityMapper().apply(request);
        System.out.println("SIEMA " + fileDB.getTitle());
        fileDB = service.save(fileDB);

        return ResponseEntity
                .created(builder
                        .pathSegment("api", "files", "{id}")
                        .buildAndExpand(fileDB.getId()).toUri())
                .build();

    }

    @CrossOrigin
    @PutMapping(value="{id}/file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> putFile(@PathVariable("id") int id,
                                        @RequestParam("file") MultipartFile file) throws IOException{
        System.out.println("PLIK");
        Optional<FileDB> file_find = service.find(id);
        if(file_find.isPresent()){
            service.updateFile(file_find.get().getId(), file.getInputStream());
            //save to directory


            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }


    }
}
