import React, {useState} from "react";
import DatePicker from "react-datepicker";

const OpenPositionsAtDateForm = (props: any) => {
    const [ dateOfGetOpenPositionsAtDate, setDateOfGetOpenPositionsAtDate ] = useState<Date>(new Date());

    const onSubmit = () => {
        let normalizedDate = normalizeDate(dateOfGetOpenPositionsAtDate);


    };

    const normalizeDate = (date: Date): string => {
        return date.toLocaleDateString().replace("/", "-").replace("/", "-") + "T00:00";
    }

    const onDateSelected = (date: Date | null) => {
        // @ts-ignore
        setDateOfGetOpenPositionsAtDate(date);
    }

    return (
        <>
            <DatePicker className="date-picker" selected={dateOfGetOpenPositionsAtDate} onChange={(date) => onDateSelected(date)} />
            <br />
            <br />
            <div className="mymodal-footer">
                <button className="btn btn-primary" onClick={() => onSubmit()}>✓ Generate ✓</button>
            </div>
        </>
    );
}

export default OpenPositionsAtDateForm;