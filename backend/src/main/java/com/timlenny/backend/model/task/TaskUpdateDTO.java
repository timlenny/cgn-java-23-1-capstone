package com.timlenny.backend.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskUpdateDTO {
    String id;
    String title;
    String desc;
    Boolean isCompleted;
}
