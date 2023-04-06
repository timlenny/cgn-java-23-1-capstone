import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {AddTaskAxiosData} from "../../component/task/AddTaskPage";

export default function UseAddTask() {
    const navigate = useNavigate();
    const [errorMsg, setErrorMsg] = useState("");

    function postSingleTask(props: AddTaskAxiosData | undefined) {
        if (props?.title && props.subtopicId) {
            axios.post("/api/tasks", {
                subtopicId: props.subtopicId,
                title: props.title,
                desc: props.desc,
                isCompleted: props.isCompleted,
            }).then(() => {
                navigate("/tasks/" + props.topicId + "/" + props.id)
            })
                .catch((error) => {
                        console.log(error)
                    }
                )
        } else {
            setErrorMsg("Please fill out the title field");
        }
    }

    return {postSingleTask, errorMsg}
}