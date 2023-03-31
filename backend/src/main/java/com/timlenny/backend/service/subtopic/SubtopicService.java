package com.timlenny.backend.service.subtopic;

import com.timlenny.backend.model.subtopic.Subtopic;
import com.timlenny.backend.model.subtopic.SubtopicDTO;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.repository.SubtopicRepository;
import com.timlenny.backend.repository.TopicRepository;
import com.timlenny.backend.service.IdService;
import com.timlenny.backend.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SubtopicService {
    private final SubtopicRepository subtopicRepository;
    private final IdService idService;
    private final TopicRepository topicRepository;
    private final TimeService timeService;

    public SubtopicService(SubtopicRepository subtopicRepository, IdService idService, TopicRepository topicRepository, TimeService timeService) {
        this.subtopicRepository = subtopicRepository;
        this.idService = idService;
        this.topicRepository = topicRepository;
        this.timeService = timeService;
    }

    public List<Subtopic> getAllSubtopicsFromTopicId(String topicId) {
        return subtopicRepository.findByTopicId(topicId);
    }

    public Subtopic addSubtopic(SubtopicDTO subtopicDTO) {
        Optional<Topic> topicForSub = topicRepository.findById(subtopicDTO.getTopicId());
        if (topicForSub.isPresent()) {
            Subtopic subtopicToAdd = new Subtopic(
                    idService.generateId(),
                    subtopicDTO.getTopicId(),
                    1,
                    subtopicDTO.getPosition(),
                    subtopicDTO.getTimeTermin(),
                    topicForSub.get().getTopicName(),
                    subtopicDTO.getTitle(),
                    subtopicDTO.getDesc(),
                    timeService.getCurrentTime());
            return subtopicRepository.save(subtopicToAdd);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic with id " + subtopicDTO.getTopicId() + " not found!");
        }
    }
}
