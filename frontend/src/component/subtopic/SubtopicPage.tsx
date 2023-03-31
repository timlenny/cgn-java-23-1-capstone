import React, {useEffect, useState} from 'react';
import {VerticalTimeline, VerticalTimelineElement} from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import '../../style/subtopic/Subtopic.css'
import headerbgr from "../../style/image/headerbgr.png"
import UseGetSubtopicData from "../../hook/subtopic/UseGetSubtopicData";
import {useNavigate, useParams} from "react-router-dom";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";
import AddIcon from "@mui/icons-material/Add";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";

export default function SubtopicPage() {
    useAuthRedirect()
    const params = useParams();
    const id: string | undefined = params.id;
    const [deleteMode, setDeleteMode] = useState(false);
    const navigate = useNavigate()
    const {getAllSubtopics, subtopics} = UseGetSubtopicData();

    useEffect(() => {
        if (id != null) {
            getAllSubtopics(id);
        }
    }, [getAllSubtopics])

    return (
        <div>
            <img src={headerbgr} height={"120"} width={"100%"} className={"subtopic-header-img"} alt={""}/>
            <div className={"vertical-timeline-wrapper"}>
                <VerticalTimeline>
                    {subtopics.map((t) => {
                        const contentStyle = {background: 'white', color: 'black'};
                        const arrowStyle = {borderRight: '7px solid  white'};
                        return (
                            <VerticalTimelineElement
                                key={t.id}
                                className="vertical-timeline-element--work"
                                contentStyle={contentStyle}
                                contentArrowStyle={arrowStyle}
                                date={t.timeTermin.toString()}
                                iconStyle={{background: 'white', color: "blue", border: "none", boxShadow: "none"}}
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
                </VerticalTimeline>
                <button className="subtopicButtonAdd" onClick={() => {
                    navigate("/subtopic/add/" + id)
                }}>
                    <AddIcon sx={{fontSize: 35}}/>
                </button>
                <button className={deleteMode ? "subtopicButtonDel-active" : "subtopicButtonDel"} onClick={() => {
                    setDeleteMode(deleteMode => !deleteMode)
                }}>
                    <DeleteOutlineIcon sx={{fontSize: 35}}/>
                </button>
                <br/>
            </div>
        </div>
    );
}