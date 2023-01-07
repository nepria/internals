package iot.lab.internals.collections;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Assignment {
    public String AssignName;
    public String date;
    public int marks;
    public double weightage;

    public Assignment(String assignName, String date, int marks, double weightage) {
        this.AssignName = assignName;
        this.date = date;
        this.marks = marks;
        this.weightage = weightage;
    }
}
