import {edgesType} from "./Edge";

export type Topic = {
    id: string;
    topicName: string;
    edges: edgesType[];
    position: TopicPosition;
    topicStatus: string;
    subtopicIds: string[];
    size: number;
    open: boolean;
}


type TopicPosition = {
    x: number;
    y: number;
}
