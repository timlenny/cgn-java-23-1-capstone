package com.timlenny.backend.repository;

import com.timlenny.backend.model.Edge;
import com.timlenny.backend.model.Topic;
import com.timlenny.backend.model.TopicPosition;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudyplanRepository {
    Map<String, Topic> topicsMap = new HashMap<>();

    public StudyplanRepository() {
        Topic demoTopicStart = new Topic(
                "1", "START", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", "", 3, true
        );
        Topic demoTopicJava = new Topic(
                "2", "Java", List.of(new Edge("32343232141", "1", "2")), new TopicPosition(300, 600), "Pending", "1852", 3, true
        );
        this.topicsMap.put(demoTopicStart.getId(), demoTopicStart);
        this.topicsMap.put(demoTopicJava.getId(), demoTopicJava);

    }

    public List<Topic> getAllTopics() {
        return topicsMap.values().stream().toList();
    }
}
