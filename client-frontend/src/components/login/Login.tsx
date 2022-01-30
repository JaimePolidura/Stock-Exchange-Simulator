import React, {Component, useState} from "react";
import '../../index.css';
import './Login.css';
import { useForm } from 'react-hook-form';
import auth from "../../services/AuthenticationService";
import {LoginRequest} from "../../services/api/requests/login/LoginRequest";

const Login = (props: any) => {
    const {register, handleSubmit, reset} = useForm();

    const onSubmit = (request: any) => {
        let requestToApi: LoginRequest = {username: request.username, password: request.password};

        auth.login(requestToApi, () => props.history.push("/profile"), (error: any) => onFailure(error));
    }

    const onFailure = (error: any) => {
        window.alert("User not found");
        reset();
    }

    return (
        <div className="myContainer div-config-dif-background">
            <h2 className="title">Login</h2>
            <br/>
            <form className="form-body" onSubmit={handleSubmit(onSubmit)} id = "login-form">
                <input type="text"
                       placeholder="Username"
                       className="form-control"
                       {...register('username', { required: true })}
                />
                <br/>
                <input type="password"
                       placeholder="Password"
                       className="form-control"
                       {...register('password', { required: true })}
                />
                <hr />

                <div className="footer">
                    <input type="submit" className="myButton" value="login" />
                </div>

            </form>
        </div>
    );
}

export default Login;
