import React, {Component, useState} from "react";
import '../../index.css';
import './Login.css';
import { useForm } from 'react-hook-form';
import auth from "../../services/AuthenticationService";

const Login = props => {
    const {register, handleSubmit} = useForm();

    const onSubmit = (request) => {
        auth.login(request, response => onSuccess(response, request.username), error => onFailure(error));
    }

    const onSuccess = (response, username) => {
        auth.setToken(response.token);
        auth.setAuthenticated(true);
        auth.setUsername(username);

        props.history.push("/profile");
    }

    const onFailure = error => {
        window.alert("User not found");
        document.getElementById("login-form").reset();
    }

    return (
        <div class="myContainer div-config-dif-background">
            <h2 class="title">Login</h2>
            <br/>
            <form class="form-body" onSubmit={handleSubmit(onSubmit)} id = "login-form">
                <input type="text"
                       name="username"
                       placeholder="Username"
                       class="form-control"
                       {...register('username', { required: true })}
                />
                <br/>
                <input type="password"
                       name="password"
                       placeholder="Password"
                       class="form-control"
                       {...register('password', { required: true })}
                />
                <hr />

                <div class="footer">
                    <input type="submit" class="myButton" value="login" />
                </div>

            </form>
        </div>
    );
}

export default Login;
