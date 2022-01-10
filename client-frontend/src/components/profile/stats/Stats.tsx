import React, {useEffect, useState} from "react";

const Stats = (props: { cash: { cash: boolean | React.ReactChild | React.ReactFragment | React.ReactPortal | null | undefined; }; }) => {
    return (
        <h5>Cash: {props.cash.cash} $</h5>
    );
}

export default Stats;
