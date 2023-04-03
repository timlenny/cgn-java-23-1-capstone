import React, {useEffect, useState} from 'react';
import {VerticalTimeline, VerticalTimelineElement} from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import '../../style/subtopic/Subtopic.css'
import addsubtopic from "../../style/image/addsubtopic.png"
import UseGetSubtopicData from "../../hook/subtopic/UseGetSubtopicData";
import {useNavigate, useParams} from "react-router-dom";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";
import AddIcon from "@mui/icons-material/Add";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import FormatDateLocal from "../../hook/subtopic/UseConvertDateToLocal";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";

export default function SubtopicPage() {
    useAuthRedirect()
    const params = useParams();
    const id: string | undefined = params.id;
    const [deleteMode, setDeleteMode] = useState(false);
    const navigate = useNavigate()
    const {getAllSubtopics, subtopics} = UseGetSubtopicData();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function fetchData() {
            if (id != null) {
                setIsLoading(true);
                try {
                    await getAllSubtopics(id);
                } catch (error) {
                    console.error(error);
                } finally {
                    setIsLoading(false);
                }
            }
        }

        fetchData();
    }, [id, getAllSubtopics]);

    function buildVerticalTimeline() {
        if (isLoading) {
            return <text></text>
        } else if (subtopics.length > 0) {
            return (<VerticalTimeline lineColor={'linear-gradient(to bottom, #D09934, #BB4720)'}>
                {subtopics.map((t) => {
                    const contentStyle = {
                        background: 'white', color: 'black', borderLeft: "7px solid transparent",
                        borderImageSource: 'linear-gradient(to bottom, #D19E35, #B2281C)',
                        borderImageSlice: 1,
                    };
                    const arrowStyle = {borderRight: '0px solid  white'};
                    return (
                        <VerticalTimelineElement
                            key={t.id}
                            className="vertical-timeline-element--work"
                            contentStyle={contentStyle}
                            contentArrowStyle={arrowStyle}
                            date={FormatDateLocal({date: t.timeTermin.toString()})}
                            iconStyle={{
                                background: 'linear-gradient(to right, #D19E35, #B2281C)',
                                color: "blue",
                                border: "none",
                                boxShadow: "none"
                            }}
                        >
                            {t.title ? (
                                <React.Fragment>
                                    <h3 className="vertical-timeline-element-title">{t.title}</h3>
                                    {t.subtitel &&
                                        <h4 className="vertical-timeline-element-subtitle">{t.subtitel}</h4>}
                                    {t.desc && <p>{t.desc}</p>}
                                </React.Fragment>
                            ) : undefined}
                        </VerticalTimelineElement>
                    );
                })}
            </VerticalTimeline>)
        } else {
            return (
                <div className={"empty-subtopic-dialog"}>
                    <img src={addsubtopic} width={"50%"} alt={""}/>
                    <text>Start your journey and create new subtopics</text>
                </div>
            )
        }
    }

    function displayTopicNameIfExists() {
        if (subtopics.length > 0) {
            return (
                <div className="header-content-subt">
                    <h1>{subtopics[0].title}</h1>
                </div>)
        }
    }

    return (
        <div>
            <div className="header-wrapper-subt">
                <button className="backButtonAddSubt">
                    <ChevronLeftIcon onClick={() => {
                        navigate("/")
                    }} sx={{fontSize: 35}}/>
                </button>
                {displayTopicNameIfExists()}
            </div>
            <div className={"vertical-timeline-wrapper"}>
                {buildVerticalTimeline()}
                <button className="subtopicButtonAdd" onClick={() => {
                    navigate("/subtopic/add/" + id + "/" + (subtopics.length + 1))
                }}>
                    <AddIcon sx={{fontSize: 35}}/>
                </button>
                <button className={deleteMode ? "subtopicButtonDel-active" : "subtopicButtonDel"} onClick={() => {
                    setDeleteMode(deleteMode => !deleteMode)
                }}>
                    <DeleteOutlineIcon sx={{fontSize: 30, color: "#1B334C"}}/>
                </button>
                <br/>
            </div>
        </div>
    );
}