package com.timlenny.backend.model.topic;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopicPosition {
    int x;
    int y;
    public TopicPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
