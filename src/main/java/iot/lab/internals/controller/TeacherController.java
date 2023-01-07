package iot.lab.internals.controller;

import iot.lab.internals.collections.Section;
import iot.lab.internals.collections.Student;
import iot.lab.internals.collections.Teacher;
import com.opencsv.exceptions.CsvException;
import iot.lab.internals.service.TeacherService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    int count = 0;

    @Autowired
    private TeacherService teacherService;


    //Get Teacher Object by passing TeacherCode
    @GetMapping
    public Teacher getDetails(@RequestParam(value="tCode") int code) {
        return teacherService.getTeacherById(code);
    }

    //Import All Teachers into Database from .csv file
    @GetMapping("/importTeachers")
    public String importTeacher(@RequestParam("file")MultipartFile file ) throws IOException {

        List<String> subjects = new ArrayList<>();
        List<List<String>> teacherSubjectMap = new ArrayList<>();
        FileInputStream fis = new FileInputStream(convert(file));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        for(Row row: sheet) {
            for(Cell cell: row) {
                int col = cell.getColumnIndex();
                if(col == 0) continue;
                subjects.add(cell.getStringCellValue());
            }
            break;
        }

        for(Row row: sheet) {
            if(row.getRowNum() == 0) continue;
            Cell section = row.getCell(0);
            for(Cell cell: row) {
                List<String> result = new ArrayList<>();
                int col = cell.getColumnIndex();
                if(col == 0) continue;
                result.add(cell.getStringCellValue());
                result.add(section.getStringCellValue());
                result.add(subjects.get(col-1));
                teacherSubjectMap.add(result);
            }
        }
       for(List<String> teacher: teacherSubjectMap) {
           createTeacher(teacher);
       }
       return "Added Successfully";
    }
    public void createTeacher(List<String> teacher) {
        //Adding a new entry for teacher if it does not exist
        Section sObj = new Section(teacher.get(1), teacher.get(2));
        List<Section> sectionMap = new ArrayList<>();
        if(!teacherService.ifTeacherExistsByName(teacher.get(0))) {
            sectionMap.add(sObj);
            Teacher tObj = new Teacher(teacher.get(0), count++, sectionMap);
            teacherService.save(tObj);
        }
        //Updating Teachers section since it already exists
        else {
            teacherService.addSection(teacher.get(0), sObj);
        }
    }

    //Convert Multipart File to File type
    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    //Save a single Teacher
    @PostMapping
    public String save(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @GetMapping("/importStudents")
    public String importStudent(@RequestParam("file")MultipartFile file ) throws IOException {
        FileInputStream fis = new FileInputStream(convert(file));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<List<String>> allStudents = new ArrayList<>();
        for(Row row: sheet) {
            List<String> student = new ArrayList<>();
            for(Cell cell: row) {
                student.add(cell.getStringCellValue());
            }
            allStudents.add(student);
        }
        for(List<String> student: allStudents) {
            Student studentObj = new Student(student.get(1), student.get(0), student.get(2), Collections.emptyList());
            teacherService.saveStudent(studentObj);
        }

        return "Students Uploaded Successfully";
    }
}

