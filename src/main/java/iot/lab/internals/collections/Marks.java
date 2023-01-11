package iot.lab.internals.collections;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Marks {
    public String subject;
    public List<Assignment> assignmentList;

    public Marks(String subject, List<Assignment> assignmentList) {
        this.subject = subject;
        this.assignmentList = assignmentList;
    }
}
