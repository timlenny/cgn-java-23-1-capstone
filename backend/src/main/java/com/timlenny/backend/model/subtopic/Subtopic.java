package com.timlenny.backend.model.subtopic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("subtopic")
public class Subtopic {
    @Id
    String id;
    String topicId;
    int iconStatus;
    int position;
    Instant timeTermin;
    String title;
    String subtitel;
    String desc;
    Instant timeCreation;
}
