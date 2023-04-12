import './App.css';
import HomePage from './component/home/HomePage'
import {Route, Routes} from "react-router-dom";
import AddTopicPage from "./component/home/AddTopicPage";
import SignUpPage from "./component/auth/SignUpPage"
import axios from "axios";
import Cookies from "js-cookie";
import LoginPage from "./component/auth/LoginPage";
import SubtopicPage from "./component/subtopic/SubtopicPage";
import AddTaskPage from "./component/task/AddTaskPage";
import TasksPage from "./component/task/TaskPage";
import AddSubtopicPage from "./component/subtopic/AddSubtopicPage";

axios.interceptors.request.use(function (config) {
    return fetch("/api/csrf").then(() => {
        config.headers["X-XSRF-TOKEN"] = Cookies.get("XSRF-TOKEN");
        return config;
    });
}, function (error) {
    return Promise.reject(error);
});

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<HomePage/>}></Route>
                <Route path="/signup" element={<SignUpPage/>}></Route>
                <Route path="/login" element={<LoginPage/>}></Route>
                <Route path="/topic/add" element={<AddTopicPage/>}></Route>
                <Route path="/subtopic/:id" element={<SubtopicPage/>}></Route>
                <Route path="/subtopic/add/:id/:length" element={<AddSubtopicPage/>}></Route>
                <Route path="/tasks/:topicId/:id" element={<TasksPage/>}></Route>
                <Route path="/tasks/add/:topicId/:id" element={<AddTaskPage/>}></Route>
            </Routes>
        </div>
    );
}

export default App;
