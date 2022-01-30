import React, {useState} from 'react';
import {Modal} from "react-bootstrap";
import {useForm} from "react-hook-form";
import backend from "../../../services/api/BackendService";
import {SendSellOrderRequest} from "../../../services/api/requests/sendsellorder/SendSellOrderRequest";
import {AxiosResponse} from "axios";
import {SendSellOrderResponse} from "../../../services/api/requests/sendsellorder/SendSellOrderResponse";

const SellStockModal = (props: any) => {
    const {register, handleSubmit, formState: { errors }, reset, clearErrors} = useForm();
    const [ sellExecutionType, setSellExecutionType ] = useState('market');

    const onSubmit = (form: any) => {
        let finalRequestToApi: SendSellOrderRequest = {
            positionId: props.trade.positionId,
            quantity: form.quantity,
            priceToExecute: sellExecutionType == 'market' ? -1 : form.price,
        }

        backend.sendSellOrder(finalRequestToApi).then(
            response => onSuccess(response),
            error => onFailure(error),
        );
    }

    const onSuccess = (response: AxiosResponse<SendSellOrderResponse>) => {
        window.alert("The order has been sended");

        let orderAdded = response.data.order;

        let orderToDisplay = {
            orderId: orderAdded.orderId,
            ticker: props.listedCompany.ticker,
            quantity: orderAdded.quantity,
            date: orderAdded.date,
            executionPrice: orderAdded.priceToExecute,
            pendingOrderType: 'SELL',
        }

        props.onOrderSellSended(orderToDisplay);
    }

    const onFailure = (response: any) => {
        window.alert("There has been an error " + response);
    }

    const onClose = () => {
        reset();
    }

    const onChangeSellExecutionType = (e: any) => {
        setSellExecutionType(e.target.value);

        if(e.target.value === 'market')
            clearErrors('price');
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

                    <select className="form-control"
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