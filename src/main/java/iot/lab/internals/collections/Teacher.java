package iot.lab.internals.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Data
@Document(collection = "teacher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teacher {
    public Teacher(String name, int code, List<Section> sectionMap) {
        this.teacherId = null;
        this.name = name;
        this.code = code;
        this.sectionMap = sectionMap;
    }
    public Teacher(){}

    @Id
     public String teacherId;
     public String name = "";
     public int code;
     public List<Section> sectionMap;

}
