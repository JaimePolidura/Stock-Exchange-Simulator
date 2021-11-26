import axios from 'axios';

class BackendService {
    login(request){
        return axios.post("http://localhost:8080/login", request);
    }
}

export default new BackendService();
