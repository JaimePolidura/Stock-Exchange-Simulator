import React, {Component, useState} from "react";
import '../../index.css';
import './Login.css';
import { useForm } from 'react-hook-form';
import auth from "../../services/AuthenticationService";

const Login = (props: any) => {
    const {register, handleSubmit, reset} = useForm();

    const onSubmit = (request: any) => {
        auth.login(request, (response: any) => onSuccess(response, request.username), (error: any) => onFailure(error));
    }

    const onSuccess = (response: any, username: any) => {
        auth.setToken(response.token);
        auth.setAuthenticated(true);
        auth.setUsername(username);

        props.history.push("/profile");
    }

    const onFailure = (error: any) => {
        window.alert("User not found");
        reset();
    }

    // @ts-ignore
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
