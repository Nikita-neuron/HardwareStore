import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

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

export default class CategoryAdd extends Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.onChangeTitle = this.onChangeTitle.bind(this);

        this.state = {
            title: "",
            successful: false,
            message: ""
        };
    }

    onChangeTitle(e) {
        this.setState({
            title: e.target.value
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
            CategoryService.save(this.state.title)
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
                <h2>Добавить Категорию</h2>
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