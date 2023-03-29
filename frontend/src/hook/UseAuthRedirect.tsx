import axios from 'axios';
import {useNavigate} from "react-router-dom";

const useAuthRedirect = () => {
    const navigate = useNavigate();
    if (window.location.pathname !== "/login") {
        axios.interceptors.response.use(function (response) {
            return response;
        }, function (error) {
            if (error.response.status === 401) {
                navigate("/login")
            }
            return Promise.reject(error);
        });
    }
};

export default useAuthRedirect;
