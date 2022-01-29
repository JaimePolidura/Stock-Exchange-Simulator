import React, {useState} from 'react';
import {Modal} from "react-bootstrap";
import './CancelOrderModal.css';
import backend from "../../../services/api/BackendService";

const CancelOrderModal = (props: any) => {
    const [ order, setOrder ] = useState(props.order);

    const close = () => {
        props.onHide();
    }

    const cancelOrder = () => {
        backend.cancelOrder(order.orderId)
            .then(res => {
                window.alert("Order sended");
                close();
            }).catch(err => {
                console.error(err);
        });
    }

    return (
        <>
            <Modal show={props.showModal} onHide={() => props.onHide()} centered>
                <Modal.Header closeButton>
                    <Modal.Title><h3>Cancel order</h3></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h6>Are you sure you want to cancel the order?</h6>
                    <br />
                    <div className="options">
                        <button onClick={() => cancelOrder()} className="btn btn-danger">Yes</button>
                        <button onClick={() => close()} className="btn btn-primary">No</button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default CancelOrderModal;