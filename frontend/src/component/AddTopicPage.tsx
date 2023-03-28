import React, {useEffect, useState} from 'react';
import '../style/AddTopicPage.css';
import CheckIcon from '@mui/icons-material/Check';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import {TopicDTO} from "../model/topic/TopicDTO";
import UseAddTopic from "../hook/UseAddTopic";
import {useNavigate} from "react-router-dom";
import {Alert} from "@mui/material";

export default function AddTopicPage() {

    const navigate = useNavigate();
    const {postSingleTopic, errorMsg} = UseAddTopic();
    const [selectedSize, setSelectedSize] = useState(3);
    const [topicData, setTopicData] = useState<TopicDTO>({
        topicId: "",
        parentName: "HOME",
        topicName: "",
        size: 3,
        position: {x: 0, y: 0,}
    });
    const [buildTopic, setBuildTopic] = useState<TopicDTO>();

    const handleSizeButtonClick = (size: number) => {
        setSelectedSize(size);
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, field: keyof TopicDTO) => {
        setTopicData({...topicData, [field]: event.target.value});
    };

    useEffect(() => {
        setBuildTopic({
                topicId: topicData.topicId,
                parentName: topicData.parentName,
                topicName: topicData.topicName,
                size: selectedSize,
                position: topicData.position
            }
        )
    }, [topicData, setTopicData, selectedSize, setSelectedSize])

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
            <p className="label">Parent Topic</p>
            <input
                type="text"
                defaultValue={"HOME"}
                className="inputField"
                onChange={(event) => handleInputChange(event, 'parentName')}
            />
            <p className="helperText">Enter parent topic name to create subtopic</p>
            <p className="label">Name</p>
            <input
                type="text"
                placeholder="New Topic"
                className="inputField"
                onChange={(event) => handleInputChange(event, 'topicName')}
            />
            <p className="label">Size</p>
            <div className="sizeButtons">
                <button
                    className={`sizeButton${selectedSize === 1 ? ' selectedSize' : ''}`}
                    onClick={() => handleSizeButtonClick(1)}
                >
                    S
                </button>
                <button
                    className={`sizeButton${selectedSize === 2 ? ' selectedSize' : ''}`}
                    onClick={() => handleSizeButtonClick(2)}
                >
                    M
                </button>
                <button
                    className={`sizeButton${selectedSize === 3 ? ' selectedSize' : ''}`}
                    onClick={() => handleSizeButtonClick(3)}
                >
                    XL
                </button>
            </div>
            <div className="actionButtons">
                <button className="backButton">
                    <ChevronLeftIcon onClick={() => {
                        navigate("/")
                    }} sx={{fontSize: 35}}/>
                </button>
                <button className="confirmButton">
                    <CheckIcon sx={{fontSize: 35}} onClick={() => {
                        postSingleTopic(buildTopic)
                    }}/>
                </button>
            </div>
        </div>
    );
}
