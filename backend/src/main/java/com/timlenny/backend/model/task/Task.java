package com.timlenny.backend.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@AllArgsConstructor
@Data
public class Task {
    @Id
    String id;
    String subtopicId;
    String title;
    String desc;
    Boolean isCompleted;
    Instant creationDate;
}