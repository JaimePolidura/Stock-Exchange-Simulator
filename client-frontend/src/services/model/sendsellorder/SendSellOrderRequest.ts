export interface SendSellOrderRequest {
    positionId: string;
    quantity: number;
    executionPrice: number;
}