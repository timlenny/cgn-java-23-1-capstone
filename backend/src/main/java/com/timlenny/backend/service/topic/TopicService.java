package com.timlenny.backend.service.topic;

import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

        if (parentTopic.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "parentTopic not found!");
        }

        Topic topicToAdd = topicConversionService.convertNewDTOtoTopic(topicToAddDTO, parentTopic.get());
        topicRepository.save(topicToAdd);
        return topicToAdd;
    }

    public String deleteTopic(String id) {
            List<String> deleteIds = new ArrayList<>();
            List<String> resultDeleteIds = getAllChildTopics(id, deleteIds);
            resultDeleteIds.add(id);

            for (String delId : resultDeleteIds) {
                topicRepository.deleteById(delId);
            }

            return id;
    }

    private List<String> getAllChildTopics(String parentId, List<String> deleteIds) {
        List<Topic> childTopics = topicRepository.findByParentId(parentId);

        for (Topic child : childTopics) {
            deleteIds.add(child.getId());
            getAllChildTopics(child.getId(), deleteIds);
        }

        return deleteIds;
    }

    public int updatePositionOfTopics(List<TopicDTO> changedTopics) {
        int updateCounter = 0;
        for (TopicDTO newTopicData : changedTopics) {
            Optional<Topic> topicToUpdate = topicRepository.findById(newTopicData.getTopicId());
            if (topicToUpdate.isPresent()) {
                Topic topic = topicToUpdate.get();
                topic.setPosition(newTopicData.getPosition());
                topicRepository.save(topic);
                updateCounter++;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic with id " + newTopicData.getTopicId() + " not found!");
            }
        }

        return updateCounter;
    }
}
