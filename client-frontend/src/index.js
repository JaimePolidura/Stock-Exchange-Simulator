import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import axios from "axios";
import auth from "./services/AuthenticationService";

const bypassurls = ["login"];

axios.interceptors.request.use(request => {
    for(let i = 0; i < bypassurls.length; i++)
        if(request.url.includes(bypassurls[i]))
            return request;

    let requestHeaders = request.headers;
    let requestJWTHeader = {
        'Authorization': `Bearer ${auth.getToken()}`,
    }

    request.headers = {
        requestHeaders,
        ...requestJWTHeader,
    };

    return request;
});

ReactDOM.render(
    <App />,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
