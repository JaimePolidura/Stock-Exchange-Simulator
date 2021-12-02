import React, {useState} from 'react';
import {Modal} from "react-bootstrap";
import {useForm} from "react-hook-form";
import backendService from "../../../services/BackendService";

const BuyStockModal = props => {
    const {register, handleSubmit, formState: { errors }, setError, clearErrors} = useForm();
    const [ buyExecutionType, setBuyExecutionType ] = useState('market');

    const onSubmit = form => {
        //TODO
    }

    const onTickerInputChanged = e => {
        let ticker = e.target.value;

        backendService.checkIfCompanyIsListed(ticker).then(response => {
            let listed = response.data;

            if(listed){
                clearErrorToInputTicker();
            }else{
                addErrorToInputTicker();
            }
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

    return (
        <Modal show={props.showModal} onHide={() => props.onHide()} centered>
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

                    <select className="form-control"
                            defaultValue={buyExecutionType}
                            onChange={e => setBuyExecutionType(e.target.value)}>

                        <option value="market">Market</option>
                        <option value="limit">Limit</option>
                    </select><br />

                    {buyExecutionType == "limit" &&
                    <input type="number"
                           placeholder="Price"
                           className={errors.price ? 'form-control is-invalid' : 'form-control'}
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
