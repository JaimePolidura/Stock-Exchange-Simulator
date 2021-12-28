class TradesData {
    constructor() {
        this.trades = [];
    }

    setTrades(trades){
        this.trades = trades;
    }

    getTrades(){
        return this.trades;
    }
}

export default new TradesData();