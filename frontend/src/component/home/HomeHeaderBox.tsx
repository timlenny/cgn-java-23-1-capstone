import React from "react";
import "../../style/home/HomeHeaderBox.css"
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import {useNavigate} from "react-router-dom";

export default function HomeHeaderBox() {
    const navigate = useNavigate()

    return (
        <div className={"home-header-bar1"}>
            <p className={"text-header-hello1"}>Study Map</p>
            <button className="backButtonAddSubt">
                <ChevronLeftIcon onClick={() => {
                    navigate("/")
                }} sx={{fontSize: 35}}/>
            </button>
        </div>
    )
}