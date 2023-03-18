import {edgesType} from "./Edge";

export type Topic = {
    id: string;
    topicName: string;
    edges: edgesType[];
    position: TopicPosition;
    topicStatus: string;
    subtopicId: string;
    size: number;
    open: boolean;
}


type TopicPosition = {
    x: number;
    y: number;
}
