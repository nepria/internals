package iot.lab.internals.service;

import iot.lab.internals.collections.Marks;
import iot.lab.internals.collections.Section;
import iot.lab.internals.collections.Student;
import iot.lab.internals.collections.Teacher;
import iot.lab.internals.repository.StudentRepository;
import iot.lab.internals.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public String save(Teacher teacher) {
        return teacherRepository.save(teacher).getTeacherId();
    }

    @Override
    public Teacher getTeacherById(int id) {
        return teacherRepository.findByCode(id);
    }

    @Override
    public boolean ifTeacherExistsByName(String name) {
        return teacherRepository.existsTeacherByName(name);
    }

    @Override
    public void addSection(String s, Section sectionMap) {
        Teacher obj = teacherRepository.findTeacherByName(s);
        List<Section> section = obj.getSectionMap();
        section.add(sectionMap);
        obj.setSectionMap(section);
        teacherRepository.save(obj);
    }

    @Override
    public void saveStudent(Student studentObj) {
        studentRepository.save(studentObj);
    }

    @Override
    public List<Student> getStudentBySection(String section) {
        return studentRepository.findStudentsBySection(section);
    }

    @Override
    public void addMarks(Marks marks, String roll) {
        Student obj = studentRepository.findStudentsByRoll(roll);
        List<Marks> marksList = obj.getMarksList();
        marksList.add(marks);
        obj.setMarksList(marksList);
        studentRepository.save(obj);
    }

    @Override
    public int getTeacherByRoll(String roll) {
        return teacherRepository.findTeacherByName(roll).getCode();
    }


}
