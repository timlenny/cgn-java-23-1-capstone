import {useCallback, useState} from "react";
import axios from "axios";
import {Subtopic} from "../../model/subtopic/Subtopic";

export default function UseGetSubtopicData() {
    const [subtopics, setSubtopics] = useState<Subtopic[]>([]);
    const getAllSubtopics = useCallback((topicId: string) => {
        axios
            .get("/api/subtopics/" + topicId)
            .then((response) => {
                console.log(response)
                setSubtopics(response.data);
            })
            .catch((error) => console.error(error));
    }, []);
    return {getAllSubtopics, subtopics};
}