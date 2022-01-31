import React, {useState} from "react";
import DatePicker from "react-datepicker";
import backend from "../../../../../services/api/BackendService";
import {Position} from "../../../../../model/positions/Position";
import { PDFDownloadLink, PDFViewer } from "@react-pdf/renderer";
import {PositionsAtDatePDF} from "./PositionsAtDatePDF";
import "../Report.css";

const OpenPositionsAtDateForm = (props: any) => {
    const [ dateOfGetOpenPositionsAtDate, setDateOfGetOpenPositionsAtDate ] = useState<Date>(new Date());

    const [ positions, setPositions ] = useState<Position[]>([]);
    const [ readyToDownloadPDF, setReadyToDownloadPDF ] = useState<boolean>(false);

    const onSubmit = () => {
        let normalizedDate = normalizeDate(dateOfGetOpenPositionsAtDate);

        backend.getOpenPositionsAtDate(normalizedDate).then(res => {
            setPositions(res.data.positions);
            setReadyToDownloadPDF(true);
        });
    };

    const normalizeDate = (date: Date): string => {
        let correctMonth: number = date.getMonth() + 1;

        let year: number = date.getFullYear();
        let month: string = (date.getMonth() + 1) < 10 ? "0" + correctMonth.toString() : correctMonth.toString();
        let day: string = date.getDate() < 10 ? "0" + date.getDate().toString() : date.getDate().toString();

        return `${year}-${month}-${day} 00:00`;
    }

    const onDateSelected = (date: Date | null) => {
        // @ts-ignore
        setDateOfGetOpenPositionsAtDate(date);
        setReadyToDownloadPDF(false);
    }

    return (
        <>
            <DatePicker className="date-picker" selected={dateOfGetOpenPositionsAtDate} onChange={(date) => onDateSelected(date)} />
            <br />
            <br />

            <div className="footer-report-form">
                {!readyToDownloadPDF &&
                    <button className="btn btn-primary footer-btn" onClick={() => onSubmit()}>✓ Generate ✓</button>
                }
                {readyToDownloadPDF &&
                    <PDFDownloadLink document={<PositionsAtDatePDF positions = {positions}
                                                                   date = {dateOfGetOpenPositionsAtDate}/>}
                                     fileName={`positions-${normalizeDate(dateOfGetOpenPositionsAtDate)}.pdf`}>

                        <button className="btn btn-danger footer-btn" >Download</button>
                    </PDFDownloadLink>
                }
            </div>
        </>
    );
}

export default OpenPositionsAtDateForm;