import axios from "axios";
import {TopicDTO} from "../model/topic/TopicDTO";
import {nodeType} from "../component/HomePage";

export default function UseUpdateTopicPosition() {
    function updateTopicPosition(props: nodeType[]) {

        const topicsToUpdate = props.map((node) => {
            const topic: TopicDTO = {
                topicId: node.id,
                parentName: "",
                topicName: node.data.label,
                size: 0,
                position: node.position
            }
            return topic
        })

        axios.put("/api/topic/positions", topicsToUpdate)
            .then((data) => {
               return data.data
            }).catch((error) => {
            console.error(error)
        })
    }

    return updateTopicPosition;

}