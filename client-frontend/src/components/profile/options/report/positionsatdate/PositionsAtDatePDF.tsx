import React from "react";
import {useState} from "react";
import {Position} from "../../../../../model/positions/Position";
import ReactPDF, { Document, Page, Text, View, Image } from "@react-pdf/renderer";
import {DataTableCell, Table, TableBody, TableCell, TableHeader} from "@david.kucsai/react-pdf-table";

export const PositionsAtDatePDF = (props: PositionsAtDatePDFProps) => {
    const [ positions, setPositions ] = useState<Position[]>(props.positions);
    const [ date, setDate ] = useState<Date>(props.date);

    const headerText = {textAlign: 'center'};
    const cellText = {textAlign: 'center'};

    return (
        <Document>
            <Page size="A4" style={{padding: "5%"}}>
                <View>
                    <Text style={{fontSize: "30px"}}><h1>SXS</h1></Text>
                    <Text style={{fontSize: "12px"}}>Open positions at {date.toLocaleDateString()}</Text>
                </View>
                <View style={{marginTop: "10%"}}>
                    <Table data={positions}>
                        <TableHeader>
                            <TableCell style={headerText}>Ticker</TableCell>
                            <TableCell style={headerText}>Quantity</TableCell>
                            <TableCell style={headerText}>Opening price</TableCell>
                            <TableCell style={headerText}>Opening date</TableCell>
                            <TableCell style={headerText}>Status now</TableCell>
                        </TableHeader>

                        <TableBody>
                            <DataTableCell style={cellText} getContent={(r) => r.ticker}/>
                            <DataTableCell style={cellText} getContent={(r) => r.quantity}/>
                            <DataTableCell style={cellText} getContent={(r) => r.openingPrice}/>
                            <DataTableCell style={cellText} getContent={(r) => r.openingDate}/>
                            <DataTableCell style={cellText} getContent={(r) => r.executedOrderType}/>
                        </TableBody>
                    </Table>
                </View>
            </Page>
        </Document>
    );
}

export interface PositionsAtDatePDFProps {
    positions: Position[],
    date: Date,
}