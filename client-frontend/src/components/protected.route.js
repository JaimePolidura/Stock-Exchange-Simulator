import React from "react";
import { Route, Redirect } from "react-router-dom";
import auth from "../services/AuthenticationService";

export const ProtectedRoute = ({component: Component, value,...rest}) => {
  return (
    <Route
      {...rest}
      render={props => {
        if (auth.isAuthenticated()) {
          return <Component {...props} value={value}/>;
        } else {
          return (
            <Redirect
              to={{
                pathname: "/",
                state: {
                  from: props.location
                }
              }}
            />
          );
        }
      }}
    />
  );
};
