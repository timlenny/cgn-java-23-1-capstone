import React, {useEffect} from 'react';
import '../../style/overview/Overview.css';
import over_banner from "../../style/image/over_banner.png";
import {useNavigate} from "react-router-dom";
import FormatDateLocal from "../../hook/subtopic/UseConvertDateToLocal";
import UseGetSubtopicsToday from "../../hook/subtopic/UseGetSubtopicsToday";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";
import UseGetSubtopicsUpcoming from "../../hook/subtopic/UseGetSubtopicsUpcoming";
import UseGetStreak from "../../hook/stats/UseGetStreak";


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
    const maxLength = 50
    return text.length > maxLength ? text.substring(0, maxLength) + "..." : text;
}

export default function OverviewPage() {
    useAuthRedirect()
    const navigate = useNavigate()
    const {getAllSubtopicsToday, subtopicsToday} = UseGetSubtopicsToday();
    const {getAllSubtopicsUpcoming, subtopicsUpcoming} = UseGetSubtopicsUpcoming();
    const {getStreak, streak} = UseGetStreak();

    useEffect(() => {
        getAllSubtopicsToday()
        getAllSubtopicsUpcoming()
        getStreak();
    }, [getAllSubtopicsToday, getAllSubtopicsUpcoming, getStreak])

    function renderWeekdays(weekdayStatus: boolean[]) {
        const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
        return days.map((day, index) => {
            const cssClass = weekdayStatus[index] ? "weekday" : "weekday_open";
            return (
                <div key={day} className={cssClass}>
                    {day}
                </div>
            );
        });
    }

    function cardsSubtToday() {
        if (subtopicsToday.length > 0) {
            return (<><h3 className={"card-view-title"}>Your today's topics:</h3>
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
            </>)
        }
    }

    function cardsSubtUpcoming() {
        if (subtopicsUpcoming.length > 0) {
            return (<><h3 className={"card-view-title"}>Upcoming topics:</h3>
                <div className="vertical-scroll">
                    {subtopicsUpcoming.map((subtopicT) => (
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
            </>)
        }
    }

    return (
        <div className="container-over">
            <div className="header-over">
                <h1 className={"header-over-title"}>Welcome</h1>
                <h2 className={"header-over-subtitle"}>Your current streak
                    is {streak.reduce((count, status) => count + (status ? 1 : 0), 0)} days for this week </h2>
                <div className="weekdays">
                    {renderWeekdays(streak)}
                </div>
                <div className={"get-motivation"}>{getMotivation()}</div>
            </div>
            <div className="content-over">
                <button className="yellow-box" onClick={() => {
                    navigate("/map")
                }}>
                    <div>
                        <img src={over_banner} alt={"GO TO STUDY MAP"}/>
                        <div className={"open-study-map"}>Go to your StudyMap</div>
                    </div>
                </button>
                {cardsSubtToday()}
                {cardsSubtUpcoming()}
            </div>
        </div>
    );
};


