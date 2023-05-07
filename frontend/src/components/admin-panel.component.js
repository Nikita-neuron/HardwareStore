import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import { Link } from "react-router-dom";

import AuthService from "../services/auth.service";
import AdminService from "../services/admin.service";
import ProductsService from "../services/products.service";
import CategoryService from "../services/category.service";

export default class AdminPanel extends Component {
    constructor(props) {
        super(props);

        this.state = {
            message: null,
            redirect: null,
            userReady: false,
            currentUser: {},
            users: [],
            products: [],
            categories: []
        };
    }

    componentDidMount() {
        const currentUser = AuthService.getCurrentUser();

        if (!currentUser || currentUser.role != "ADMIN") this.setState({ redirect: "/" });
        this.setState({ 
            currentUser: currentUser, 
            userReady: true
        })

        AdminService.getUsers().then(response => this.setState({ users: response.data }));
        ProductsService.getAll().then(response => this.setState({ products: response.data }));
        CategoryService.getAll().then(response => this.setState({ categories: response.data }));
    }

    onUserDelete(event, id) {
        AdminService.deleteUserById(id).then(response => {
            console.log(response);
            window.location.reload();
        }, error => {
            const resMessage =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                    error.message ||
                    error.toString();

                this.setState({ message: resMessage });
        });
    }

    onProductDelete(event, id) {
        ProductsService.deleteById(id).then(response => {
            console.log(response);
            window.location.reload();
        }, error => {
            const resMessage =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                    error.message ||
                    error.toString();

                this.setState({ message: resMessage });
        });
    }

    onCategoryDelete(event, id) {
        CategoryService.deleteById(id).then(response => {
            console.log(response);
            window.location.reload();
        }, error => {
            const resMessage =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                    error.message ||
                    error.toString();

                this.setState({ message: resMessage });
        });
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
                {this.state.message && (
                    <div className="form-group">
                        <div className="alert alert-danger" role="alert">
                                {this.state.message}
                        </div>
                    </div>
                )}

                <h2>Пользователи:</h2>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">firstName</th>
                            <th scope="col">lastName</th>
                            <th scope="col">birthday</th>
                            <th scope="col">phone</th>
                            <th scope="col">email</th>
                            <th scope="col">status</th>
                            <th scope="col">role</th>
                            <th scope="col">action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.users.map((user, index) => { return(
                                <tr key={index}>
                                    <th>{user.id}</th>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>{user.birthday}</td>
                                    <td>{user.phone}</td>
                                    <td>{user.email}</td>
                                    <td>{user.status}</td>
                                    <td>{user.role}</td>
                                    <td>{(user.role != "ADMIN" && 
                                        <Link to="/admin" className="btn btn-danger" onClick={ event => this.onUserDelete(event, user.id)}>Удалить</Link>
                                    )}</td>
                                </tr>
                            )})
                        }
                    </tbody>
                </table>

                <h2>Продукты:</h2>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">title</th>
                            <th scope="col">price</th>
                            <th scope="col">categories</th>
                            <th scope="col">action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.products.map((product, index) => { return(
                                <tr key={index}>
                                    <th>{product.id}</th>
                                    <td>{product.title}</td>
                                    <td>{product.price}₽</td>
                                    <td>{product.categories.map((category, index) => {return <span key={index}>{(index != 0 && <br/>)}{category.title}</span>})}</td>
                                    <td>
                                        <Link to="/admin" className="btn btn-danger" onClick={ event => this.onProductDelete(event, product.id)}>Удалить</Link>
                                    </td>
                                </tr>
                            )})
                        }
                    </tbody>
                </table>
                <Link to="/admin/addProduct" className="btn btn-primary">Добавить</Link>
                <br/>
                <br/>
                <h2>Категории:</h2>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">title</th>
                            <th scope="col">action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.categories.map((category, index) => { return(
                                <tr key={index}>
                                    <th>{category.id}</th>
                                    <td>{category.title}</td>
                                    <td>
                                        <Link to="/admin" className="btn btn-danger" onClick={ event => this.onCategoryDelete(event, category.id)}>Удалить</Link>
                                    </td>
                                </tr>
                            )})
                        }
                    </tbody>
                </table>
                <Link to="/admin/addCategory" className="btn btn-primary">Добавить</Link>
            </div>
        );
    }
}