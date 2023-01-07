package iot.lab.internals.collections;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Marks {
    String subject;
    List<Assignment> assignmentList;
}
