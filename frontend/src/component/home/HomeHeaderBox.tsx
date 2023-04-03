import React from "react";
import "../../style/home/HomeHeaderBox.css"
import FormatDateLocal from "../../hook/subtopic/UseConvertDateToLocal";

export default function HomeHeaderBox() {
    const currentTime = new Date().toString();

    return (
        <div className={"home-header-bar1"}>
            <p className={"text-header-date1"}>{FormatDateLocal({date: currentTime})}</p>
            <p className={"text-header-hello1"}>Welcome, Max</p>
        </div>
    )
}