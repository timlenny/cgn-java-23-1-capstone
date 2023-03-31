import headerbgr from "../../style/image/headerbgr.png";
import React from "react";
import "../../style/home/HomeHeaderBox.css"

export default function HomeHeaderBox() {
    return (
        <div className={"home-header-bar"}>
            <p className={"text-header-hello"}>Hello. Let's start.</p>
            <img src={headerbgr} className={"homepage-header-img"} alt={""}/>
        </div>
    )
}