package com.timlenny.backend.model.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
   String topicId;
   String parentName;
   String topicName;
   int size;
   TopicPosition position;
}
