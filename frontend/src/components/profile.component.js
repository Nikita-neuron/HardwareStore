import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../services/auth.service";

export default class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            redirect: null,
            userReady: false,
            currentUser: { email: "" }
        };
    }

    componentDidMount() {
        const currentUser = AuthService.getCurrentUser();

        if (!currentUser) this.setState({ redirect: "/" });
        this.setState({ currentUser: currentUser, userReady: true })
        if (currentUser && currentUser.role == "ADMIN") this.setState({ redirect: "/admin" });
    }

    render() {
        if (this.state.redirect) {
            return <Navigate to={this.state.redirect} />
        }

        const { currentUser } = this.state;

        return (
            <div className="container">
                {(this.state.userReady) ?
                <div>
                    <header className="jumbotron">
                        <h3>
                            <strong>{currentUser.firstName}</strong> Профиль
                        </h3>
                    </header>
                    <p>
                        <strong>Token:</strong>{" "}
                        {currentUser.token}
                    </p>
                    <p>
                        <strong>Email:</strong>{" "}
                        {currentUser.email}
                    </p>
                </div>: null}
            </div>
        );
    }
}