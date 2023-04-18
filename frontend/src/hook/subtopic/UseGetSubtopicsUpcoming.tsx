import {useCallback, useState} from "react";
import axios from "axios";
import {Subtopic} from "../../model/subtopic/Subtopic";

export default function UseGetSubtopicsUpcoming() {
    const [subtopicsUpcoming, setSubtopicsUpcoming] = useState<Subtopic[]>([]);


    const getAllSubtopicsUpcoming = useCallback(async () => {
        try {
            const response = await axios.get("/api/subtopics/upcoming");
            console.log(response);
            setSubtopicsUpcoming(response.data);
        } catch (error) {
            console.error(error);
        }
    }, []);

    return {getAllSubtopicsUpcoming, subtopicsUpcoming};
}
