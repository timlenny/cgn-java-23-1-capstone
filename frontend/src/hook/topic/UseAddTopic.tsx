import axios from "axios";
import {TopicDTO} from "../../model/topic/TopicDTO";
import {useNavigate} from "react-router-dom";
import {useState} from "react";

export default function UseAddTopic() {
    const navigate = useNavigate();
    const [errorMsg, setErrorMsg] = useState("");
    function postSingleTopic(props: TopicDTO | undefined) {
        if (props?.parentName && props?.topicName && props.size) {
            axios.post("/api/topic", {
                parentName: props.parentName,
                topicName: props.topicName,
                size: props.size,
            })
                .then(() => {
                    navigate("/map")
                })
                .catch((error) => {
                        if (error.response.data.error === "Conflict") {
                            setErrorMsg("A topic with the name " + props.topicName + " already exists");
                        } else if (error.response.data.error === "Bad Request") {
                            setErrorMsg("Parent topic " + props.parentName + " does not exist");
                        } else {
                            setErrorMsg("An error has occurred: " + error.response.data);
                        }
                    }
                )
        } else {
            setErrorMsg("Please fill out all the fields");
        }
    }
    return {postSingleTopic, errorMsg}
}