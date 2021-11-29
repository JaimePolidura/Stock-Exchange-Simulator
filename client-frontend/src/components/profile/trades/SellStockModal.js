import React, {Component, useState} from 'react';
import {Modal} from "react-bootstrap";
import {useForm} from "react-hook-form";
import backend from "../../../services/BackendService";

const SellStockModal = props => {
    const {register, handleSubmit, formState: { errors }} = useForm();

    const [ sellExecutionType, setSellExecutionType ] = useState('market');

    const onSubmit = request => {
        let sellExecutionTypeToApi = {};

        if(sellExecutionType == 'market'){
            sellExecutionTypeToApi = {
                executionPrice: -1,
            }
        }else{
            sellExecutionTypeToApi = {
                executionPrice: request.price,
            }
        }

        let finalRequestToApi = {
            orderType: 'SELL',
            executionType: sellExecutionTypeToApi.executionPrice,
            quantity: request.quantity,
            ticker: props.trade.ticker,
        }

        backend.executeOrder(finalRequestToApi).then(
            response => console.log("ok"),
            error => window.alert(error),
        );
    }

    return (
        <Modal show={props.showModal} onHide={() => props.onHide()} centered>
            <Modal.Header closeButton>
                <Modal.Title><h3>Sell {props.trade.name}</h3></Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>
                    Currency: {props.trade.currency.code}<br />
                    Initial price: {props.trade.averagePrice}<br />
                    Acual price: {props.trade.actualPrice}<br />
                    Liquidation value: {props.renderMarketValue()}<br />
                </p>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <input type="number"
                           className="form-control"
                           placeholder="quantity"
                           defaultValue={props.trade.quantity}
                           {...register('quantity', { required: true, min: 1, max: props.trade.quantity })}/>

                    <br />

                    <select class="form-control"
                            defaultValue={sellExecutionType}
                            onChange={e => setSellExecutionType(e.target.value)}>

                        <option value="market">Market</option>
                        <option value="limit">Limit</option>
                    </select>

                    <br />

                    {sellExecutionType == "limit" &&
                        <input type="text"
                               class="form-control"
                               placeholder="Price"
                               {...register('price', { required: false, min: 0 })}/>
                    }

                    <hr />
                    <div className="mymodal-footer">
                        <input type="submit"
                               className={!errors.quantity && !errors.price ? "btn btn-primary" : "btn btn-primary disabled"}
                               value="✓ Sell ✓"/>
                    </div>
                </form>
            </Modal.Body>
        </Modal>
    );
}

export default SellStockModal;
