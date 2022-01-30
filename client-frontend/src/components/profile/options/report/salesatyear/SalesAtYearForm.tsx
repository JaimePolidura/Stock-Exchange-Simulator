import React from "react";
import "../Report.css";

export const SalesAtYearForm = (props: any) => {
    const onSubmit = () => {}

    return (
        <>
            <select className="form-control">
                <option>2019</option>
                <option>2020</option>
                <option>2021</option>
                <option>2022</option>
            </select>
            <br />
            <br />
            <div className="mymodal-footer">
                <button className="btn btn-primary footer-btn" onClick={() => onSubmit()}>✓ Generate ✓</button>
            </div>
        </>
    );
}

export default SalesAtYearForm;

