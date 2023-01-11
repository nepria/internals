package iot.lab.internals.collections;

import lombok.Builder;
import lombok.Data;

@Data
public class Assignment {
    public String AssignName;
    public String date="";
    public double marks=0;
    public double weightage;

    public Assignment(String AssignName, double marks) {
        this.AssignName = AssignName;
        this.marks = marks;
    }
}
