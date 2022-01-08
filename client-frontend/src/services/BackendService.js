import axios from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";

const apiRoute = "http://localhost:8080/";

class BackendService {

    login(request){
        return axios.post(apiRoute + "auth/login", request);
    }

    getProfielData(){
        return profileData;
    }

    sendBuyOrder(order){
        return axios.post(apiRoute + "orders/buy/send", order);
    }

    sendSellOrder(order){
        return axios.post(apiRoute + "orders/sell/send", order);
    }

    getCompanyIsListedData(ticker){
        return axios.get(apiRoute + "listedcompanies/get/" + ticker);
    }

    getOrdersBuyAndSell() {
        return axios.get(apiRoute + "orders/get/type?orderTypesId=1,2");
    }

    getAllListedCompanies(){
        return axios.get(apiRoute + "listedcompanies/get");
    }

    getOpenPositions(){
        return axios.get(apiRoute + "positions/open/get");
    }

    cancelOrder(orderId){
        return axios.post(apiRoute + "orders/send/cancel/" + orderId);
    }

}

export default new BackendService();
