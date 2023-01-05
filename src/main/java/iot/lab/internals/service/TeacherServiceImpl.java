package iot.lab.internals.service;

import iot.lab.internals.collections.Teacher;
import iot.lab.internals.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public String save(Teacher teacher) {
        return teacherRepository.save(teacher).getTeacherId();
    }

    @Override
    public Teacher getTeacherById(String id) {
        return teacherRepository.findByCode(id);
    }


}
