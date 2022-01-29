import React, {useState} from 'react';
import {Modal} from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import './ReportModal.css';
import {ReportType} from "../../../../services/reports/ReportType";

const ReportModal = (props: any) => {
    const [ reportType, setReportType ] = useState<ReportType>(ReportType.OPEN_POSITIONS_AT_DATE);
    const [ dateOfGetOpenPositionsAtDate, setDateOfGetOpenPositionsAtDate ] = useState<Date | null>(new Date());

    const onSubmit = () => {
    }

    const onChangeExecutionType = (e: any): void => {
        if(e.target.value == 'OPEN_POSITIONS_AT_DATE'){
            setReportType(ReportType.OPEN_POSITIONS_AT_DATE);
        }else{
            setReportType(ReportType.SALES_AT_YEAR);
        }
    }

    const renderGenerateReportOpenPositionsAtDate = (): any => {
        return <DatePicker className="date-picker" selected={dateOfGetOpenPositionsAtDate} onChange={(date) => setDateOfGetOpenPositionsAtDate(date)} />;
    }

    const renderGenerateReportSalesAtYear = (): any => {
        return (
            <>
                <select className="form-control">
                    <option>2019</option>
                    <option>2020</option>
                    <option>2021</option>
                    <option>2022</option>
                </select>
            </>
        );
    }

    return (
        <>
            <Modal show={props.showModal} onHide={() => props.onHide()} centered>
                <Modal.Header closeButton>
                    <Modal.Title><h3>Generate report</h3></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <select className="form-control" onChange={e => onChangeExecutionType(e)}>
                        <option value="OPEN_POSITIONS_AT_DATE">Open positions at date</option>
                        <option value="SALES_AT_YEAR">Positions closed in year</option>
                    </select>

                    <br />

                    {reportType == ReportType.OPEN_POSITIONS_AT_DATE && renderGenerateReportOpenPositionsAtDate()}
                    {reportType == ReportType.SALES_AT_YEAR && renderGenerateReportSalesAtYear()}

                    <br />
                    <br />

                    <div className="mymodal-footer">
                        <button className="btn btn-primary" onClick={() => onSubmit()}>✓ Generate ✓</button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default ReportModal;