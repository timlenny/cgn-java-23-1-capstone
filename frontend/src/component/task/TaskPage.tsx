import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import "../../style/task/TaskView.css"
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import "../../style/task/TaskHeader.css"
import TaskList from "./TaskList";
import UseGetAllTasks from "../../hook/task/UseGetAllTasks";
import AddIcon from "@mui/icons-material/Add";

export default function TasksPage() {
    const params = useParams();
    const topicId: string | undefined = params.topicId;
    const id: string | undefined = params.id;
    const navigate = useNavigate()
    const {getAllTasks, task} = UseGetAllTasks();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function fetchData() {
            if (id != null) {
                setIsLoading(true);
                try {
                    await getAllTasks(id);
                } catch (error) {
                    console.error(error);
                } finally {
                    setIsLoading(false);
                }
            }
        }

        fetchData();
    }, [id, getAllTasks]);

    if (isLoading) {
        return <h1>Loading...</h1>
    } else {
        console.log(task)
        return (
            <div className={"task-page"}>
                <div className="header-wrapper-task">
                    <button className="backButtonAddSubt">
                        <ChevronLeftIcon onClick={() => {
                            navigate("/subtopic/" + topicId)
                        }} sx={{fontSize: 35}}/>
                    </button>
                    <div className="header-content-subt">
                        <h1>Tasks</h1>
                    </div>
                </div>
                <TaskList tasks={task}></TaskList>
                <button className="taskRoundButtonAdd" onClick={() => {
                    navigate("/tasks/add/" + topicId + "/" + id)
                }}>
                    <AddIcon sx={{fontSize: 35}}/>
                </button>
            </div>
        )
    }
}