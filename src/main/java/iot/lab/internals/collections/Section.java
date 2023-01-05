package iot.lab.internals.collections;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Section {
    private int section;
    private String subject;
}
