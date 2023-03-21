package com.timlenny.backend.service;

import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicConversionServiceTest {

    @Mock
    private IdService idService;

    @InjectMocks
    private TopicConversionService topicConversionService;

    private Topic parentTopic;

    @BeforeEach
    void setUp() {
        parentTopic = new Topic();
        parentTopic.setTopicName("HOME");
        parentTopic.setId("parent-id");
        parentTopic.setPosition(new TopicPosition(125, 250));
        parentTopic.setEdges(List.of(new Edge("edge-id", "source-id", "parent-id")));
    }


    @Test
    void createNewEdgesFromParentName() {
        when(idService.generateId()).thenReturn("new-edge-id");

        List<Edge> edges = topicConversionService.createNewEdgesFromParentName(parentTopic, "new-child-id");

        assertEquals(1, edges.size());
        assertEquals("new-edge-id", edges.get(0).getId());
    }

    @Test
    void checkTargetInEdges() {
        boolean result = TopicConversionService.checkTargetInEdges(parentTopic.getEdges(), "parent-id");
        assertTrue(result);

        result = TopicConversionService.checkTargetInEdges(parentTopic.getEdges(), "non-existent-id");
        assertFalse(result);
    }

    @Test
    void setNewTopicInitialPosition() {
        TopicPosition position = topicConversionService.setNewTopicInitialPosition(parentTopic);

        assertNotNull(position);
        assertNotEquals(parentTopic.getPosition().getX(), position.getX());
        assertNotEquals(parentTopic.getPosition().getY(), position.getY());
    }

    @Test
    void calcTopicPositionForSubtopics() {
        Topic subtopic = new Topic();
        subtopic.setTopicName("Subtopic");
        subtopic.setId("subtopic-id");
        subtopic.setPosition(new TopicPosition(200, 300));
        subtopic.setEdges(List.of(new Edge("subtopic-edge-id", "subtopic-id", "child-id")));

        TopicPosition position = topicConversionService.calcTopicPositionForSubtopics(subtopic, 50, 50);

        assertNotNull(position);
        assertNotEquals(subtopic.getPosition().getX(), position.getX());
        assertNotEquals(subtopic.getPosition().getY(), position.getY());
    }

    @Test
    void calcTopicPositionForHomeTopics() {
        TopicPosition position = topicConversionService.calcTopicPositionForHomeTopics(parentTopic, 50, 50);

        assertNotNull(position);
        assertNotEquals(parentTopic.getPosition().getX(), position.getX());
        assertNotEquals(parentTopic.getPosition().getY(), position.getY());
    }
}
