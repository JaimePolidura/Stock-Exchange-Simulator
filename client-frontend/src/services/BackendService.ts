import axios, {AxiosResponse} from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";
import {LoginRequest} from "./model/login/LoginRequest";
import {SendBuyOrderRequest} from "./model/sendbuyorder/SendBuyOrderRequest";
import {SendBuyOrderResponse} from "./model/sendbuyorder/SendBuyOrderResponse";
import {SendSellOrderRequest} from "./model/sendsellorder/SendSellOrderRequest";
import {SendSellOrderResponse} from "./model/sendsellorder/SendSellOrderResponse";
import {GetListedCompanies} from "./model/getlistedcompanies/GetListedCompanies";
import {GetSellOrdersResponse} from "./model/getsellorders/GetSellOrdersResponse";
import {GetBuyOrdersResponse} from "./model/getbuyorders/GetBuyOrdersResponse";
import {GetOpenPositionsResponse} from "./model/getopenpositions/GetOpenPositionsResponse";
import {OpenPosition} from "./model/getopenpositions/OpenPosition";
import {CancelOrderResponse} from "./model/cancelorder/CancelOrderResponse";

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

    getSellOrders(): Promise<AxiosResponse<GetSellOrdersResponse>>{
        return axios.get(apiRoute + "orders/sell/get?orderStates=PENDING");
    }

    getBuyOrders(): Promise<AxiosResponse<GetBuyOrdersResponse>>{
        return axios.get(apiRoute + "orders/buy/get?orderStates=PENDING");
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
