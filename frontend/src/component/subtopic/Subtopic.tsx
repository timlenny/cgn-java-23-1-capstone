import React from 'react';
import {VerticalTimeline, VerticalTimelineElement} from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import '../../style/subtopic/Subtopic.css'
import headerbgr from "../../style/image/headerbgr.png"

export default function Subtopic() {
    const timeline = [
        {
            id: "3123-21321-21321",
            icon: "",
            position: "",
            date: '01.05.2023, 12:15 Uhr',
            title: 'Java',
            subtitle: 'Grundlagen Java',
            desc: 'Was ist Java? Was sind Klasssen und Objekte?'
        },
    ];

    return (
        <div>
            <img src={headerbgr} height={"120"} width={"100%"} className={"subtopic-header-img"} alt={""}/>
            <div className={"vertical-timeline-wrapper"}>
                <VerticalTimeline>
                    {timeline.map((t) => {
                        const contentStyle = {background: 'white', color: 'black'};
                        const arrowStyle = {borderRight: '7px solid  white'};
                        return (
                            <VerticalTimelineElement
                                key={t.id}
                                className="vertical-timeline-element--work"
                                contentStyle={contentStyle}
                                contentArrowStyle={arrowStyle}
                                date={t.date}
                                icon={t.icon}
                                iconStyle={{background: 'white', color: "blue", border: "none", boxShadow: "none"}}
                            >
                                {t.title ? (
                                    <React.Fragment>
                                        <h3 className="vertical-timeline-element-title">{t.title}</h3>
                                        {t.subtitle &&
                                            <h4 className="vertical-timeline-element-subtitle">{t.subtitle}</h4>}
                                        {t.desc && <p>{t.desc}</p>}
                                    </React.Fragment>
                                ) : undefined}
                            </VerticalTimelineElement>
                        );
                    })}
                </VerticalTimeline>
                <br/>
            </div>
        </div>
    );
}