export interface SendSellOrderRequest {
    positionId: string;
    quantity: number;
    priceToExecute: number;
}