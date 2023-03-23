package com.timlenny.backend.model.topic;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Edge {
    String id;
    String source;
    String target;
    public Edge(String id, String source, String target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }
}
