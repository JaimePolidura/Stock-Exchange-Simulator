import React from "react";
import './TradeDisplayInTable.css';
import {Modal} from "react-bootstrap";
import SellStockModal from "./SellStockModal";

class TradeDisplayInTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            trade: props.value,
            showModal: false,
            sellExecutionType: "Market",
        };
    }

    render() {
        return (
            <>
                <tr className="trade-tr" onClick={() => {this.openModal();}}>
                    <td>{this.state.trade.ticker}</td>
                    <td>{this.state.trade.name}</td>
                    <td>{this.state.trade.averagePrice} {this.state.trade.currency.symbol}</td>
                    <td>{this.state.trade.actualPrice} {this.state.trade.currency.symbol}</td>
                    <td>{this.state.trade.quantity}</td>
                    <td>{this.renderMarketValue()}</td>
                    <td>{this.renderResult()}</td>
                    <td>{this.renderResultYield()}</td>
                </tr>
                {this.renderModal()}
            </>
        );
    }

    renderModal(){
        return (
            <SellStockModal showModal={this.state.showModal}
                            onHide={() => this.closeModal()}
                            trade={this.state.trade}
                            renderMarketValue = {() => this.renderMarketValue()}/>
        );
    }

    openModal(){
        this.setState({showModal: true});
    }

    closeModal(){
        this.setState({showModal: false});
    }

    renderResult(){
        let result = this.calculateResult();

        return result >= 0 ?
            <div class="green">+ {result} {this.state.trade.currency.symbol}</div> :
            <div class="red">{result} {this.state.trade.currency.symbol}</div>;
    }

    renderMarketValue(){
        let trade = this.state.trade;

        return Math.round(trade.quantity * trade.actualPrice) + " " + trade.currency.symbol;
    }

    renderResultYield(){
        let trade = this.state.trade;

        let investedCapital = trade.quantity * trade.averagePrice;
        let actualCapital = trade.quantity * trade.actualPrice;

        let resultYield = Math.round(((actualCapital / investedCapital) - 1) * 100);

        return resultYield >= 0 ?
            <div class="green">+ {resultYield} %</div> :
            <div class="red">{resultYield} %</div>;
    }

    calculateResult(){
        let trade = this.state.trade;

        return Math.round((trade.actualPrice - trade.averagePrice) * trade.quantity);
    }
}

export default TradeDisplayInTable;
