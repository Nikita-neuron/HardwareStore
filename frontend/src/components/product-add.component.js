import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import ProductsService from "../services/products.service";
import CategoryService from "../services/category.service";

const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                Это поле обязательно к заполнению!
            </div>
        );
    }
};

export default class ProductAdd extends Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.onChangeTitle = this.onChangeTitle.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this);
        this.onChangeCategories = this.onChangeCategories.bind(this);

        this.state = {
            title: "",
            price: "",
            categories: [],
            categories_all: [],
            successful: false,
            message: ""
        };
    }

    componentDidMount() {
        CategoryService.getAll().then(response => this.setState({ categories_all: response.data }));
    }

    onChangeTitle(e) {
        this.setState({
            title: e.target.value
        });
    }

    onChangePrice(e) {
        this.setState({
            price: e.target.value
        });
    }

    onChangeCategories(category) {
        let categories = this.state.categories;
        let index = categories.indexOf(category);
        if (index === -1) categories.push(category);
        else categories.splice(index, 1);
        this.setState({
            categories: categories
        });
    }

    handleSave(e) {
        e.preventDefault();

        this.setState({
            message: "",
            successful: false
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            ProductsService.save(this.state.title, this.state.price, this.state.categories)
            .then(response => {
                console.log(response);
                window.location.replace("/admin");
            }, error => {
                const resMessage = error.response.data;
                this.setState({
                    successful: false,
                    message: resMessage
                });
            });
        }
    }

    render() {
        return (
            <div className="col-md-12">
                <h2>Добавить продукт</h2>
                <div className="card card-container">
                    <Form
                        onSubmit={this.handleSave}
                        ref={c => {
                            this.form = c;
                        }}
                    >
                        {!this.state.successful && (
                            <div>
                                <div className="form-group">
                                    <label>Название</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="title"
                                        value={this.state.title}
                                        onChange={this.onChangeTitle}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label>Цена</label>
                                    <Input
                                        type="number"
                                        className="form-control"
                                        name="price"
                                        value={this.state.price}
                                        onChange={this.onChangePrice}
                                        validations={[required]}
                                    />
                                </div>
                                <div className="form-group">
                                    <label>Категории</label>
                                    {this.state.categories_all.map((category, index) => {return (
                                        <div key={index}>
                                            <input type="checkbox" onChange={e => this.onChangeCategories(category.title)}/>
                                            {category.title}
                                        </div>
                                    )})}
                                </div>
                                <div className="form-group">
                                    <button className="btn btn-primary btn-block">Добавить</button>
                                </div>
                            </div>
                        )}

                        {this.state.message && (
                            <div className="form-group">
                                <div
                                    className={
                                        this.state.successful
                                        ? "alert alert-success"
                                        : "alert alert-danger"
                                    }
                                    role="alert"
                                >
                                    {this.state.message}
                                </div>
                            </div>
                        )}
                        <CheckButton
                            style={{ display: "none" }}
                            ref={c => {
                                this.checkBtn = c;
                            }}
                        />
                    </Form>
                </div>
            </div>
        );
    }
}