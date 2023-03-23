package com.timlenny.backend.model.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("topics")
public class Topic {
    @Id
    String id;
    String parentId;
    String topicName;
    List<Edge> edges;
    TopicPosition position;
    String topicStatus;
    String subtopicId;
    int size;
    boolean open;

}

