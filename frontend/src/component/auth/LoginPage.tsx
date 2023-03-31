import React from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import '../../style/auth/AuthPageStyle.css'
import DotAnimation from "../../style/home/DotAnimation";
import {Alert} from "@mui/material";

export default function LoginPage() {
    const [username, setUsername] = React.useState<string>("");
    const [password, setPassword] = React.useState<string>("");
    const [error, setError] = React.useState<string>("");
    const navigate = useNavigate();

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const authHeader = window.btoa(`${username}:${password}`);
        axios
            .post(
                "/api/users/login",
                {},
                {
                    headers: {
                        Authorization: `Basic ${authHeader}`,
                    },
                }
            )
            .then(() => {
                const redirect = window.sessionStorage.getItem("signInRedirect") || "/";
                window.sessionStorage.removeItem("signInRedirect");
                navigate(redirect);
            })
            .catch(() => {
                setError("Incorrect username and / or password")
            });
    };

    function ifErrordisplayError() {
        if (error !== "") {
            return (
                <Alert severity="warning">{error}</Alert>
            )
        }
    }

    return (
        <div className="auth-page-body">
            <DotAnimation></DotAnimation>
            <div className="login-card">
                {ifErrordisplayError()}
                <div className="login-card-content">
                    <div className="header">
                        <div className="logo">
                            <div>EDUFYLY</div>
                        </div>
                        <h3 className={"auth-info"}>LOGIN</h3>
                    </div>
                    <div className="form">
                        <div className="form-field username">
                            <div className="icon">
                                <i className="far fa-user"></i>
                            </div>
                            <input type="text" placeholder="Username"
                                   value={username} onChange={user => setUsername(user.currentTarget.value)}/>
                        </div>
                        <div className="form-field password">
                            <div className="icon">
                                <i className="fas fa-lock"></i>
                            </div>
                            <input type="password" placeholder="Password"
                                   value={password} onChange={password => setPassword(password.currentTarget.value)}/>
                        </div>
                        <button type="submit" onClick={handleSubmit}>Login</button>
                        <div>
                            Don't have an account? <text style={{fontWeight: "bold"}} onClick={() => {
                            navigate("/signup")
                        }}>Sign Up Now</text>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}