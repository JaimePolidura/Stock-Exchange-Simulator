import React, {Component, useState} from "react";
import '../../index.css';
import './Login.css';
import { useForm } from 'react-hook-form';
import redirectService from "../../services/RedirectService";
import backendService from "../../services/BackendService";

const Login = () => {
    const {register, handleSubmit} = useForm();

    const [token, setToken] = useState();
    const [username, setUsername] = useState();

    const onSubmit = (request) => {
        backendService.login(request)
            .then(response => onSuccess(request.username, response.data))
            .catch(error => onFailure(error));
    }

    const onSuccess = (username, response) => {
        setToken(response.token);
        setUsername(username);

        redirectService.redirect("/profile");
    }

    const onFailure = error => {
        //TODO reset form, error message
    }

    return (
        <div class="myContainer div-config-dif-background">
            <h2 class="title">Login</h2>
            <br/>
            <form class="form-body" onSubmit={handleSubmit(onSubmit)}>
                <input type="text"
                       name="username"
                       placeholder="username"
                       class="form-control"
                       {...register('username', { required: true })}
                />
                <br/>
                <input type="password"
                       name="password"
                       placeholder="password"
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
