import React from "react";
import './Options.css';

export default class Options extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="header">
                <div className="header-utils">
                    <div className="header-utils-trade">
                        <button className="btn btn-primary">Trade</button>
                    </div>
                    <div className="header-utils-utils">
                        <button className="btn btn-light">Deposit</button>
                        <button className="btn btn-light">Withdraw</button>
                        <button className="btn btn-light">Settings</button>
                    </div>
                </div>
                <div className="header-logout">
                    <button className="btn btn-danger" onClick={() => this.logout()}>Logout</button>
                </div>
            </div>
        );
    }
}
