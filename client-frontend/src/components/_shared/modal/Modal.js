import React from 'react'
import ReactDom from 'react-dom'
import './Modal.css';

export default function Modal({ open, children, onClose }) {
    if (!open) return null

    return ReactDom.createPortal(
        <>
            <div class="overlay_styles" />
            <div class="modal-styles">
                <div className="modal-top">
                    <span onClick={onClose} aria-hidden="true" className="close-button">&times;</span>
                </div>
                {children}
            </div>
        </>,
        document.getElementById('portal')
    )
}
