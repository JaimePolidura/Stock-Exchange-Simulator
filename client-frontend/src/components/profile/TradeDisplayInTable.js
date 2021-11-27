import React from "react";

class TradeDisplayInTable extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <td>{this.props.value.ticker}</td>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.averagePrice}</td>
                <td>{this.props.value.actualPrice}</td>
                <td>{this.props.value.quantity}</td>
            </>
        );
    }
}

export default TradeDisplayInTable;
