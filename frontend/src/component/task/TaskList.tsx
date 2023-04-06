import {TaskType} from "../../model/task/Task";
import React, {useState} from "react";
import HighlightOffIcon from "@mui/icons-material/HighlightOff";
import EditIcon from "@mui/icons-material/Edit";
import UseUpdateTask from "../../hook/task/UseUpdateTask";

export default function TaskList(props: { tasks: TaskType[] }) {
    const [isEditing, setIsEditing] = useState("");
    const [editedTitle, setEditedTitle] = useState("");
    const {updateSingleTask} = UseUpdateTask();

    const taskList = props.tasks.map((task) => {

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
                    <div key={task.id}>{task.title}</div>
                )}
                <div className="icons">
                    <HighlightOffIcon onClick={() => {
                    }}></HighlightOffIcon>
                    <EditIcon style={{paddingLeft: "10px"}} onClick={handleEdit}></EditIcon>
                </div>
            </div>
        );
    });
    return <div>{taskList}</div>;
}