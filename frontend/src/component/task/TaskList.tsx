import {TaskType} from "../../model/task/Task";
import React, {useState} from "react";
import HighlightOffIcon from "@mui/icons-material/HighlightOff";
import EditIcon from "@mui/icons-material/Edit";
import UseUpdateTask from "../../hook/task/UseUpdateTask";
import UseDeleteTask from "../../hook/task/UseDeleteTask";

export default function TaskList(props: { tasks: TaskType[] }) {
    const [isEditing, setIsEditing] = useState("");
    const [editedTitle, setEditedTitle] = useState("");
    const {updateSingleTask} = UseUpdateTask();
    const {deleteTask} = UseDeleteTask();
    const [allTasks, setAllTasks] = useState(props.tasks);
    const taskList = allTasks.map((task) => {

        const handleEdit = () => {
            setEditedTitle(task.title)
            if (isEditing) {
                setIsEditing("")
            } else {
                setIsEditing(task.id)
            }
            if (isEditing) {
                task.title = editedTitle;
                updateSingleTask(task)
            }
        };

        const handleTaskClick = (clickedTask: TaskType) => {
            const updatedTasks = allTasks.map((task) => {
                if (task.id === clickedTask.id) {
                    const changedTask = {...task, isCompleted: !clickedTask.isCompleted}
                    updateSingleTask(changedTask)
                    return changedTask
                } else {
                    return task
                }
            })
            setAllTasks(updatedTasks)
        };

        return (
            <div
                className={task.isCompleted ? "task-row complete" : "task-row"}
                key={task.id}
            >
                {isEditing === task.id ? (
                    <input
                        className={"edit-title-task"}
                        value={editedTitle}
                        onChange={(e) => setEditedTitle(e.target.value)}
                    />
                ) : (
                    <div
                        onClick={(event) => {
                            event.stopPropagation();
                            handleTaskClick(task);
                        }}
                        key={task.id}>{task.title}</div>
                )}
                <div className="icons">
                    <HighlightOffIcon onClick={() => {
                        deleteTask(task.id);
                        setAllTasks(allTasks.filter((tasklist) => tasklist.id !== task.id));
                    }}></HighlightOffIcon>
                    <EditIcon style={{paddingLeft: "10px"}} onClick={handleEdit}></EditIcon>
                </div>
            </div>
        );
    });
    return <div>{taskList}</div>;
}