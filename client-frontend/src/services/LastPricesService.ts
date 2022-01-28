import backend from "./BackendService";

class LastPricesService {
    private lastPrices: {[ticker: string]: number};

    constructor() {
        this.lastPrices = {};
    }

    async getLastPrice(ticker: string): Promise<any> {
        if(ticker in this.lastPrices)
            return new Promise((() => this.lastPrices[ticker]));

        return await backend.getLastPrice(ticker).then(res => {
            this.lastPrices[ticker] = res.data;

            return res.data;
        });
    }
}

export default new LastPricesService();