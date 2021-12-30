import React from "react";
import './TradeDisplayInTable.css';
import SellStockModal from "./SellStockModal";
import listedCompaniesData from "../../../data/ListedCompaniesData";
import {set} from "react-hook-form";

class TradeDisplayInTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            trade: props.value,
            showModal: false,
            sellExecutionType: "Market",
            onOrderSellSended: props.onOrderSellSended,
        };
    }

    render() {
        return (
            <>
                <tr className="trade-tr" onClick={() => {this.openModal();}}>
                    <td>{this.state.trade.ticker}</td>
                    <td>{this.nameFromTicker(this.state.ticker)}</td>
                    <td>{this.state.trade.openingPrice} $</td>
                    <td>{this.state.trade.openingDate}</td>
                    <td>{this.actualPriceFromTicker(this.state.trade.ticker)} $</td>
                    <td>{this.state.trade.quantity}</td>
                    <td>{this.renderMarketValue()}</td>
                    <td>{this.renderResult()}</td>
                    <td>{this.renderResultYield()}</td>
                </tr>
                {this.renderSellStockModal()}
            </>
        );
    }
    
    //TODO load data asynch
    nameFromTicker(ticker){
        return "Loading...";
    }

    actualPriceFromTicker(ticker){
        return 1000;
    }

    renderSellStockModal(){
        return (
            <SellStockModal showModal={this.state.showModal}
                            onHide={() => this.closeModal()}
                            trade={this.state.trade}
                            renderMarketValue = {() => this.renderMarketValue()}
                            onOrderSellSended = {order=> this.onOrderSellSended(order)}
            />
        );
    }

    onOrderSellSended(order){
        this.setState({showModal: false});

        this.state.onOrderSellSended(order);
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
            <div class="green">+ {result} $</div> :
            <div class="red">{result} $</div>;
    }

    renderMarketValue(){
        let trade = this.state.trade;

        return Math.round(trade.quantity * this.actualPriceFromTicker(trade.ticker)) + " $";
    }

    renderResultYield(){
        let trade = this.state.trade;

        let investedCapital = trade.quantity * trade.openingPrice;
        let actualCapital = trade.quantity * this.actualPriceFromTicker(trade.ticker);

        let resultYield = Math.round(((actualCapital / investedCapital) - 1) * 100);

        return resultYield >= 0 ?
            <div class="green">+ {resultYield} %</div> :
            <div class="red">{resultYield} %</div>;
    }

    calculateResult(){
        let trade = this.state.trade;

        return Math.round((this.actualPriceFromTicker(trade.ticker) - trade.openingPrice) * trade.quantity);
    }
}

export default TradeDisplayInTable;
