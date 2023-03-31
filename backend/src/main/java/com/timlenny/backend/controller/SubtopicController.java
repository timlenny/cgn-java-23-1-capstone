package com.timlenny.backend.controller;

import com.timlenny.backend.model.subtopic.Subtopic;
import com.timlenny.backend.model.subtopic.SubtopicDTO;
import com.timlenny.backend.service.subtopic.SubtopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtopic")
public class SubtopicController {
    SubtopicService subtopicService;

    public SubtopicController(SubtopicService subtopicService) {
        this.subtopicService = subtopicService;
    }

    @GetMapping("/{id}")
    public List<Subtopic> getAllSubtopicsFromTopicId(@PathVariable String id) {
        return subtopicService.getAllSubtopicsFromTopicId(id);
    }

    @PostMapping
    public Subtopic addSubtopic(@RequestBody SubtopicDTO subtopicDTO) {
        return subtopicService.addSubtopic(subtopicDTO);
    }

}
