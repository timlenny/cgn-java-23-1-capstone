import axios from "axios";
import {useState} from "react";

export default function UseDeleteTopic() {
    const [deleteStatus, setDeleteStatus] = useState("");
    function deleteSingleTopic(id: string | undefined) {
        if (id) {
            axios.delete("/api/topic/" + id)
                .then((data) => {
                    setDeleteStatus(data.data)
                })
                .catch((error) => {
                    setDeleteStatus(error.response.data)
                })
        }
    }
    return {deleteSingleTopic, deleteStatus}
}