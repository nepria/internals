package iot.lab.internals.service;

import iot.lab.internals.collections.*;

import java.util.List;

public interface TeacherService {
    String save(Teacher teacher);


    Teacher getTeacherById(int id);

    boolean ifTeacherExistsByName(String s);
    boolean ifTeacherExistsByCode(int c);

    void addSection(String s, Section sectionMap);

    void saveStudent(Student studentObj);

    List<Student> getStudentBySection(String section);

    void addMarks(List<Assignment> assignmentList,String subject, String roll);

    double totalMarks(String subject, String roll);
    List<Teacher> getTeacher();

    List<Assignment> getAssignment(String section,String subject);

    int getTeacherByRoll(String roll);
}
