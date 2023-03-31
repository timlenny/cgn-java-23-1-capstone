import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {SubtopicDTO} from "../../model/subtopic/SubtopicDTO";

export default function UseAddSubtopic() {
    const navigate = useNavigate();
    const [errorMsg, setErrorMsg] = useState("");

    function postSingleSubtopic(props: SubtopicDTO | undefined) {
        if (props?.title && props?.desc && props.timeTermin) {
            console.log(props.topicId)
            axios.post("/api/subtopic", {
                topicId: props.topicId,
                position: props.position,
                timeTermin: props.timeTermin.toISOString(),
                title: props.title,
                desc: props.desc
            })
                .then(() => {
                    navigate("/subtopic/" + props.topicId,)
                })
                .catch((error) => {
                        console.log(error)
                    }
                )
        } else {
            setErrorMsg("Please fill out all the fields");
        }
    }

    return {postSingleSubtopic, errorMsg}
}