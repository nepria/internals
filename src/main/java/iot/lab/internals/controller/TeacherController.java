package iot.lab.internals.controller;
import iot.lab.internals.collections.*;
import iot.lab.internals.service.TeacherService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    int count = 0;

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

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
    @PostMapping()
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

    @GetMapping("/addAssigment")
    public String addAssignment(@RequestParam("file")MultipartFile file, @RequestParam("tName")String name,
                                @RequestParam("tSection")String section, @RequestParam("tSubject")String subject,
                                @RequestParam("assignName")String assignName, @RequestParam("weightage")double weightage) throws IOException {

        List<Student> students = teacherService.getStudentBySection(section);
        FileInputStream fis = new FileInputStream(convert(file));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        for(Student student: students) {

            for(Row row: sheet) {
                for(Cell cell: row) {
                    if(cell.getNumericCellValue() == Integer.parseInt(student.roll)) {
                        int columnIndex = cell.getColumnIndex();
                        double individualMarks = row.getCell(columnIndex + 1).getNumericCellValue();
                        List<Assignment> assignmentList = new ArrayList<>();
                        assignmentList.add(new Assignment(assignName, individualMarks, weightage));
                        insertMarksStudent(assignmentList, subject, student.roll);
                    }
                }
            }
        }
        return "Assignment added Successfully";
    }

    private void insertMarksStudent(List<Assignment> assignmentList,String subject, String roll) {
        teacherService.addMarks(assignmentList,subject, roll);
    }

    @GetMapping("/totalStudents")
    public int totalStudents(@RequestParam("tSection")String section) throws IOException {
        List<Student> students = teacherService.getStudentBySection(section);
        int count = 0;
        for(Student st: students) {
            count++;
        }
        return count;
    }

    @GetMapping("/getAssignments")
    public List<Assignment> getAssignment(@RequestParam("tSection")String section, @RequestParam("tSubject")String subject) throws IOException {
        return teacherService.getAssignment(section, subject);
    }

    @GetMapping("/totalMarks")
    public File totalMarks(@RequestParam("tName")String name, @RequestParam("tSection")String section, @RequestParam("tSubject")String subject) throws IOException  {
        List<Student> students = teacherService.getStudentBySection(section);
        double final_marks = 0.0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Interal Marks");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] { "ROLL NO", "NAME", "TOTAL MARKS" });
        int k = 2;
        for(Student student: students) {
            final_marks = getTotalMarks(subject, student.roll);
            data.put(""+k++, new Object[] { student.roll, student.name, ""+final_marks });
        }
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String)obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
       FileOutputStream out = new FileOutputStream(new File("internal_marks.xlsx"));
       workbook.write(out);
       File inter = new File("internal_marks.xls");
       out.close();
       return inter;
    }

    private double getTotalMarks(String section, String roll) {
        return teacherService.totalMarks(section, roll);
    }

//    @GetMapping("/authorization")
//    public Object authorization(@RequestParam("tName")String name, @RequestParam("tCode")int code) throws IOException {
//        if (teacherService.ifTeacherExistsByCode(code)) {
//            Teacher teacher = teacherService.getTeacherById(code);
//            if (teacher.getName().equalsIgnoreCase(name))
//                return teacher;
//        }
//
//        return ResponseEntity.accepted().body("Teacher does not exist");
//    }

       @GetMapping("/authorization")
    public ResponseEntity<Teacher> authorization(@RequestParam("tName")String name, @RequestParam("tCode")int code) throws IOException {

        if (teacherService.ifTeacherExistsByCode(code)) {
            Teacher teacher = teacherService.getTeacherById(code);
            if (teacher.getName().equalsIgnoreCase(name)) {
                return new ResponseEntity<>(teacher, HttpStatusCode.valueOf(200));
            }
        }

        return new ResponseEntity<>(new Teacher(), HttpStatusCode.valueOf(401));
    }

    @GetMapping("/getTeachers")
    public List<Teacher> getTeachers() throws IOException {
        List<Teacher> tc = new ArrayList<Teacher>();
        tc = teacherService.getTeacher();
        return tc;
    }

}

