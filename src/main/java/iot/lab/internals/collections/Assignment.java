package iot.lab.internals.collections;

import lombok.Builder;
import lombok.Data;

@Data
public class Assignment {
    public String AssignName;
    public String date="";
    public double marks=0;
    public double weightage = 1.0;

    public Assignment(String AssignName, double marks, double weightage) {
        this.AssignName = AssignName;
        this.marks = marks;
        this.weightage = weightage;
    }
}
