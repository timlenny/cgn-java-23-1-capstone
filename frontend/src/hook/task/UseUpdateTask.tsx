import axios from "axios";
import {TaskType} from "../../model/task/Task";

export default function UseUpdateTask() {

    function updateSingleTask(props: TaskType | undefined) {
        if (props) {
            axios.post("/api/tasks/update", {
                id: props.id,
                title: props.title,
                desc: props.desc,
                isCompleted: props.isCompleted,
            }).catch((error) => {
                    console.log(error)
                }
            )
        }
    }

    return {updateSingleTask}
}