import React, {useEffect} from 'react';
import '../../style/overview/Overview.css';
import over_banner from "../../style/image/over_banner.png";
import {useNavigate} from "react-router-dom";
import FormatDateLocal from "../../hook/subtopic/UseConvertDateToLocal";
import UseGetSubtopicsToday from "../../hook/subtopic/UseGetSubtopicsToday";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";


function getMotivation(): string {
    const motivationArray: string[] = [
        "Believe you can and you're halfway there.",
        "Strive for progress, not perfection.",
        "You are capable of amazing things.",
        "Don't let yesterday take up too much of today.",
        "The only way to do great work is to love what you do."
    ];
    const randomIndex: number = Math.floor(Math.random() * motivationArray.length);
    return motivationArray[randomIndex];
}

function truncateText(text: string) {
    const maxLength = 70
    return text.length > maxLength ? text.substring(0, maxLength) + "..." : text;
}

export default function OverviewPage() {
    useAuthRedirect()
    const navigate = useNavigate()
    const {getAllSubtopicsToday, subtopicsToday} = UseGetSubtopicsToday();

    useEffect(() => {
        getAllSubtopicsToday()
    }, [getAllSubtopicsToday])

    return (
        <div className="container-over">
            <div className="header-over">
                <h1 className={"header-over-title"}>Welcome</h1>
                <h2 className={"header-over-subtitle"}>Your current streak is 1 day</h2>
                <div className="weekdays">
                    {['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'].map((day) => (
                        <div key={day} className="weekday">
                            {day}
                        </div>
                    ))}
                </div>
                <div className={"get-motivation"}>{getMotivation()}</div>
            </div>
            <div className="content-over">
                <button className="yellow-box" onClick={() => {
                    navigate("/map")
                }}>
                    <div>
                        <img src={over_banner} alt={"GO TO STUDYMAP"}/>
                        <div className={"open-study-map"}>Go to your StudyMap</div>
                    </div>
                </button>
                <h3 className={"card-view-title"}>Your today's topics:</h3>
                <div className="horizontal-scroll">
                    {subtopicsToday.map((subtopicT) => (
                        <div key={subtopicT.id} className="task" style={{width: '250px'}}>
                            <h3 className="vertical-timeline-element-title">{subtopicT.title}</h3>
                            {subtopicT.subtitel &&
                                <h4 className="vertical-timeline-element-subtitle">{subtopicT.subtitel}</h4>}
                            <div
                                className={"cust-date-class"}>{FormatDateLocal({date: subtopicT.timeTermin.toString()})}</div>
                            {subtopicT.desc && <p>{truncateText(subtopicT.desc)}</p>}
                        </div>
                    ))}

                </div>
                <h3 className={"card-view-title"}>Upcoming topics:</h3>
                <div className="vertical-scroll">
                    {subtopicsToday.map((subtopicT) => (
                        <div key={subtopicT.id} className="task" style={{width: '250px'}}>
                            <h3 className="vertical-timeline-element-title">{subtopicT.title}</h3>
                            {subtopicT.subtitel &&
                                <h4 className="vertical-timeline-element-subtitle">{subtopicT.subtitel}</h4>}
                            <div
                                className={"cust-date-class"}>{FormatDateLocal({date: subtopicT.timeTermin.toString()})}</div>
                            {subtopicT.desc && <p>{truncateText(subtopicT.desc)}</p>}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};


