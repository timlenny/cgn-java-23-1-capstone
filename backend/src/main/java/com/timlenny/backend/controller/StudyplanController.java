package com.timlenny.backend.controller;

import com.timlenny.backend.model.Topic;
import com.timlenny.backend.service.StudyplanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class StudyplanController {
    StudyplanService studyplanService;

    public StudyplanController(StudyplanService studyplanService) {
        this.studyplanService = studyplanService;
    }

    @GetMapping("/topic")
    public List<Topic> getAllTopics() {
        return studyplanService.getAllTopics();
    }
}
