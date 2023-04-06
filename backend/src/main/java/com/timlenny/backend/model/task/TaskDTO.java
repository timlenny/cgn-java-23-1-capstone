package com.timlenny.backend.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDTO {
    String subtopicId;
    String title;
    String desc;
    Boolean isCompleted;
}