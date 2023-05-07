import React, { Component } from "react";
import { BrowserRouter, Route, Routes, Link } from "react-router-dom";

import AuthService from "./services/auth.service";

import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import Profile from "./components/profile.component";

import AdminPanel from "./components/admin-panel.component";
import ProductAdd from "./components/product-add.component";
import CategoryAdd from "./components/category-add.component";
import Basket from "./components/basket.component";

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            currentUser: undefined,
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if (user) {
            this.setState({
                currentUser: user
            });
        }
    }

    logOut() {
        AuthService.logout();
        this.setState({
            currentUser: undefined,
        });
    }

    render() {
        const { currentUser } = this.state;

        return (
            <BrowserRouter>
                <div>
                    <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <Link to={"/"} className="navbar-brand">Home</Link>
                        {currentUser ? (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to={"/user"} className="nav-link">{currentUser.email}</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/login" className="nav-link" onClick={this.logOut}>Выйти</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/basket" className="nav-link">Корзина</Link>
                                </li>
                            </div>
                        ) : (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to={"/login"} className="nav-link">Войти</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to={"/register"} className="nav-link">Зарегистрироваться</Link>
                                </li>
                            </div>
                        )}
                    </nav>

                    <div className="container mt-3">
                        <Routes>
                            <Route path="/" element={<Home />} />
                            <Route path="/login" element={<Login />} />
                            <Route path="/register" element={<Register />} />
                            <Route path="/user" element={<Profile />} />
                            <Route path="/basket" element={<Basket />} />
                            <Route path="/admin/" element={<AdminPanel />} />
                            <Route path="/admin/addProduct" element={<ProductAdd />} />
                            <Route path="/admin/addCategory" element={<CategoryAdd />} />
                        </Routes>
                    </div>
                </div>
            </BrowserRouter>
        );
    }
}

export default App;