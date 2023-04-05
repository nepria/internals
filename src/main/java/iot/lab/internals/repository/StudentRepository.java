package iot.lab.internals.repository;

import iot.lab.internals.collections.Assignment;
import iot.lab.internals.collections.Marks;
import iot.lab.internals.collections.Student;
import iot.lab.internals.collections.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
        List<Student> findStudentsBySection(String section);

        Student findStudentsByRoll(String roll);
        List<Assignment> findStudentBySection(String section);

}
