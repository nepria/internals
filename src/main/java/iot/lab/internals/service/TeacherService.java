package iot.lab.internals.service;

import iot.lab.internals.collections.Section;
import iot.lab.internals.collections.Teacher;

import java.util.List;

public interface TeacherService {
    String save(Teacher teacher);


    Teacher getTeacherById(String id);

    boolean ifTeacherExistsByName(String s);


    void addSection(String s, Section sectionMap);
}
