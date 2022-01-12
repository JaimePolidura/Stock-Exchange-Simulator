export interface SendBuyOrderResponse {
    orderId: string;
    clientId: string;
    date: string;
    state: string;
    ticker: string;
    quantity: number;
    executionPrice: number;
}