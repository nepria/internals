package iot.lab.internals.collections;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Section {
     public Section(String section, String subject) {
          this.section = section;
          this.subject = subject;
     }

     public String section = "";
     public String subject = "";
}
