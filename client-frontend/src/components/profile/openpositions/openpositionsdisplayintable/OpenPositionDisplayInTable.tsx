import React from "react";
import './OpenPositionDisplayInTable.css';
import SellStockModal from "./sellstockmodal/SellStockModal";
import {OpenPosition} from "../../../../model/positions/OpenPosition";
import {ListedCompany} from "../../../../model/listedcomapnies/ListedCompany";
import {SellOrder} from "../../../../model/orders/SellOrder";
import lastPricesService from "../../../../services/LastPricesService";

class TradeDisplayInTable extends React.Component<TradeDisplayInTableProps, TradeDisplayInTableState> {
    constructor(props: TradeDisplayInTableProps) {
        super(props);

        this.state = {
            trade: props.value,
            showModal: false,
            sellExecutionType: "Market",
            onOrderSellSended: props.onOrderSellSended,
            listedCompany: props.listedCompany,
            lastPrice: -1,
        };

    }

    componentDidMount() {
        this.loadPriceByApi();
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

    actualPriceFromTicker(ticker: string): any{
        if(lastPricesService.isLoaded(ticker)){
            return lastPricesService.getLastPriceNoSafe(ticker);
        }else{
            this.loadPriceByApi();
            return 'Loading';
        }
    }

    loadPriceByApi() {
        lastPricesService.getLastPrice(this.state.trade.ticker).then(res => {
            this.setState({lastPrice: res});
        });
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

    onOrderSellSended(order: SellOrder): void{
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
        if(this.actualPriceFromTicker(this.state.trade.ticker) == 'Loading') return 'Loading';

        let result = this.calculateResult();

        return result >= 0 ?
            <div className="green">+ {result} $</div> :
            <div className="red">{result} $</div>;
    }

    renderMarketValue(): string{
        if(this.actualPriceFromTicker(this.state.trade.ticker) == 'Loading') return 'Loading';

        let trade = this.state.trade;

        return Math.round(trade.quantity * this.actualPriceFromTicker(trade.ticker)) + " $";
    }

    renderResultYield(): any{
        if(this.actualPriceFromTicker(this.state.trade.ticker) == 'Loading') return 'Loading';

        let trade = this.state.trade;

        let investedCapital = trade.quantity * trade.openingPrice;
        let actualCapital = trade.quantity * this.actualPriceFromTicker(trade.ticker);

        let resultYield = Math.round(((actualCapital / investedCapital) - 1) * 100);

        return resultYield >= 0 ?
            <div className="green">+ {resultYield} %</div> :
            <div className="red">{resultYield} %</div>;
    }

    calculateResult(): string | number{
        let trade: OpenPosition = this.state.trade;

        return Math.round((this.actualPriceFromTicker(trade.ticker) - trade.openingPrice) * trade.quantity);
    }
}

export default TradeDisplayInTable;

export interface TradeDisplayInTableState {
    trade: OpenPosition,
    showModal: boolean,
    sellExecutionType: string,
    lastPrice: number,
    listedCompany: ListedCompany,
    onOrderSellSended: (order: SellOrder) => void,
}

export interface TradeDisplayInTableProps {
    value: OpenPosition,
    onOrderSellSended: (order: SellOrder) => void,
    listedCompany: ListedCompany,
}