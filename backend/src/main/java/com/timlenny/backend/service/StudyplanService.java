package com.timlenny.backend.service;

import com.timlenny.backend.model.Topic;
import com.timlenny.backend.repository.StudyplanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyplanService {
    StudyplanRepository studyplanRepository;

    public StudyplanService(StudyplanRepository studyplanRepository) {
        this.studyplanRepository = studyplanRepository;
    }

    public List<Topic> getAllTopics() {
        return studyplanRepository.findAll();
    }

}
