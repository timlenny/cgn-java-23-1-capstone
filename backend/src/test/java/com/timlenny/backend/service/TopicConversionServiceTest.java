package com.timlenny.backend.service;

import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

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

    // ...

    @ParameterizedTest
    @MethodSource("calcTopicPositionForSubtopicsArguments")
    void testCalcTopicPositionForSubtopics(Topic subtopic, int xDiff, int yDiff) {
        TopicPosition position = topicConversionService.calcTopicPositionForSubtopics(subtopic, xDiff, yDiff);

        assertNotNull(position);
        assertNotEquals(subtopic.getPosition().getX(), position.getX());
        assertNotEquals(subtopic.getPosition().getY(), position.getY());
    }

    static Stream<Arguments> calcTopicPositionForSubtopicsArguments() {
        Topic subtopic1 = new Topic();
        subtopic1.setTopicName("Subtopic");
        subtopic1.setId("subtopic-id");
        subtopic1.setPosition(new TopicPosition(200, 300));
        subtopic1.setEdges(List.of(new Edge("subtopic-edge-id", "subtopic-id", "child-id")));

        Topic subtopic2 = new Topic();
        subtopic2.setTopicName("Subtopic");
        subtopic2.setId("subtopic-id");
        subtopic2.setPosition(new TopicPosition(200, 300));
        subtopic2.setEdges(List.of(new Edge("subtopic-edge-id", "source-id", "subtopic-id")));

        Topic subtopic3 = new Topic();
        subtopic3.setTopicName("Subtopic");
        subtopic3.setId("subtopic-id");
        subtopic3.setPosition(new TopicPosition(200, 300));
        subtopic3.setEdges(List.of(new Edge("subtopic-edge-id", "subtopic-id", "child-id")));

        return Stream.of(
                Arguments.of(subtopic1, 50, 50),
                Arguments.of(subtopic2, 50, 50),
                Arguments.of(subtopic3, 50, 50)
        );
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
    void calcTopicPositionForHomeTopics() {
        TopicPosition position = topicConversionService.calcTopicPositionForHomeTopics(parentTopic, 50, 50);

        assertNotNull(position);
        assertNotEquals(parentTopic.getPosition().getX(), position.getX());
        assertNotEquals(parentTopic.getPosition().getY(), position.getY());
    }

    @Test
    void testSetNewTopicInitialPosition_NotHome() {
        Topic nonHomeParentTopic = new Topic();
        nonHomeParentTopic.setTopicName("NOT_HOME");
        nonHomeParentTopic.setId("parent-id");
        nonHomeParentTopic.setPosition(new TopicPosition(125, 250));
        nonHomeParentTopic.setEdges(List.of(new Edge("edge-id", "source-id", "parent-id")));

        TopicPosition position = topicConversionService.setNewTopicInitialPosition(nonHomeParentTopic);

        assertNotNull(position);
        assertNotEquals(nonHomeParentTopic.getPosition().getX(), position.getX());
        assertNotEquals(nonHomeParentTopic.getPosition().getY(), position.getY());
    }


    @Test
    void testCreateNewEdgesFromParentName_targetInEdges() {
        when(idService.generateId()).thenReturn("new-edge-id");

        Topic parent = new Topic();
        parent.setTopicName("Test");
        parent.setId("parent-id");
        parent.setEdges(List.of(new Edge("edge-id", "parent-id", "child-id")));

        List<Edge> edges = topicConversionService.createNewEdgesFromParentName(parent, "new-child-id");

        assertEquals(1, edges.size());
        Edge edge = edges.get(0);
        assertEquals("new-edge-id", edge.getId());
        assertEquals("parent-id", edge.getTarget());
    }

    @Test
    void testCreateNewEdgesFromParentName_targetNotInEdges() {
        when(idService.generateId()).thenReturn("new-edge-id");

        Topic parent = new Topic();
        parent.setTopicName("Test");
        parent.setId("parent-id");
        parent.setEdges(List.of(new Edge("edge-id", "child-id", "parent-id")));

        List<Edge> edges = topicConversionService.createNewEdgesFromParentName(parent, "new-child-id");

        assertEquals(1, edges.size());
        Edge edge = edges.get(0);
        assertEquals("new-edge-id", edge.getId());
    }

    @Test
    void testCalcTopicPositionForSubtopics_differentPositionCases() {
        Topic subtopic = new Topic();
        subtopic.setTopicName("Subtopic");
        subtopic.setId("subtopic-id");
        subtopic.setPosition(new TopicPosition(200, 300));
        subtopic.setEdges(List.of(new Edge("subtopic-edge-id", "subtopic-id", "child-id")));

        // Test different position cases by varying xDiff and yDiff values
        TopicPosition position = topicConversionService.calcTopicPositionForSubtopics(subtopic, -50, 50);
        assertNotNull(position);
        assertNotEquals(subtopic.getPosition().getX(), position.getX());
        assertNotEquals(subtopic.getPosition().getY(), position.getY());

        position = topicConversionService.calcTopicPositionForSubtopics(subtopic, 50, -50);
        assertNotNull(position);
        assertNotEquals(subtopic.getPosition().getX(), position.getX());
        assertNotEquals(subtopic.getPosition().getY(), position.getY());

        position = topicConversionService.calcTopicPositionForSubtopics(subtopic, -50, -50);
        assertNotNull(position);
        assertNotEquals(subtopic.getPosition().getX(), position.getX());
        assertNotEquals(subtopic.getPosition().getY(), position.getY());
    }


}