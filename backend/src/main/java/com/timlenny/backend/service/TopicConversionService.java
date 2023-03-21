package com.timlenny.backend.service;

import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.model.topic.TopicPosition;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class TopicConversionService {

    private final IdService idService;
    private boolean randomAboveBelowHome;
    SecureRandom random = new SecureRandom();

    public TopicConversionService(IdService idService) {
        this.idService = idService;
    }

    public Topic convertNewDTOtoTopic(TopicDTO topicDTO, Topic parentTopic) {
        randomAboveBelowHome = random.nextBoolean();
        Topic newTopic = new Topic();
        newTopic.setTopicName(topicDTO.getTopicName());
        newTopic.setSize(topicDTO.getSize());
        newTopic.setId(idService.generateId());
        newTopic.setEdges(createNewEdgesFromParentName(parentTopic, newTopic.getId()));
        newTopic.setTopicStatus("Pending");
        newTopic.setSubtopicId(idService.generateId());
        newTopic.setOpen(true);
        newTopic.setPosition(setNewTopicInitialPosition(parentTopic));
        return newTopic;
    }

    public List<Edge> createNewEdgesFromParentName(Topic parentTopic, String childTopicID) {
        if (parentTopic.getTopicName().equals("HOME")) {
            if (randomAboveBelowHome) {
                return List.of(new Edge(idService.generateId(), parentTopic.getId(), childTopicID));
            } else {
                return List.of(new Edge(idService.generateId(), childTopicID, parentTopic.getId()));
            }
        } else {
            if (checkTargetInEdges(parentTopic.getEdges(), parentTopic.getId())) {
                return List.of(new Edge(idService.generateId(), parentTopic.getId(), childTopicID));
            } else {
                return List.of(new Edge(idService.generateId(), childTopicID, parentTopic.getId()));
            }
        }
    }

    public static boolean checkTargetInEdges(List<Edge> edges, String targetValue) {
        for (Edge edge : edges) {
            if (edge.getTarget().equals(targetValue)) {
                return true;
            }
        }
        return false;
    }


    public TopicPosition setNewTopicInitialPosition(Topic parentTopic) {
        if (parentTopic.getTopicName().equals("HOME")) {
            return calcTopicPositionForHomeTopics(parentTopic, random.nextInt(151) + 50, random.nextInt(151) + 150);
        } else {
            return calcTopicPositionForSubtopics(parentTopic, random.nextInt(151) + 50, random.nextInt(151) + 150);
        }
    }

    public TopicPosition calcTopicPositionForSubtopics(Topic parentTopic, int randomPositionX, int randomPositionY) {
        if (checkTargetInEdges(parentTopic.getEdges(), parentTopic.getId())) {
            if ((random.nextInt(2) + 1) == 1) {
                return new TopicPosition((parentTopic.getPosition().getX() + randomPositionX), (parentTopic.getPosition().getY() + randomPositionY));
            } else {
                return new TopicPosition((parentTopic.getPosition().getX() - randomPositionX), (parentTopic.getPosition().getY() + randomPositionY));
            }
        } else {
            if ((random.nextInt(2) + 1) == 1) {
                return new TopicPosition((parentTopic.getPosition().getX() - randomPositionX), (parentTopic.getPosition().getY() - randomPositionY));
            } else {
                return new TopicPosition((parentTopic.getPosition().getX() + randomPositionX), (parentTopic.getPosition().getY() - randomPositionY));
            }
        }
    }

    public TopicPosition calcTopicPositionForHomeTopics(Topic parentTopic, int randomPositionX, int randomPositionY) {
        if (randomAboveBelowHome) {
            if ((random.nextInt(2) + 1) == 1) {
                return new TopicPosition((parentTopic.getPosition().getX() + randomPositionX), (parentTopic.getPosition().getY() + randomPositionY));
            } else {
                return new TopicPosition((parentTopic.getPosition().getX() - randomPositionX), (parentTopic.getPosition().getY() + randomPositionY));
            }
        } else {
            if ((random.nextInt(2) + 1) == 1) {
                return new TopicPosition((parentTopic.getPosition().getX() - randomPositionX), (parentTopic.getPosition().getY() - randomPositionY));
            } else {
                return new TopicPosition((parentTopic.getPosition().getX() + randomPositionX), (parentTopic.getPosition().getY() - randomPositionY));
            }
        }
    }

    public Topic initTopic() {
        return new Topic(
                idService.generateId(),
                "HOME",
                List.of(),
                new TopicPosition(125, 250),
                "HOME",
                "HOME",
                3,
                true
        );
    }
}
