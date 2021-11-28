import React from "react";

export default class Stats extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            cash: props.cash,
        }
    }

    render() {
        return (
            <h5>Cash: {this.state.cash.cash} {this.state.cash.currency.symbol}</h5>
        );
    }
}
