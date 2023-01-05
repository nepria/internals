package iot.lab.internals.service;

import iot.lab.internals.collections.Teacher;

public interface TeacherService {
    String save(Teacher teacher);


    Teacher getTeacherById(String id);
}
