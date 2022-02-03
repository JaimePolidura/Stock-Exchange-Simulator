import backend from "./api/BackendService";

class LastPricesService {
    private readonly lastPrices: {[ticker: string]: number};
    private readonly pendingRequest: {[ticker: string]: Promise<any>};

    constructor() {
        this.lastPrices = {};
        this.pendingRequest = {};
    }

    isLoaded(ticker: string): boolean {
        return this.lastPrices[ticker] != undefined;
    }

    getLastPriceNoSafe(ticker: string): number {
        return this.lastPrices[ticker];
    }

    async getLastPrice(ticker: string): Promise<any> {
        if(this.lastPrices[ticker] != undefined)
            return new Promise((() => this.lastPrices[ticker]));

        if(this.pendingRequest[ticker] != undefined)
            return this.pendingRequest[ticker];

        let promise: Promise<any> = backend.getLastPrice(ticker);

        this.pendingRequest[ticker] = promise;

        return await promise.then(res => {
            this.lastPrices[ticker] = res.data;
            delete this.pendingRequest[ticker];

            return res.data;
        });
    }
}

export default new LastPricesService();