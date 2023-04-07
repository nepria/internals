package iot.lab.internals.service;

import iot.lab.internals.collections.*;
import iot.lab.internals.repository.StudentRepository;
import iot.lab.internals.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public boolean ifTeacherExistsByCode(int code) {
        return teacherRepository.existsTeacherByCode(code);
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
    public void addMarks(List<Assignment> assignmentList, String subject, String roll) {
        Student obj = studentRepository.findStudentsByRoll(roll);
        List<Marks> marksList = obj.getMarksList();
        int i, f = 0;
        for(i = 0; i < marksList.size(); i++) {
                Marks marks_sub = marksList.get(i);
                if (marks_sub.subject.equalsIgnoreCase(subject)) {
                    List<Assignment> a = marks_sub.getAssignmentList();
                    Assignment assignment = assignmentList.get(0);
                    a.add(assignment);
                    marks_sub.setAssignmentList(a);
                    f = 1;
                    break;
                }
        }
        if(f == 0) {
            Marks marks = new Marks(subject, assignmentList);
            marksList.add(marks);
            obj.setMarksList(marksList);
        }
        studentRepository.save(obj);
    }

    @Override
    public int getTeacherByRoll(String roll) {
        return teacherRepository.findTeacherByName(roll).getCode();
    }

    @Override
    public double totalMarks(String subject, String roll) {
        Student obj = studentRepository.findStudentsByRoll(roll);
        List<Marks> marksList = obj.getMarksList();
        int i = 0, j = 0;
        double sum = 0.0;
        while(true) {
            if (i < marksList.size()) {
                Marks marks_sub = marksList.get(i);
                if (subject.equalsIgnoreCase(marks_sub.getSubject())) {
                    List<Assignment> assignmentsList = marks_sub.getAssignmentList();
                    while (j < assignmentsList.size()) {
                        Assignment sub_assignment = assignmentsList.get(j);
                        sum = sum + (sub_assignment.getMarks() * sub_assignment.getWeightage());
                        j++;
                    }
                    return sum;
                }
                i++;
            }
            else
                break;
        }
        return sum;
    }

    @Override
    public List<Teacher> getTeacher() {
        List<Teacher> tc = new ArrayList<Teacher>();
        tc = teacherRepository.findAll();
        return tc;
    }

    @Override
    public List<Assignment> getAssignment(String section,String subject) {
        List<Student> students = studentRepository.findStudentsBySection(section);
            List<Marks> marks = students.get(0).getMarksList();
            int i = 0;
        while(true) {
            if (i < marks.size()) {
                Marks marks_sub = marks.get(i);
                if (subject.equalsIgnoreCase(marks_sub.getSubject())) {
                    return marks_sub.getAssignmentList();
                }
            }
            else
                return null;
        }
    }

}
