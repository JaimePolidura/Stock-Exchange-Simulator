import BackendService from "./BackendService";

class AuthenticationService {
    constructor() {
        this.authenticated = false;
        this.token = null;
        this.username = null;
    }

    login(request, onSuccess, onFailure){
        BackendService.login(request)
            .then(response => onSuccess(response.data))
            .catch(error => onFailure(error));
    }

    isAuthenticated(){
        return this.authenticated;
    }

    logout(){
        this.authenticated = false;
        this.token = null;
    }

    getToken(){
        return this.token;
    }

    setToken(value){
        this.token = value;
    }

    setAuthenticated(value) {
        this.authenticated = value;
    }

    getUsername(){
        return this.username;
    }

    setUsername(value){
        this.username = value;
    }
}

export default new AuthenticationService();
