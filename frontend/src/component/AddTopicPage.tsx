import React, {useEffect, useState} from 'react';
import '../style/AddTopicPage.css';
import CheckIcon from '@mui/icons-material/Check';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import {TopicDTO} from "../model/topic/TopicDTO";
import UseAddTopic from "../hook/UseAddTopic";
import {useNavigate} from "react-router-dom";

export default function AddTopicPage() {

    const navigate = useNavigate();
    const {postSingleTopic} = UseAddTopic();
    const [selectedSize, setSelectedSize] = useState(1);
    const [topicData, setTopicData] = useState<TopicDTO>({parentName: "", topicName: "", size: 1});
    const [buildTopic, setBuildTopic] = useState<TopicDTO>();

    const handleSizeButtonClick = (size: number) => {
        setSelectedSize(size);
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, field: keyof TopicDTO) => {
        setTopicData({...topicData, [field]: event.target.value});
    };

    useEffect(() => {
        setBuildTopic({
                parentName: topicData.parentName,
                topicName: topicData.topicName,
                size: selectedSize
            }
        )
    }, [topicData, setTopicData, selectedSize, selectedSize])


    return (
        <div className="addTopicPage">
            <p className="label">Parent Topic</p>
            <input
                type="text"
                defaultValue={"HOME"}
                className="inputField"
                onChange={(event) => handleInputChange(event, 'parentName')}
            />
            <p className="helperText">A::B to select subtopics</p>
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
