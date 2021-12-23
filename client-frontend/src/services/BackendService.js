import axios from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";

const apiRoute = "http://localhost:8080/";

class BackendService {

    login(request){
        console.log(request)

        return axios.post(apiRoute + "login", request);
    }

    getProfielData(){
        return profileData;
    }

    executeOrder(order){
        return axios.post(apiRoute + "executeorder", order);
    }

    getCompanyIsListedData(ticker){
        return axios.get(apiRoute + "getlistedcompany/" + ticker);
    }

    getOrders(){
        return axios.get(apiRoute + "getorders");
    }
}

export default new BackendService();
