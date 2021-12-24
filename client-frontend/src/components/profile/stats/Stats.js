import React from "react";

const Stats = props => {
    return (
        <h5>Cash: {props.cash.cash} {props.cash.currency.symbol}</h5>
    );
}

export default Stats;
