import React from 'react';
import './App.css';
import HomePage from './component/./HomePage'
import {Route, Routes} from "react-router-dom";
import AddTopicPage from "./component/AddTopicPage";

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<HomePage/>}></Route>
                <Route path="/topic/add" element={<AddTopicPage/>}></Route>
            </Routes>
        </div>
    );
}
export default App;
