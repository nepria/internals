package iot.lab.internals.service;

import iot.lab.internals.collections.Marks;
import iot.lab.internals.collections.Section;
import iot.lab.internals.collections.Student;
import iot.lab.internals.collections.Teacher;

import java.util.List;

public interface TeacherService {
    String save(Teacher teacher);


    Teacher getTeacherById(int id);

    boolean ifTeacherExistsByName(String s);


    void addSection(String s, Section sectionMap);

    void saveStudent(Student studentObj);

    List<Student> getStudentBySection(String section);

    void addMarks(Marks marks, String roll);

    int getTeacherByRoll(String roll);
}
