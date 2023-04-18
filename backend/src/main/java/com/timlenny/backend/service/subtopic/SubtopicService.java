package com.timlenny.backend.service.subtopic;

import com.timlenny.backend.model.subtopic.Subtopic;
import com.timlenny.backend.model.subtopic.SubtopicDTO;
import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.repository.SubtopicRepository;
import com.timlenny.backend.repository.TaskRepository;
import com.timlenny.backend.repository.TopicRepository;
import com.timlenny.backend.service.IdService;
import com.timlenny.backend.service.TimeService;
import com.timlenny.backend.service.user.MongoUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SubtopicService {
    private final SubtopicRepository subtopicRepository;
    private final IdService idService;
    private final TopicRepository topicRepository;
    private final TimeService timeService;
    private final TaskRepository taskRepository;
    private final MongoUserService mongoUserService;

    public SubtopicService(SubtopicRepository subtopicRepository, IdService idService, TopicRepository topicRepository, TimeService timeService, TaskRepository taskRepository, MongoUserService mongoUserService) {
        this.subtopicRepository = subtopicRepository;
        this.idService = idService;
        this.topicRepository = topicRepository;
        this.timeService = timeService;
        this.taskRepository = taskRepository;
        this.mongoUserService = mongoUserService;
    }

    public List<Subtopic> getAllSubtopicsFromTopicId(String topicId) {
        List<Subtopic> subtopicsResult = subtopicRepository.findByTopicId(topicId);
        List<Subtopic> subtopics = new ArrayList<>(subtopicsResult);
        subtopics.sort(Comparator.comparingInt(Subtopic::getPosition));
        return subtopics.stream().map(this::calcTreeSizeForSubtopics).toList();
    }

    public Subtopic calcTreeSizeForSubtopics(Subtopic subtopic) {
        List<Task> tasksResult = taskRepository.findBySubtopicId(subtopic.getId());
        List<Task> doneTasks = tasksResult.stream().filter(Task::getIsCompleted).toList();
        if (doneTasks.size() < (tasksResult.size() / 2)) {
            subtopic.setIconStatus(1);
        } else if ((doneTasks.size() >= (tasksResult.size() / 2)) && (doneTasks.size() < tasksResult.size()) && (!doneTasks.isEmpty())) {
            subtopic.setIconStatus(2);
        } else if ((doneTasks.size() == tasksResult.size()) && (!doneTasks.isEmpty())) {
            subtopic.setIconStatus(3);
        } else {
            subtopic.setIconStatus(1);
        }
        return subtopic;
    }

    public Subtopic addSubtopic(SubtopicDTO subtopicDTO) {
        Optional<Topic> topicForSub = topicRepository.findById(subtopicDTO.getTopicId());
        if (topicForSub.isPresent()) {
            Subtopic subtopicToAdd = new Subtopic(
                    idService.generateId(),
                    subtopicDTO.getTopicId(),
                    1,
                    calcSubtopicPosition(subtopicDTO, topicForSub.get().getId()),
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

    public int calcSubtopicPosition(SubtopicDTO subtopicDTO, String topicId) {
        List<Subtopic> allSubtopicsFromTopicResult = subtopicRepository.findByTopicId(topicId);

        List<Subtopic> allSubtopicsFromTopic = new ArrayList<>(allSubtopicsFromTopicResult);
        allSubtopicsFromTopic.sort(Comparator.comparingInt(Subtopic::getPosition));


        List<Subtopic> subtopicsSamePosition = (allSubtopicsFromTopic.stream().filter((subtopic -> subtopic.getPosition() == subtopicDTO.getPosition())).toList());

        if (!subtopicsSamePosition.isEmpty()) {

            for (Subtopic subt : allSubtopicsFromTopic) {

                if (subt.getPosition() >= subtopicsSamePosition.get(0).getPosition()) {
                    subt.setPosition((subt.getPosition() + 1));
                    subtopicRepository.save(subt);
                }
            }
        }
        return subtopicDTO.getPosition();
    }

    public String deleteSubtopic(String id) {
        subtopicRepository.deleteById(id);
        return id;
    }

    public List<Subtopic> getAllSubtopicsToday(boolean upcoming) {
        List<String> allUserTopics = mongoUserService.loadTopicsFromCurrentUser();
        List<Subtopic> allUserSubtopics = new ArrayList<>();

        Instant now = Instant.now();
        ZonedDateTime zdtNow = ZonedDateTime.ofInstant(now, ZoneId.systemDefault());
        ZonedDateTime zdtTomorrowMidnight = zdtNow.plusDays(1).toLocalDate().atStartOfDay(zdtNow.getZone());
        Instant tomorrowMidnight = zdtTomorrowMidnight.toInstant();

        for (String topicId : allUserTopics) {
            List<Subtopic> subtopicsFromTopicId = this.getAllSubtopicsFromTopicId(topicId);
            allUserSubtopics.addAll(subtopicsFromTopicId);
        }

        if (upcoming) {
            allUserSubtopics = allUserSubtopics.stream()
                    .filter(subtopic -> subtopic.getTimeTermin().isAfter(tomorrowMidnight) && subtopic.getIconStatus() != 3)
                    .limit(5)
                    .toList();
        } else {
            allUserSubtopics = allUserSubtopics.stream()
                    .filter(subtopic -> subtopic.getTimeTermin().isBefore(tomorrowMidnight) && subtopic.getIconStatus() != 3)
                    .toList();
        }

        List<Subtopic> subtopicsToday = new ArrayList<>(allUserSubtopics);
        subtopicsToday.sort(Comparator.comparing(Subtopic::getTimeTermin));
        return subtopicsToday;
    }


}
