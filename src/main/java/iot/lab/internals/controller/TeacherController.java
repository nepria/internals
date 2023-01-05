package iot.lab.internals.controller;

import iot.lab.internals.collections.Teacher;
import iot.lab.internals.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public Teacher getDetails(@RequestParam(value="Tid") String id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public String save(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }
}

