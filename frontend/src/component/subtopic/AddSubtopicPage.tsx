import React, {useEffect, useState} from 'react';
import '../../style/topic/AddTopicPage.css';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import {useNavigate, useParams} from "react-router-dom";
import {Alert, Box, createTheme, ThemeProvider} from "@mui/material";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";
import {SubtopicDTO} from "../../model/subtopic/SubtopicDTO";
import UseAddSubtopic from "../../hook/subtopic/UseAddSubtopic";
import {DateTimePicker, LocalizationProvider} from '@mui/x-date-pickers';
import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";

export default function AddSubtopicPage() {
    const params = useParams();
    const id: string | undefined = params.id;
    const length: string | undefined = params.length;
    useAuthRedirect()
    const navigate = useNavigate();
    const {postSingleSubtopic, errorMsg} = UseAddSubtopic();
    const [subtopicData, setSubtopicData] = useState<SubtopicDTO>({
        topicId: id ? id : "",
        position: parseInt(length ? length : "1"),
        timeTermin: new Date(),
        title: "",
        desc: "",
    });
    const [buildSubtopic, setBuildSubtopic] = useState<SubtopicDTO>();
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, field: keyof SubtopicDTO) => {
        setSubtopicData({...subtopicData, [field]: event.target.value});
    };

    const themeTime = createTheme({
        palette: {
            primary: {
                main: '#E28038',
            },
        },
    });

    useEffect(() => {
        setBuildSubtopic({
                topicId: subtopicData.topicId,
                position: subtopicData.position,
                timeTermin: subtopicData.timeTermin,
                title: subtopicData.title,
                desc: subtopicData.desc,
            }
        )
    }, [subtopicData, setSubtopicData])

    function ifErrordisplayError() {
        if (errorMsg !== "") {
            return (
                <Alert severity="warning">{errorMsg}</Alert>
            )
        }
    }

    function handleDateTimeChange(value: Date | null) {
        if (value) {
            setSubtopicData({...subtopicData, timeTermin: value});
        }
    }

    function handleInputDesc(event: React.ChangeEvent<HTMLTextAreaElement>) {
        if (event.target.value) {
            setSubtopicData({...subtopicData, desc: event.target.value});
        }
    }

    return (
        <div>
            <div className="header-wrapper-addsubt">
                <button className="backButtonAddSubt">
                    <ChevronLeftIcon onClick={() => {
                        navigate("/subtopic/" + id)
                    }} sx={{fontSize: 35}}/>
                </button>
                <div className="header-content-addsubt">
                    <h1>Add new subtopic</h1>
                </div>
            </div>
            <div className="addTopicPage">
                {ifErrordisplayError()}
                <p className="label">Title</p>
                <input
                    type="text"
                    placeholder={"Name of the subtopic"}
                    defaultValue={""}
                    className="inputField"
                    onChange={(event) => handleInputChange(event, 'title')}
                />
                <p className="label">Description</p>
                <textarea
                    placeholder="Insert a description"
                    className="inputField"
                    maxLength={500}
                    style={{height: '10%', paddingTop: '10px'}}
                    onChange={(event) => handleInputDesc(event)}
                />
                <p className="label">Date</p>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <ThemeProvider theme={themeTime}>
                        <Box sx={{width: "70%"}}>
                            <DateTimePicker
                                value={subtopicData.timeTermin}
                                onChange={(value) => {
                                    handleDateTimeChange(value);
                                }}
                                disableFuture={false}
                                sx={{
                                    border: "1px solid white",
                                    borderRadius: "5px",
                                    "& input": {
                                        color: "#1E364E",
                                    },
                                    width: "100%",
                                    "& .MuiPickersBasePicker-pickerView": {
                                        width: "100%",
                                    },
                                }}
                            />
                        </Box>
                    </ThemeProvider>
                </LocalizationProvider>
                <p className="label">Position</p>
                <input
                    defaultValue={parseInt(length ? length : "1")}
                    type="number"
                    placeholder=""
                    className="inputField"
                    onChange={(event) => handleInputChange(event, 'position')}
                    style={{width: '15%'}}
                    onWheel={(event) => event.currentTarget.blur()}
                />
                <br/>
                <button className={"addSubtopicPage-confirm-button"} onClick={() => {
                    postSingleSubtopic(buildSubtopic)
                }}>CREATE
                </button>
            </div>
        </div>
    );
}
