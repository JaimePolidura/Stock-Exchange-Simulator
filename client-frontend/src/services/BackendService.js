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

    executeOrder(order){
        return axios.post(apiRoute + "orders/execute", order);
    }

    getCompanyIsListedData(ticker){
        return axios.get(apiRoute + "listedcompanies/get/" + ticker);
    }

    getOrders() {
        return axios.get(apiRoute + "orders/get");
    }

    getAllListedCompanies(){
        return axios.get(apiRoute + "listedcompanies/get");
    }

}

export default new BackendService();
