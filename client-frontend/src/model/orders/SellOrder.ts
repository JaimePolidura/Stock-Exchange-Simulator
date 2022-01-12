import {ExecutionOrder} from "./ExecutionOrder";

export interface SellOrder extends ExecutionOrder{
    positionIdToSell: string;
}