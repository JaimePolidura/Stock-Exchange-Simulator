export interface Order {
    orderId: string;
    clientId: string;
    date: string;
    type: OrderType;
    state: OrderState;
    ticker: string;
}

export enum OrderState {
    PENDING,
    CANCELLED,
    ERROR,
    EXECUTED,
}

export enum OrderType {
    BUY,
    SELL,
    CANCEL,
}