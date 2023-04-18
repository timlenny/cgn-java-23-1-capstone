import {useCallback, useState} from "react";
import axios from "axios";

export default function UseGetStreak() {
    const [streak, setStreak] = useState([false, false, false, false, false, false, false]);

    const getStreak = useCallback(async () => {
        try {
            const response = await axios.get("/api/stats");
            setStreak(response.data)
        } catch (error) {
            console.error(error);
        }
    }, []);

    return {getStreak, streak};
}