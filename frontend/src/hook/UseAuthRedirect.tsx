import axios from 'axios';

const useAuthRedirect = () => {
    if (window.location.pathname !== "/login") {
        axios.interceptors.response.use(function (response) {
            return response;
        }, function (error) {
            if (error.response.status === 401) {
                window.location.href = "/login";
            }
            return Promise.reject(error);
        });
    }
};

export default useAuthRedirect;
