import axios from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";

const apiRoute = "http://localhost:8080/";

class BackendService {

    login(request: any){
        return axios.post(apiRoute + "auth/login", request);
    }

    getProfielData(){
        return profileData;
    }

    sendBuyOrder(order: any){
        return axios.post(apiRoute + "orders/buy/send", order);
    }

    sendSellOrder(order: any){
        return axios.post(apiRoute + "orders/sell/send", order);
    }

    getCompanyIsListedData(ticker: any){
        return axios.get(apiRoute + "listedcompanies/get/" + ticker);
    }

    getSellOrders(){
        return axios.get(apiRoute + "orders/sell/get?orderStates=PENDING");
    }

    getBuyOrders(){
        return axios.get(apiRoute + "orders/buy/get?orderStates=PENDING");
    }

    getAllListedCompanies(){
        return axios.get(apiRoute + "listedcompanies/get");
    }

    getOpenPositions(){
        return axios.get(apiRoute + "positions/open/get");
    }
    
    cancelOrder(orderId: any){
        return axios.post(apiRoute + "orders/cancel/send/" + orderId);
    }

}

export default new BackendService();
