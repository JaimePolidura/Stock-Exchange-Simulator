import BackendService from "./api/BackendService";
import {LoginRequest} from "./api/requests/login/LoginRequest";
import {LoginResponse} from "./api/requests/login/LoginResponse";

class AuthenticationService {
    authenticated: boolean;
    token: string;
    username: string;

    constructor() {
        this.authenticated = false;
        this.token = "";
        this.username = "";
    }

    login(request: LoginRequest, onSuccess: any, onFailure: any): void{
        BackendService.login(request)
            .then(res => {
                let response: LoginResponse = res.data;

                this.authenticated = true;
                this.token = response.token;
                this.username = request.username;

                onSuccess();
            })
            .catch(error => onFailure(error));
    }

    isAuthenticated(): boolean{
        return this.authenticated;
    }

    logout(): void{
        this.authenticated = false;
        this.token = "";
    }

    getToken(): string{
        return this.token;
    }

    setToken(value: string): void{
        this.token = value;
    }

    setAuthenticated(value: boolean): void {
        this.authenticated = value;
    }

    getUsername(): string{
        return this.username;
    }

    setUsername(value: string): void{
        this.username = value;
    }
}

export default new AuthenticationService();
