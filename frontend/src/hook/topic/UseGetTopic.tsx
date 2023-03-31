import {useCallback, useState} from "react";
import axios from "axios";
import {Topic} from "../../model/topic/Topic";

export default function UseGetTopic() {
    const [topic, setTopic] = useState<Topic[]>([]);
    const getAllTopics = useCallback(() => {
        axios
            .get("/api/topic")
            .then((response) => {
                setTopic(response.data);
            })
            .catch((error) => console.error(error));
    }, []);

    return {getAllTopics, topic};
}
