import React from 'react'
import ReactDom from 'react-dom'
import './Modal.css';
import {Modal} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';

export default function MyModal({ open, children, onClose }) {
    if (!open) return null

    return ReactDom.createPortal(
        <>
            <div class="overlay_styles"/>
            <div class="wrapper">
                <Modal show={open} onHide={onClose} centered>
                    {children}
                </Modal>
            </div>
        </>,
        document.getElementById('portal')
    )
}
