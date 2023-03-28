import React from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

export default function LoginPage() {
    const [username, setUsername] = React.useState<string>("");
    const [password, setPassword] = React.useState<string>("");
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
            .catch((err) => {
                alert(err.response.data.error);
            });
    };

    return (
        <div style={{padding: "5rem 0"}}>
            <h1>Sign In</h1>
            <form onSubmit={e => {
                handleSubmit(e)
            }}>
                <div>
                    <label>
                        Username
                        <input
                            type="text"
                            value={username}
                            onChange={e => setUsername(e.currentTarget.value)}
                        />
                    </label>
                </div>

                <div>
                    <label>
                        Password
                        <input
                            type="password"
                            value={password}
                            onChange={e => setPassword(e.currentTarget.value)}
                        />
                    </label>
                </div>
                <button type="submit">Sign In</button>
            </form>
        </div>
    );
}