import React, {Component} from "react";
import '../../index.css';
import './Login.css';
import { useForm } from 'react-hook-form';

const Login = () => {
    const {register, handleSubmit} = useForm();

    const onSubmit = (data) => {
        console.log(data);
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
