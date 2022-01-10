import React, {ReactComponentElement} from "react";
import './TradeDisplayInTable.css';
import SellStockModal from "./SellStockModal";

class TradeDisplayInTable extends React.Component<any, any> {
    constructor(props: any) {
        super(props);

        this.state = {
            trade: props.value,
            showModal: false,
            sellExecutionType: "Market",
            onOrderSellSended: props.onOrderSellSended,
            listedCompany: props.listedCompany,
        };
    }

    render() {
        return (
            <>
                <tr className="trade-tr" onClick={() => {this.openModal();}}>
                    <td>{this.state.trade.ticker}</td>
                    <td>{this.state.listedCompany.name}</td>
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

    actualPriceFromTicker(ticker: string): number{
        return 1000;
    }

    renderSellStockModal(): any{
        return (
            <SellStockModal showModal={this.state.showModal}
                            onHide={() => this.closeModal()}
                            listedCompany = {this.state.listedCompany}
                            trade={this.state.trade}
                            renderMarketValue = {() => this.renderMarketValue()}
                            onOrderSellSended = {(order: any) => this.onOrderSellSended(order)}
            />
        );
    }

    onOrderSellSended(order: any): void{
        this.setState({showModal: false});

        this.state.onOrderSellSended(order);
    }

    openModal(): void{
        this.setState({showModal: true});
    }

    closeModal(): void{
        this.setState({showModal: false});
    }

    renderResult(): any{
        let result = this.calculateResult();

        return result >= 0 ?
            <div className="green">+ {result} $</div> :
            <div className="red">{result} $</div>;
    }

    renderMarketValue(): string{
        let trade = this.state.trade;

        return Math.round(trade.quantity * this.actualPriceFromTicker(trade.ticker)) + " $";
    }

    renderResultYield(): any{
        let trade = this.state.trade;

        let investedCapital = trade.quantity * trade.openingPrice;
        let actualCapital = trade.quantity * this.actualPriceFromTicker(trade.ticker);

        let resultYield = Math.round(((actualCapital / investedCapital) - 1) * 100);

        return resultYield >= 0 ?
            <div className="green">+ {resultYield} %</div> :
            <div className="red">{resultYield} %</div>;
    }

    calculateResult(): number{
        let trade = this.state.trade;

        return Math.round((this.actualPriceFromTicker(trade.ticker) - trade.openingPrice) * trade.quantity);
    }
}

export default TradeDisplayInTable;
