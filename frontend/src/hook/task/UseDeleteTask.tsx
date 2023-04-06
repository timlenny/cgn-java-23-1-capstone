import axios from "axios";

export default function UseDeleteTask() {
    function deleteTask(id: string | undefined) {
        if (id) {
            axios.delete("/api/tasks/" + id)
                .catch((error) => {
                    console.log(error)
                })
        }
    }

    return {deleteTask}
}