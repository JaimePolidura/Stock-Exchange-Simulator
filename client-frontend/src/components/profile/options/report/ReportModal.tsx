import React, {ReactComponentElement, useState} from 'react';
import {Modal} from "react-bootstrap";
import "react-datepicker/dist/react-datepicker.css";
import './ReportModal.css';
import {ReportType} from "../../../../model/reports/ReportType";
import {EnumDictionary} from "../../../../utils/EnumDictionary";
import OpenPositionsAtDateForm from "./positionsatdate/OpenPositionsAtDateForm";
import SalesAtYearForm from "./salesatyear/SalesAtYearForm";

const ReportModal = (props: any) => {
    const [ reportType, setReportType ] = useState<ReportType>(ReportType.OPEN_POSITIONS_AT_DATE);

    const forms: EnumDictionary<ReportType, ReactComponentElement<any>> = {
        [ReportType.OPEN_POSITIONS_AT_DATE]: <OpenPositionsAtDateForm />,
        [ReportType.SALES_AT_YEAR]: <SalesAtYearForm />,
    };

    const onChangeExecutionType = (e: any): void => {
        // @ts-ignore
        setReportType(ReportType[e.target.value]);
    }

    const renderForm = (): ReactComponentElement<any> => {
        return forms[reportType];
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
                    {renderForm()}
                    <br />
                </Modal.Body>
            </Modal>
        </>
    );
}

export default ReportModal;