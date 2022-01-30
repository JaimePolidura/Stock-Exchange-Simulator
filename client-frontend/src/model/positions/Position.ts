import {ExecutedOrderType} from "./ExecutedOrderType";

export interface Position {
    orderId: string;
    executedOrderType: ExecutedOrderType;
    ticker: string;
    openingDate: string;
    openingPrice: string;
    quantity: number;
}