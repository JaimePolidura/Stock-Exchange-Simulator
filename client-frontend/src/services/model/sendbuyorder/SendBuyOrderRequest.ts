export interface SendBuyOrderRequest {
    ticker: string;
    quantity: number;
    executionPrice: number;
}