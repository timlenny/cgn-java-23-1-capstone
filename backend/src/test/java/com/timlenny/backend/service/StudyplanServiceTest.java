package com.timlenny.backend.service;

import com.timlenny.backend.model.Edge;
import com.timlenny.backend.model.Topic;
import com.timlenny.backend.model.TopicPosition;
import com.timlenny.backend.repository.StudyplanRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudyplanServiceTest {

    StudyplanRepository studyplanRepository = mock(StudyplanRepository.class);
    StudyplanService studyplanService = new StudyplanService(studyplanRepository);

    Topic demoTopicStart = new Topic(
            "1", "START", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );

    @Test
    void isGetAllTopicsReturningAllTopics_whenGetAllTopicsCalled() {
        when(studyplanRepository.getAllTopics()).thenReturn(List.of(demoTopicStart));
        List<Topic> actual = studyplanService.getAllTopics();
        assertEquals(actual, List.of(demoTopicStart));
    }


}
