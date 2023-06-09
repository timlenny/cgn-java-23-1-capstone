import React, {useEffect, useState} from 'react';
import '../../style/topic/AddTopicPage.css';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import {TopicDTO} from "../../model/topic/TopicDTO";
import UseAddTopic from "../../hook/topic/UseAddTopic";
import {useNavigate} from "react-router-dom";
import {Alert} from "@mui/material";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";

export default function AddTopicPage() {
    useAuthRedirect()
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
        <div>
            <div className="header-wrapper-addtopic">
                <button className="backButton">
                    <ChevronLeftIcon onClick={() => {
                        navigate("/map")
                    }} sx={{fontSize: 35}}/>
                </button>
                <div className="header-content-addtopic">
                    <h1>Add new topic</h1>
                </div>
            </div>
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
                <br/>
                <button className={"addTopicPage-confirm-button"} onClick={() => {
                    postSingleTopic(buildTopic)
                }}>CREATE
                </button>
            </div>
        </div>
    );
}
