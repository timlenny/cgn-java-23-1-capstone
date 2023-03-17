package com.timlenny.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    String id;
    String topicName;
    List<Edge> edges;
    TopicPosition position;
    String topicStatus;
    String subtopicId;
    int size;
    boolean open;

}

