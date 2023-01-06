package iot.lab.internals.service;

import iot.lab.internals.collections.Section;
import iot.lab.internals.collections.Teacher;
import iot.lab.internals.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        save(obj);
    }


}
