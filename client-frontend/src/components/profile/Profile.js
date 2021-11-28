import React from "react";
import './Profile.css';
import auth from "../../services/AuthenticationService";
import backendService from "../../services/BackendService";
import Trades from "./trades/Trades";
import Stats from "./stats/Stats";
import Options from "./options/Options";

class Profile extends React.Component {
    constructor(props) {
        super(props);

        let profileData = backendService.getProfielData();
        this.state = {
            trades: profileData.trades,
            cash: profileData.cash,
        };
    }

    render() {
        return (
            <div class="content div-config-dif-background">
                <Options/>
                <hr/>
                <Stats cash = {this.state.cash}/>
                <br/>
                <Trades trades={this.state.trades}/>
            </div>
        );
    }

    logout(){
        auth.logout();
        this.props.history.push("/");
    }
}


export default Profile;
