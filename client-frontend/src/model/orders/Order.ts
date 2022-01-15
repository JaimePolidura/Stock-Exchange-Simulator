export interface Order {
    orderId: string;
    clientId: string;
    date: string;
    pendingOrderType: PendingOrderType;
    state: OrderState;
    ticker: string;
}

export enum OrderState {
    PENDING,
    CANCELLED,
    ERROR,
    EXECUTED,
}

export enum PendingOrderType {
    BUY,
    SELL,
    CANCEL,
}