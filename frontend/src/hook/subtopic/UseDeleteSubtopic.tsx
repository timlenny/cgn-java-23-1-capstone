import axios from "axios";

export default function UseDeleteSubtopic() {
    function deleteSubtopic(id: string | undefined) {
        if (id) {
            axios.delete("/api/subtopics/" + id)
                .catch((error) => {
                    console.log(error)
                })
        }
    }

    return {deleteSubtopic}
}