package eti.pg.lab.file.service;

import eti.pg.lab.file.entity.FileDB;
import eti.pg.lab.file.repository.FileDBRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileDBService {
    private FileDBRepository repository;

    @Autowired
    public FileDBService(FileDBRepository repository){
        this.repository = repository;
    }

    @Transactional
    public FileDB save(FileDB fileDB){
        FileDB fileDB_S= FileDB.builder()
                .id(fileDB.getId())
                .title(fileDB.getTitle())
                .author(fileDB.getAuthor())
                .description(fileDB.getDescription())
                .build();

        // save file to directory

        return repository.save(fileDB_S);
    }

    @Transactional
    public void updateFile(int id, InputStream is){
        repository.findById(id).ifPresent(file -> {
            try {
                file.setData(is.readAllBytes());
                //save to directory
                Path path = Paths.get("E:\\Piotrek\\Studia\\Semestr 5\\Architektury Usług Internetowych\\lab6\\AUI-Lab\\FILES");
                File targetFile = new File(path +"\\"+file.getTitle() + ".txt");
                OutputStream os = new FileOutputStream(targetFile);
                os.write(file.getData());
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }
    @Transactional
    public Optional<FileDB> find(int id){
        return repository.findById(id);
    }


}
