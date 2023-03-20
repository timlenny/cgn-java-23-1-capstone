package com.timlenny.backend.model.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

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
        this.topicName = topicDTO.topicName;
        this.size = topicDTO.size;
        this.id = "4";
        this.edges = List.of(new Edge(UUID.randomUUID().toString(), "2", "4"));
        this.position = new TopicPosition(250, 700);
        this.subtopicId = UUID.randomUUID().toString();
        this.open = true;
        this.topicStatus = "Pending";
    }
}

