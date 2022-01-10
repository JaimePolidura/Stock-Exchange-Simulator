import React, {useState} from 'react';
import {Modal} from "react-bootstrap";
import {useForm} from "react-hook-form";
import backendService from "../../../services/BackendService";
import backend from "../../../services/BackendService";

const BuyStockModal = (props: any) => {
    const {register, handleSubmit, formState: { errors }, setError, clearErrors, reset} = useForm();
    const [ buyExecutionType, setBuyExecutionType ] = useState('market');
    const [ listedCompany, setListedCompany ] = useState(null);

    const onSubmit = (form: any) => {
        let finalRequestToApi = {
            executionPrice: buyExecutionType == 'market' ? -1 : form.price,
            quantity: form.quantity,
            ticker: form.ticker.toUpperCase(),
        }

        backend.sendBuyOrder(finalRequestToApi)
            .then(response => onSuccess(response))
            .catch(error => onFailure(error));
    }

    const onChangeExecutionType = (e: any) => {
        setBuyExecutionType(e.target.value);

        if(e.target.value === 'market'){
            clearErrors('price');
        }
    }

    const onSuccess = (response: any) => {
        // @ts-ignore
        let listedCompanyTicker = listedCompany == null ? "" : listedCompany.ticker;

        let orderToDisplay = {
            orderId: response.data.orderId,
            ticker: listedCompanyTicker,
            quantity: response.data.quantity,
            date: response.data.date,
            executionPrice: response.data.executionPrice,
            type: 'Buy',
        }

        props.onOrderBuySended(orderToDisplay);

        window.alert("The order has been sended");
    }

    const onFailure = (error: any) => {
        console.log(error);
    }

    const onTickerInputChanged = (e: any) => {
        let ticker = e.target.value;

        backendService.getCompanyIsListedData(ticker)
            .then(response => {
                setListedCompany(response.data);
                clearErrorToInputTicker();
            })
            .catch(error => {
                addErrorToInputTicker();
                setListedCompany(null);
            });
    }

    const clearErrorToInputTicker = () => {
        clearErrors('ticker');
    }

    const addErrorToInputTicker = () => {
        setError("ticker", {
            type: "manual",
            message: "Company not listed",
        });
    }

    const onClose = () => {
        reset();
    }

    return (
        <>
            <Modal show={props.showModal} onExit={() => onClose()} onHide={() => props.onHide()} centered>
                <Modal.Header closeButton>
                    <Modal.Title><h3>Buy</h3></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <input type="text"
                               placeholder="Ticker"
                               className={errors.ticker ? 'form-control is-invalid' : 'form-control'}
                               onKeyUp={e => onTickerInputChanged(e)}
                               {...register('ticker', {required: true})}/><br />

                        <input type="number"
                               placeholder="Quantity"
                               className={errors.quantity ? 'form-control is-invalid' : 'form-control'}
                               {...register('quantity', {required: true, min: 1})}/><br />
                        
                        <select className="form-control"
                                defaultValue={buyExecutionType}
                                onChange={e => onChangeExecutionType(e)}>

                            <option value="market">Market</option>
                            <option value="limit">Limit</option>
                        </select><br />

                        {buyExecutionType == "limit" &&
                        <input type="number"
                               placeholder="Price"
                               className={errors.price ? 'form-control is-invalid' : 'form-control'}
                               {...register('price', { required: false, min: 1 })}/>
                        }<br />

                        <div className="mymodal-footer">
                            <input type="submit"
                                   className={!errors.ticker && !errors.price && !errors.quantity ? "btn btn-primary" : "btn btn-primary disabled"}
                                   value="✓ Buy ✓"/>
                        </div>
                    </form>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default BuyStockModal;
