import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import DotAnimation from "../../style/home/DotAnimation";
import {Alert} from "@mui/material";

export default function SignUpPage() {
    const [username, setUsername] = React.useState<string>("");
    const [password, setPassword] = React.useState<string>("");
    const [error, setError] = React.useState<string>("");
    const navigate = useNavigate();

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        axios.post("/api/users", {
            username,
            password
        }).then(() => {
            navigate("/login");
        }).catch(err => {
            if (err.message.includes("409")) {
                setError("Username already exists")
            } else if (err.message.includes("400")) {
                setError("Username must have at least 3 characters. Password must have at least 7 characters.")
            } else {
                setError(err.response.data)
            }
            console.log(err)
        });
    };

    function ifErrordisplayError() {
        if (error !== "") {
            return (
                <Alert severity="warning">{error}</Alert>
            )
        }
    }

    const [setIsPaused, setSetIsPaused] = useState<(value: boolean) => void>(() => () => {
    });

    useEffect(() => {
        return () => {
            if (setIsPaused) {
                setIsPaused(true);
            }
        };
    }, [setIsPaused]);

    return (
        <div className="auth-page-body">
            <DotAnimation setPause={setSetIsPaused}></DotAnimation>
            <div className="login-card">
                {ifErrordisplayError()}
                <div className="login-card-content">
                    <div className="header">
                        <div className="logo">
                            <div>EDUFYLY</div>
                        </div>
                        <h3 className={"auth-info"}>SIGN UP</h3>
                    </div>
                    <div className="form">
                        <div className="form-field username">
                            <div className="icon">
                                <i className="far fa-user"></i>
                            </div>
                            <input type="text" placeholder="Username"
                                   value={username} onChange={username => setUsername(username.currentTarget.value)}/>
                        </div>
                        <div className="form-field password">
                            <div className="icon">
                                <i className="fas fa-lock"></i>
                            </div>
                            <input type="password" placeholder="Password"
                                   value={password} onChange={pw => setPassword(pw.currentTarget.value)}/>
                        </div>
                        <button className={"button-login-page"} type="submit" onClick={handleSubmit}>SIGN UP NOW
                        </button>
                        <div>
                            Already have an account? <text style={{fontWeight: "bold"}} onClick={() => {
                            navigate("/login")
                        }}>Login Now</text>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}