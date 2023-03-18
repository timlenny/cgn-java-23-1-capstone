package com.timlenny.backend.service;

import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic addTopic(TopicDTO topicToAddDTO) {
        Topic topicToAdd = new Topic(topicToAddDTO);
        topicRepository.save(topicToAdd);
        return topicToAdd;
    }
}
