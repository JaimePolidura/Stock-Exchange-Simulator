import BackendService from "./BackendService";

class AuthenticationService {
    constructor() {
        this.authenticated = false;
        this.token = null;
    }

    login(request, onSuccess, onFailure){
        BackendService.login(request)
            .then(response => onSuccess(response.data))
            .catch(error => onFailure(error));
    }

    isAuthenticated(){
        return this.authenticated;
    }

    getToken(){
        return this.token;
    }

    setAuthenticated(value) {
        this.authenticated = value;
    }

    setToken(value){
        this.token = value;
    }
}

export default new AuthenticationService();
