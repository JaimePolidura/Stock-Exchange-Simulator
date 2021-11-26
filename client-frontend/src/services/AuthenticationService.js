import BackendService from "./BackendService";

class AuthenticationService {
    constructor() {
        this.isAuthenticated = false;
        this.token = null;
    }

    login(request, onSuccess, onFailure){
        BackendService.login(request)
            .then(response => {
                this.isAuthenticated = true;
                this.token = response.data.token;

                onSuccess(response.data);
            })
            .catch(error => {
                onFailure(error)
            });
    }

    isAuthenticated(){
        return this.isAuthenticated;
    }

    getToken(){
        return this.token;
    }
}

export default new AuthenticationService;
