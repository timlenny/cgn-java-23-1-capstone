import {useCallback, useState} from "react";
import axios from "axios";
import {Subtopic} from "../../model/subtopic/Subtopic";

export default function UseGetSubtopicsToday() {
    const [subtopicsToday, setSubtopicsToday] = useState<Subtopic[]>([]);

    const getAllSubtopicsToday = useCallback(async () => {
        try {
            const response = await axios.get("/api/subtopics/today");
            console.log(response);
            setSubtopicsToday(response.data);
        } catch (error) {
            console.error(error);
        }
    }, []);

    return {getAllSubtopicsToday, subtopicsToday, setSubtopicsToday};
}
