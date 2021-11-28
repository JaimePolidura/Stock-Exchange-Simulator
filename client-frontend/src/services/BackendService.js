import axios from 'axios';
import {ProfileData as profileData} from "../components/profile/ProfileData";

class BackendService {
    login(request){
        return axios.post("http://localhost:8080/login", request);
    }

    getProfielData(){
        //Instead of making an API I will use in memory trades
        return profileData;
    }
}

export default new BackendService();
