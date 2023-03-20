import axios from "axios";
import {TopicDTO} from "../model/topic/TopicDTO";

export default function UseAddTopic() {
    function postSingleTopic(props: TopicDTO | undefined) {
        if (props) {
            axios.post("/api/topic", {
                parentName: props.parentName,
                topicName: props.topicName,
                size: props.size,
            })
                .then(() => {
                    window.location.assign("/");
                })
                .catch((error) => console.error(error))
        }
    }

    return {postSingleTopic}
}