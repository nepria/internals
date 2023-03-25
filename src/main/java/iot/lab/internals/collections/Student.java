package iot.lab.internals.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "student")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    public Student(String name, String roll, String section, List<Marks> marksList) {
        this.name = name;
        this.roll = roll;
        this.section = section;
        this.marksList = marksList;
    }
    @Id
    public String studentId;
    public String name;
    public String roll;
    public String section;
    public List<Marks> marksList;
}


