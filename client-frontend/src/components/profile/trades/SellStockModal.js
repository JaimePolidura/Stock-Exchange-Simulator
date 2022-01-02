import React, {Component, useState} from 'react';
import {Modal} from "react-bootstrap";
import {useForm} from "react-hook-form";
import backend from "../../../services/BackendService";

const SellStockModal = props => {
    const {register, handleSubmit, formState: { errors }, reset, clearErrors} = useForm();
    const [ sellExecutionType, setSellExecutionType ] = useState('market');

    const onSubmit = form => {
        let finalRequestToApi = {
            tradeId: props.trade.tradeId,
            quantity: form.quantity,
            executionPrice: sellExecutionType == 'market' ? -1 : form.price,
        }

        backend.sendSellOrder(finalRequestToApi).then(
            response => onSuccess(response),
            error => onFailure(error),
        );
    }

    const onSuccess = response => {
        window.alert("The order has been sended");

        props.onOrderSellSended({
            ...response.data,
            name: props.trade.name,
            totalValueOrder: calculateTotalValueToSell(response.data),
            result: calculateResultToSell(response.data),
        });
    }

    const calculateResultToSell = orderData => {
        let priceToSell = sellExecutionType == 'Limit' ?
            orderData.executionPrice :
            props.trade.actualPrice;

        return Math.round((priceToSell - props.trade.averagePrice) * props.trade.quantity);
    }

    const calculateTotalValueToSell = orderData => {
        let priceToSell = sellExecutionType == 'Limit' ?
            orderData.executionPrice :
            props.trade.actualPrice;

        return Math.round(priceToSell * orderData.quantity);
    }

    const onFailure = response => {
        window.alert("There has been an error " + response);
    }

    const onClose = () => {
        reset();
    }

    const onChangeSellExecutionType = e => {
        setSellExecutionType(e.target.value);

        if(e.target.value === 'market'){
            clearErrors('price');
        }
    }

    return (
        <Modal show={props.showModal} onExit={() => onClose()} onHide={() => props.onHide()} centered>
            <Modal.Header closeButton>
                <Modal.Title><h3>Sell {props.listedCompany.name}</h3></Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>
                    Currency: USD<br />
                    Initial price: {props.trade.averagePrice}<br />
                    Acual price: {props.trade.actualPrice}<br />
                    Liquidation value: {props.renderMarketValue()}<br />
                </p>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <input type="number"
                           className={errors.quantity ? 'form-control is-invalid' : 'form-control'}
                           placeholder="quantity"
                           defaultValue={props.trade.quantity}
                           {...register('quantity', { required: true, min: 1, max: props.trade.quantity })}/>

                    <br />

                    <select class="form-control"
                            defaultValue={sellExecutionType}
                            onChange={e => onChangeSellExecutionType(e)}>

                        <option value="market">Market</option>
                        <option value="limit">Limit</option>
                    </select>

                    <br />

                    {sellExecutionType == "limit" &&
                        <input type="text"
                               className={errors.price ? 'form-control is-invalid' : 'form-control'}
                               placeholder="Price"
                               {...register('price', { required: false, min: 0 })}/>
                    }

                    <hr />
                    <div className="mymodal-footer">
                        <input type="submit"
                               className={!errors.quantity && !errors.price && !errors.quantity? "btn btn-primary" : "btn btn-primary disabled"}
                               value="✓ Sell ✓"/>
                    </div>
                </form>
            </Modal.Body>
        </Modal>
    );
}

export default SellStockModal;
