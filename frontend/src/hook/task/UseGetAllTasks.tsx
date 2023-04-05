import {useCallback, useState} from "react";
import axios from "axios";
import {TaskType} from "../../model/task/Task";

export default function UseGetAllTasks() {
    const [task, setTask] = useState<TaskType[]>([]);

    const getAllTasks = useCallback(async (subtopicId: string) => {
        try {
            const response = await axios.get("/api/tasks/" + subtopicId);
            setTask(response.data);
        } catch (error) {
            console.error(error);
        }
    }, []);

    return {getAllTasks, task: task, setTask: setTask};
}