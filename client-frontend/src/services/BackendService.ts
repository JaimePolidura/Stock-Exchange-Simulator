import axios, {AxiosResponse} from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";
import {LoginRequest} from "./requests/login/LoginRequest";
import {SendBuyOrderRequest} from "./requests/sendbuyorder/SendBuyOrderRequest";
import {SendBuyOrderResponse} from "./requests/sendbuyorder/SendBuyOrderResponse";
import {SendSellOrderRequest} from "./requests/sendsellorder/SendSellOrderRequest";
import {SendSellOrderResponse} from "./requests/sendsellorder/SendSellOrderResponse";
import {GetListedCompanies} from "./requests/getlistedcompanies/GetListedCompanies";
import {GetOpenPositionsResponse} from "./requests/getopenpositions/GetOpenPositionsResponse";
import {CancelOrderResponse} from "./requests/cancelorder/CancelOrderResponse";
import {GetExecutionOrders} from "./requests/getexecutionorders/GetExecutionOrders";

const apiRoute = "http://localhost:8080/";

class BackendService {

    login(request: LoginRequest){
        return axios.post(apiRoute + "auth/login", request);
    }

    getProfielData(){
        return profileData;
    }

    sendBuyOrder(order: SendBuyOrderRequest): Promise<AxiosResponse<SendBuyOrderResponse>>{
        return axios.post(apiRoute + "orders/buy/send", order);
    }

    sendSellOrder(order: SendSellOrderRequest): Promise<AxiosResponse<SendSellOrderResponse>>{
        return axios.post(apiRoute + "orders/sell/send", order);
    }

    getListedCompanies(ticker: string): Promise<AxiosResponse<GetListedCompanies>>{
        return axios.get(apiRoute + "listedcompanies/get/" + ticker);
    }

    getPendingOrders(): Promise<AxiosResponse<GetExecutionOrders>>{
        return axios.get(apiRoute + "orders/execution/get?orderStates=PENDING");
    }

    getAllListedCompanies(): Promise<AxiosResponse<GetListedCompanies>>{
        return axios.get(apiRoute + "listedcompanies/get");
    }

    getOpenPositions(): Promise<AxiosResponse<GetOpenPositionsResponse>>{
        return axios.get(apiRoute + "positions/open/get");
    }
    
    cancelOrder(orderId: string): Promise<AxiosResponse<CancelOrderResponse>>{
        return axios.post(apiRoute + "orders/cancel/send/" + orderId);
    }

}

export default new BackendService();
