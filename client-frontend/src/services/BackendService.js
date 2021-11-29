import axios from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";

const apiRoute = "http://localhost:8080/";

class BackendService {

    login(request){
        return axios.post(apiRoute + "login", request);
    }

    getProfielData(){
        //Instead of making an API I will use in memory trades
        return profileData;
    }

    executeOrder(order){
        return axios.post(apiRoute + "executeorder", order);
    }
}

export default new BackendService();
