import axios from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";

const apiRoute = "http://localhost:8080/";

class BackendService {

    login(request){
        return axios.post(apiRoute + "login", request);
    }

    getProfielData(){
        return profileData;
    }

    sendBuyOrder(order){
        return axios.post(apiRoute + "orders/send/buy", order);
    }

    sendSellOrder(order){
        return axios.post(apiRoute + "orders/send/sell", order);
    }

    getCompanyIsListedData(ticker){
        return axios.get(apiRoute + "listedcompanies/get/" + ticker);
    }

    getOrdersBuyAndSell() {
        return axios.get(apiRoute + "orders/get/type?types=1,2");
    }

    getAllListedCompanies(){
        return axios.get(apiRoute + "listedcompanies/get");
    }

    getTrades(){
        return axios.get(apiRoute + "trades/get");
    }

}

export default new BackendService();
