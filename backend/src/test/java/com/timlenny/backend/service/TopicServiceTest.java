package com.timlenny.backend.service;

import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.repository.TopicRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    TopicRepository topicRepository = mock(TopicRepository.class);
    TopicConversionService topicConversionService = mock(TopicConversionService.class);
    TopicService topicService = new TopicService(topicRepository, topicConversionService);

    Topic demoTopicStart = new Topic(
            "1", "START", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );
    TopicDTO demoTopicStartDTO = new TopicDTO(
            "1", "START", 3
    );

    @Test
    void isGetAllTopicsReturningAllTopics_whenGetAllTopicsCalled() {
        when(topicRepository.findAll()).thenReturn(List.of(demoTopicStart));
        List<Topic> actual = topicService.getAllTopics();
        assertEquals(actual, List.of(demoTopicStart));
    }

    @Test
    void isGetAllTopicsReturningAllTopics_whenGetAllTopicsCalledAndRepoIsEmpty() {
        List<Topic> actual = topicService.getAllTopics();
        assertEquals(actual, List.of());
    }

    @Test
    void isAddTopicAddingNewTopic_whenAddTopicCalled() {
        when(topicRepository.save(demoTopicStart)).thenReturn(demoTopicStart);
        Topic actual = topicService.addTopic(demoTopicStartDTO);
        assertEquals(actual.getTopicName(), demoTopicStart.getTopicName());
    }
}
