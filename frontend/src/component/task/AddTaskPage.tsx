import React, {useEffect, useState} from 'react';
import '../../style/topic/AddTopicPage.css';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import {useNavigate, useParams} from "react-router-dom";
import {Alert} from "@mui/material";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";
import {SubtopicDTO} from "../../model/subtopic/SubtopicDTO";
import {TaskDTO} from "../../model/task/TaskDTO";
import "../../style/task/AddTask.css"
import UseAddTask from "../../hook/task/UseAddTask";

export type AddTaskAxiosData = {
    subtopicId: string;
    title: string;
    desc: string;
    isCompleted: boolean;
    topicId: string;
    id: string;
}
export default function AddTaskPage() {
    const params = useParams();
    const topicId: string | undefined = params.topicId;
    const id: string | undefined = params.id;
    useAuthRedirect()

    const navigate = useNavigate();
    const {postSingleTask: postSingleTask, errorMsg} = UseAddTask();
    const [taskData, setTaskData] = useState<TaskDTO>({
        subtopicId: id ? id : "",
        title: "",
        desc: "",
        isCompleted: false,
    });
    const [buildTask, setBuildTask] = useState<AddTaskAxiosData>();
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, field: keyof SubtopicDTO) => {
        setTaskData({...taskData, [field]: event.target.value});
    };


    useEffect(() => {
        setBuildTask({
            subtopicId: id ? id : "",
            title: taskData.title,
            desc: taskData.desc,
            isCompleted: false,
            topicId: topicId ? topicId : "",
            id: id ? id : ""
        })

    }, [taskData, setTaskData])

    function ifErrordisplayError() {
        if (errorMsg !== "") {
            return (
                <Alert severity="warning">{errorMsg}</Alert>
            )
        }
    }


    function handleInputDesc(event: React.ChangeEvent<HTMLTextAreaElement>) {
        if (event.target.value) {
            setTaskData({...taskData, desc: event.target.value});
        }
    }

    return (
        <div>
            <div className="header-wrapper-addtask">
                <button className="backButtonAddSubt">
                    <ChevronLeftIcon onClick={() => {
                        navigate("/tasks/" + topicId + "/" + id)
                    }} sx={{fontSize: 35}}/>
                </button>
                <div className="header-content-addsubt">
                    <h1>Add new task</h1>
                </div>
            </div>
            <div className="addTopicPage">
                {ifErrordisplayError()}
                <p className="label">Title</p>
                <input
                    type="text"
                    placeholder={"Name of the task"}
                    defaultValue={""}
                    className="inputField"
                    onChange={(event) => handleInputChange(event, 'title')}
                />
                <p className="label">Description</p>
                <textarea
                    placeholder="Insert a description"
                    className="inputField"
                    maxLength={500}
                    style={{height: '10%', paddingTop: '10px'}}
                    onChange={(event) => handleInputDesc(event)}
                />
                <br/>
                <button className={"addTaskPage-confirm-button"} onClick={() => {
                    postSingleTask(buildTask)
                }}>CREATE
                </button>
            </div>
        </div>
    );
}
