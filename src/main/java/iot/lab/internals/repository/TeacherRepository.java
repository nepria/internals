package iot.lab.internals.repository;


import iot.lab.internals.collections.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

    boolean existsTeacherByName(String name);
    Teacher findByCode(int code);
    Teacher findTeacherByName(String name);
    boolean existsTeacherByCode(int code);
    List<Teacher> findAll();

}
