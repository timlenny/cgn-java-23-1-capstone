package com.timlenny.backend.controller;

import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @DeleteMapping("/topic/{id}")
    public String deleteTopic(@PathVariable String id) {
        return topicService.deleteTopic(id);
    }
}
