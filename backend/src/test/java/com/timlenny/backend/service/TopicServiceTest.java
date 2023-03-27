package com.timlenny.backend.service;

import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.repository.TopicRepository;
import com.timlenny.backend.service.topic.TopicConversionService;
import com.timlenny.backend.service.topic.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    TopicRepository topicRepository = mock(TopicRepository.class);
    TopicConversionService topicConversionService = mock(TopicConversionService.class);
    TopicService topicService = new TopicService(topicRepository, topicConversionService);

    Topic demoTopicHome = new Topic(
            "1",
            "NONE",
            "HOME",
            List.of(),
            new TopicPosition(125, 250),
            "HOME",
            "HOME",
            3,
            true
    );
    TopicDTO demoTopicJavaDTO = new TopicDTO(
            "2", "HOME", "Java", 3, new TopicPosition(0, 0)
    );

    Topic demoTopicJava = new Topic(
            "2", "NONE", "Java", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );

    Topic demoTopicJavaChild1 = new Topic(
            "3", "Java", "Java Child 1", List.of(new Edge("32319", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );

    @Test
    void isGetAllTopicsReturningAllTopics_whenGetAllTopicsCalled() {
        when(topicRepository.findAll()).thenReturn(List.of(demoTopicHome));
        List<Topic> actual = topicService.getAllTopics();
        assertEquals(actual, List.of(demoTopicHome));
    }

    @Test
    void isGetAllTopicsReturningAllTopics_whenGetAllTopicsCalledAndRepoIsEmpty() {
        List<Topic> actual = topicService.getAllTopics();
        assertEquals(actual, List.of());
    }

    @Test
    @DirtiesContext
    void isAddTopicAddingNewTopic_whenAddTopicCalled() {
        when(topicRepository.findByTopicName(demoTopicJavaDTO.getParentName())).thenReturn(Optional.ofNullable(demoTopicHome));
        when(topicConversionService.convertNewDTOtoTopic(demoTopicJavaDTO, demoTopicHome)).thenReturn(demoTopicJava);
        when(topicRepository.save(demoTopicJava)).thenReturn(demoTopicJava);

        Topic actual = topicService.addTopic(demoTopicJavaDTO);
        assertEquals(actual, demoTopicJava);
    }

    @Test
    @DirtiesContext
    void isErrorThrown_WhenNameOfTopicToAddExists() {
        String actual = "No Exception";
        when(topicRepository.findByTopicName(demoTopicJavaDTO.getTopicName())).thenReturn(Optional.ofNullable(demoTopicHome));
        try {
            topicService.addTopic(demoTopicJavaDTO);
        } catch (Exception error) {
            actual = error.getMessage();
        }

        assertEquals("409 CONFLICT \"A topic with the name Java already exists\"", actual);
    }

    @Test
    @DirtiesContext
    void isErrorThrown_WhenParentTopicDoesntExists() {
        String actual = "No Exception";
        when(topicRepository.findByTopicName(demoTopicJavaDTO.getParentName())).thenReturn(Optional.empty());
        try {
            topicService.addTopic(demoTopicJavaDTO);
        } catch (Exception error) {
            actual = error.getMessage();
        }

        assertEquals("400 BAD_REQUEST \"parentTopic not found!\"", actual);
    }

    @Test
    @DirtiesContext
    void isTopicDeleteCorrectly_WhenDeleteTopic() {
        when(topicRepository.findByParentId(demoTopicJava.getId())).thenReturn(List.of(demoTopicJavaChild1));
        String actual = topicService.deleteTopic(demoTopicJava.getId());
        assertEquals(actual, demoTopicJava.getId());
    }

    @Test
    @DirtiesContext
    void isTopicPositionUpdateSetCorrect_WhenTopicPositionChanges() {
        when(topicRepository.findById(demoTopicJava.getId())).thenReturn(Optional.ofNullable(demoTopicJava));
        int actual = topicService.updatePositionOfTopics(List.of(demoTopicJavaDTO));
        assertEquals(1, actual);
    }

    @Test
    @DirtiesContext
    void isTopicPositionUpdateThrowException_WhenTopicToUpdateNotExists() {
        try {
            topicService.updatePositionOfTopics(List.of(demoTopicJavaDTO));
        } catch (Exception error) {
            assertEquals("400 BAD_REQUEST \"Topic with id 2 not found!\"", error.getMessage());
        }
    }


}
