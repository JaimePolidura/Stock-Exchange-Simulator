import {Order} from "./Order";

export interface ExecutionOrder extends Order{
    quantity: number;
    priceToExecute: number;
}