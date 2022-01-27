export interface SendBuyOrderRequest {
    ticker: string;
    quantity: number;
    priceToExecute: number;
}