package com.timlenny.backend.controller;

import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.service.topic.TopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TopicController {
    TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topic")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("/topic")
    public Topic addTopic(@RequestBody TopicDTO topicToAddDTO) {
        return topicService.addTopic(topicToAddDTO);
    }

    @PutMapping("/topic/positions")
    public int updatePositionOfTopics(@RequestBody List<TopicDTO> changedTopics) {
        return topicService.updatePositionOfTopics(changedTopics);
    }

    @DeleteMapping("/topic/{id}")
    public String deleteTopic(@PathVariable String id) {
        return topicService.deleteTopic(id);
    }
}
