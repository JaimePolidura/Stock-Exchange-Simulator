import React, {useState} from 'react';
import {Modal} from "react-bootstrap";

const ReportModal = (props: any) => {
    return (
        <>
            <Modal show={props.showModal} onHide={() => props.onHide()} centered>
                <Modal.Header closeButton>
                    <Modal.Title><h3>Report</h3></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <select className="form-control">
                            <option>2019</option>
                            <option>2020</option>
                            <option>2021</option>
                            <option>2022</option>
                        </select>
                        <br />
                        <div className="mymodal-footer">
                            <input type="submit"
                                   className="btn btn-primary"
                                   value="✓ Generate ✓" />
                        </div>
                    </form>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default ReportModal;