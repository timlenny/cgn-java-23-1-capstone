package com.timlenny.backend.service.topic;

import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.repository.TopicRepository;
import com.timlenny.backend.service.IdService;
import com.timlenny.backend.service.user.MongoUserService;
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
    MongoUserService mongoUserService;
    IdService idService;

    public TopicService(TopicRepository topicRepository, TopicConversionService topicConversionService, MongoUserService mongoUserService, IdService idService) {
        this.topicRepository = topicRepository;
        this.topicConversionService = topicConversionService;
        this.mongoUserService = mongoUserService;
        this.idService = idService;
    }

    public List<Topic> getAllTopics() {
        List<Topic> topicIdsUser = topicRepository.findAllById(mongoUserService.loadTopicsFromCurrentUser());
        if (topicIdsUser.isEmpty()) {
            return setInitHomeTopic();
        } else {
            return topicIdsUser;
        }
    }

    public List<Topic> setInitHomeTopic() {
        Topic initTopic = new Topic(
                idService.generateId(),
                idService.generateId(),
                "HOME",
                List.of(),
                new TopicPosition(125, 250),
                "HOME",
                "HOME",
                3,
                true
        );
        topicRepository.save(initTopic);
        mongoUserService.addTopicIdToUser(initTopic.getId());
        return List.of(initTopic);
    }

    public Topic addTopic(TopicDTO topicToAddDTO) {
        List<String> userTopicIds = mongoUserService.loadTopicsFromCurrentUser();
        Optional<Topic> duplicateCheck = topicRepository.findByTopicNameAndIdIn(topicToAddDTO.getTopicName(), userTopicIds);
        if (duplicateCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A topic with the name " + topicToAddDTO.getTopicName() + " already exists");
        }

        Optional<Topic> parentTopic = topicRepository.findByTopicNameAndIdIn(topicToAddDTO.getParentName(), userTopicIds);

        if (parentTopic.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "parentTopic not found!");
        }

        Topic topicToAdd = topicConversionService.convertNewDTOtoTopic(topicToAddDTO, parentTopic.get());
        mongoUserService.addTopicIdToUser(topicToAdd.getId());
        topicRepository.save(topicToAdd);
        return topicToAdd;
    }

    public String deleteTopic(String id) {
            List<String> deleteIds = new ArrayList<>();
            List<String> resultDeleteIds = getAllChildTopics(id, deleteIds);
            resultDeleteIds.add(id);

            for (String delId : resultDeleteIds) {
                mongoUserService.removeTopicIdFromUser(delId);
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
