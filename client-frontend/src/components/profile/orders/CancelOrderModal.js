import React, {useState} from 'react';
import {Modal} from "react-bootstrap";
import './CancelOrderModal.css';
import backend from "../../../services/BackendService";

const CancelOrderModal = props => {
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
                    <div class="options">
                        <button onClick={() => cancelOrder()} class="btn btn-danger">Yes</button>
                        <button onClick={() => close()} class="btn btn-primary">No</button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default CancelOrderModal;