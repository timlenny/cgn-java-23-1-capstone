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
    String topicName;
    List<Edge> edges;
    TopicPosition position;
    String topicStatus;
    String subtopicId;
    int size;
    boolean open;

    public Topic(TopicDTO topicDTO) {
        this.id = topicDTO.id;
        this.topicName = topicDTO.topicName;
        this.edges = topicDTO.edges;
        this.position = topicDTO.position;
        this.topicStatus = topicDTO.topicStatus;
        this.subtopicId = topicDTO.subtopicId;
        this.size = topicDTO.size;
        this.open = topicDTO.open;
    }
}

