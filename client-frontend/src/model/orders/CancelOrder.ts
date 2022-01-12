import {Order} from "./Order";

export interface CancelOrder extends Order{
    orderIdToCancel: string;
}