import React, {useEffect, useState} from 'react';
import '../../style/topic/AddTopicPage.css';
import CheckIcon from '@mui/icons-material/Check';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import {useNavigate, useParams} from "react-router-dom";
import {Alert} from "@mui/material";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";
import {SubtopicDTO} from "../../model/subtopic/SubtopicDTO";
import UseAddSubtopic from "../../hook/subtopic/UseAddSubtopic";

export default function AddSubtopicPage() {
    const params = useParams();
    const id: string | undefined = params.id;
    useAuthRedirect()
    const navigate = useNavigate();
    const {postSingleSubtopic, errorMsg} = UseAddSubtopic();
    const [subtopicData, setSubtopicData] = useState<SubtopicDTO>({
        topicId: id ? id : "",
        position: 1,
        timeTermin: new Date(),
        title: "",
        desc: "",
    });
    const [buildSubtopic, setBuildSubtopic] = useState<SubtopicDTO>();
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, field: keyof SubtopicDTO) => {
        setSubtopicData({...subtopicData, [field]: event.target.value});
    };

    useEffect(() => {
        setBuildSubtopic({
                topicId: subtopicData.topicId,
                position: subtopicData.position,
                timeTermin: new Date(),
                title: subtopicData.title,
                desc: subtopicData.desc,
            }
        )
    }, [subtopicData, setSubtopicData])

    function ifErrordisplayError() {
        if (errorMsg !== "") {
            return (
                <Alert severity="warning">{errorMsg}</Alert>
            )
        }
    }

    return (
        <div className="addTopicPage">
            {ifErrordisplayError()}
            <p className="label">Title</p>
            <input
                type="text"
                defaultValue={""}
                className="inputField"
                onChange={(event) => handleInputChange(event, 'title')}
            />
            <p className="label">Description</p>
            <input
                type="text"
                placeholder=""
                className="inputField"
                onChange={(event) => handleInputChange(event, 'desc')}
            />
            <p className="label">Date</p>
            <input
                type="text"
                placeholder=""
                className="inputField"
                onChange={(event) => handleInputChange(event, 'timeTermin')}
            />
            <p className="label">Position</p>
            <input
                type="number"
                placeholder=""
                className="inputField"
                onChange={(event) => handleInputChange(event, 'position')}
            />
            <div className="actionButtons">
                <button className="backButton">
                    <ChevronLeftIcon onClick={() => {
                        navigate("/")
                    }} sx={{fontSize: 35}}/>
                </button>
                <button className="confirmButton">
                    <CheckIcon sx={{fontSize: 35}} onClick={() => {
                        postSingleSubtopic(buildSubtopic)
                    }}/>
                </button>
            </div>
        </div>
    );
}
