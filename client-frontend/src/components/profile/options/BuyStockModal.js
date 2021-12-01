import React, {useState} from 'react';
import {Modal} from "react-bootstrap";
import {useForm} from "react-hook-form";

const BuyStockModal = props => {
    const {register, handleSubmit, formState: { errors }} = useForm();
    const [ buyExecutionType, setBuyExecutionType ] = useState('market');

    const onSubmit = form => {
        console.log(form);
    }

    const onTickerInputChanged = e => {
        //TODO Request to the api
    }

    return (
        <Modal show={props.showModal} onHide={() => props.onHide()} centered>
            <Modal.Header closeButton>
                <Modal.Title><h3>Buy</h3></Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <input type="text"
                           placeholder="Ticker"
                           className="form-control"
                           onKeyUp={e => onTickerInputChanged(e)}
                           {...register('ticker', {required: true})}/><br />

                    <select className="form-control"
                            defaultValue={buyExecutionType}
                            onChange={e => setBuyExecutionType(e.target.value)}>

                        <option value="market">Market</option>
                        <option value="limit">Limit</option>
                    </select><br />

                    {buyExecutionType == "limit" &&
                    <input type="text"
                           class="form-control"
                           placeholder="Price"
                           {...register('price', { required: false, min: 0 })}/>
                    }<br />

                    <div className="mymodal-footer">
                        <input type="submit"
                               className={!errors.ticker && !errors.price ? "btn btn-primary" : "btn btn-primary disabled"}
                               value="✓ Buy ✓"/>
                    </div>
                </form>
            </Modal.Body>
        </Modal>
    );
}

export default BuyStockModal;
