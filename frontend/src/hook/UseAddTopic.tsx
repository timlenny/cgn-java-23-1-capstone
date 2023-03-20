import axios from "axios";
import {TopicDTO} from "../model/topic/TopicDTO";
import {useNavigate} from "react-router-dom";

export default function UseAddTopic() {
    const navigate = useNavigate();
    function postSingleTopic(props: TopicDTO | undefined) {
        if (props) {
            axios.post("/api/topic", {
                parentName: props.parentName,
                topicName: props.topicName,
                size: props.size,
            })
                .then(() => {
                    navigate("/")
                })
                .catch((error) => console.error(error))
        }
    }

    return {postSingleTopic}
}