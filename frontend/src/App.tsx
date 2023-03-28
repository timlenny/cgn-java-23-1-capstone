import './App.css';
import HomePage from './component/./HomePage'
import {Route, Routes} from "react-router-dom";
import AddTopicPage from "./component/AddTopicPage";
import SignUpPage from "./component/SignUpPage"
import axios from "axios";
import Cookies from "js-cookie";
import LoginPage from "./component/LoginPage";
import useAuthRedirect from "./hook/UseAuthRedirect";


function App() {
    useAuthRedirect();

    axios.interceptors.request.use(function (config) {
        return fetch("/api/csrf").then(() => {
            config.headers["X-XSRF-TOKEN"] = Cookies.get("XSRF-TOKEN");
            return config;
        });
    }, function (error) {
        return Promise.reject(error);
    });

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<HomePage/>}></Route>
                <Route path="/signup" element={<SignUpPage/>}></Route>
                <Route path="/login" element={<LoginPage/>}></Route>
                <Route path="/topic/add" element={<AddTopicPage/>}></Route>
            </Routes>
        </div>
    );
}

export default App;
