package iot.lab.internals.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "teacher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teacher {
    @Id
    private String teacherId;
    private String name;
    private String code;
    private List<Section> sectionMap;

}
