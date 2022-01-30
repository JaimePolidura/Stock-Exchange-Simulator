import {Position} from "../../../../../model/positions/Position";

export interface PositionsAtDatePDFState {
    positions: Position[],
    date: Date,
}