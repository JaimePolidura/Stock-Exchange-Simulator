import './App.css';
import NavbarMain from "./components/navbar/Navbar-main";
import Login from "./components/login/Login";
import Profile from "./components/profile/Profile";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import {ProtectedRoute} from "./components/protected.route";
import React from "react";

function App (props: any) {
  return (
      <Router>
          <div className="App">
            <NavbarMain/>
            <Switch>
                <ProtectedRoute exact path="/profile" component={Profile}/>
                <Route path="/" component={Login}/>
            </Switch>
        </div>
      </Router>
  );
}

export default App;
