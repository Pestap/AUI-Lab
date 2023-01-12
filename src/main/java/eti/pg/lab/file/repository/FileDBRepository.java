package eti.pg.lab.file.repository;

import eti.pg.lab.file.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Integer> {

}
