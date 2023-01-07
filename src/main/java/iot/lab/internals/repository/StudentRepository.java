package iot.lab.internals.repository;

import iot.lab.internals.collections.Student;
import iot.lab.internals.collections.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {


}
