import {Position} from "./Position";

export interface ClosedPosition extends Position{
    closingPirce: number;
    closingDate: string;
}