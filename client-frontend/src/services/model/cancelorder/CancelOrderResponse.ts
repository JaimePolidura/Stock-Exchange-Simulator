export interface CancelOrderResponse {
    orderId: string;
    clientId: string;
    date: string;
    state: string;
    ticker: string;
    orderIdToCancel: string;
}