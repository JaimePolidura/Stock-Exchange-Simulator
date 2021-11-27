import axios from 'axios';
import {Trades as trades} from "../components/profile/Trades";

class BackendService {
    login(request){
        return axios.post("http://localhost:8080/login", request);
    }

    getTrades(){
        //Instead of making an API I will use in memory trades
        return trades;
    }
}

export default new BackendService();
