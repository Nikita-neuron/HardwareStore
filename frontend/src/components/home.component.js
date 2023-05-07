import React, { Component } from "react";
import { Link } from "react-router-dom";

import ProductsService from "../services/products.service";
import AuthService from "../services/auth.service";

export default class Home extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products: [],
            currentUser: undefined,
            userRole: ""
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();
        if (user) {
            this.setState({
              currentUser: user,
              userRole: user.role
            });
        }
            
        ProductsService.getAll().then(
            response => {
                this.setState({
                    products: response.data
                });
            },
            error => {
                this.setState({
                    content:
                        (error.response && error.response.data) ||
                        error.message ||
                        error.toString()
                });
            }
        );
    }

    render() {
        return (
            <main className="container">
                <h3 className="products-title text-center mb-3">Продукты</h3>
                <div className="products d-flex flex-row justify-content-around flex-wrap">
                    {
                        this.state.products.map((product, index) => { return(
                            <div className="card mb-2" style={{"width": "18rem"}} key={index}>
                                <div className="card-body">
                                    <h5 className="card-title">{product.title}</h5>
                                    <p className="card-text">{product.price}₽</p>
                                    <ul className="list-group list-group-flush">
                                        {
                                            product.categories.map((category, index) => { return (
                                                <li className="list-group-item fst-italic fw-light" key={index}>{category.title}</li>
                                            )})
                                        }
                                    </ul>
                                    {
                                        (this.state.currentUser &&
                                            <Link to="/home" className="btn btn-primary">Добавить в корзину</Link>
                                        )
                                    }
                                </div>
                            </div>
                        )})
                    }
                </div>
            </main>
        );
    }
}