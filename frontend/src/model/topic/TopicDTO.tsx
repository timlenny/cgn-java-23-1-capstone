export type TopicDTO = {
    topicId: string,
    parentName: string;
    topicName: string;
    size: number;
    position: TopicPosition
}

type TopicPosition = {
    x: number;
    y: number;
}
