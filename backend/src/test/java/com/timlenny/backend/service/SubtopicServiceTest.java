package com.timlenny.backend.service;

import com.timlenny.backend.model.subtopic.Subtopic;
import com.timlenny.backend.model.subtopic.SubtopicDTO;
import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.repository.SubtopicRepository;
import com.timlenny.backend.repository.TopicRepository;
import com.timlenny.backend.service.subtopic.SubtopicService;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubtopicServiceTest {

    SubtopicRepository subtopicRepository = mock(SubtopicRepository.class);
    IdService idService = mock(IdService.class);
    TopicRepository topicRepository = mock(TopicRepository.class);
    TimeService timeService = mock(TimeService.class);
    SubtopicService subtopicService = new SubtopicService(subtopicRepository, idService, topicRepository, timeService);

    Instant demoTime = Instant.parse("2022-04-01T10:00:00Z");
    SubtopicDTO demoSubtopic1DTO = new SubtopicDTO("1", 1, demoTime, "Title", "desc");
    Subtopic demoSubtopic1 = new Subtopic("1234", "1", 1, 1, demoTime, "Java", "Title", "desc", demoTime);
    Topic demoTopicJava = new Topic(
            "2", "1", "Java", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", List.of(""), 3, true
    );

    @Test
    @DirtiesContext
    void isGetAllTopicsReturningAllTopics_whenGetAllTopicsCalled() {
        when(subtopicRepository.findByTopicId("1")).thenReturn(List.of(demoSubtopic1));
        List<Subtopic> actual = subtopicService.getAllSubtopicsFromTopicId("1");
        assertEquals(actual, List.of(demoSubtopic1));
    }

    @Test
    @DirtiesContext
    void isSubtopicAddedCorrect_WhenSubtopicAdd() {
        when(topicRepository.findById("1")).thenReturn(Optional.ofNullable(demoTopicJava));
        when(subtopicRepository.save(demoSubtopic1)).thenReturn(demoSubtopic1);
        when(idService.generateId()).thenReturn("1234");
        when(timeService.getCurrentTime()).thenReturn(demoTime);

        Subtopic actual = subtopicService.addSubtopic(demoSubtopic1DTO);
        assertEquals(demoSubtopic1, actual);
    }

}
