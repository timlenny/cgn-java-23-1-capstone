package com.timlenny.backend.service;

import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    TopicRepository topicRepository;
    TopicConversionService topicConversionService;

    public TopicService(TopicRepository topicRepository, TopicConversionService topicConversionService) {
        this.topicRepository = topicRepository;
        this.topicConversionService = topicConversionService;
    }

    public List<Topic> getAllTopics() {
        List<Topic> allTopics = topicRepository.findAll();
        if (allTopics.isEmpty()) {
            allTopics = initialTopicDataSetup();
        }
        return allTopics;
    }

    private List<Topic> initialTopicDataSetup() {
        topicRepository.save(topicConversionService.initTopic());
        return topicRepository.findAll();
    }

    public Topic addTopic(TopicDTO topicToAddDTO) {
        Optional<Topic> duplicateCheck = topicRepository.findByTopicName(topicToAddDTO.getTopicName());
        if (duplicateCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A topic with the name " + topicToAddDTO.getTopicName() + " already exists");
        }

        Optional<Topic> parentTopic = topicRepository.findByTopicName(topicToAddDTO.getParentName());
        if (parentTopic.isPresent()) {
            Topic topicToAdd = topicConversionService.convertNewDTOtoTopic(topicToAddDTO, parentTopic.get());
            topicRepository.save(topicToAdd);
            return topicToAdd;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "parentTopic not found!");
        }
    }
}
