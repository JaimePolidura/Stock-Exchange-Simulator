class RedirectService {
    redirect(to){
        window.location.href = this.#getBaseURL() + to;
    }

    #getBaseURL = () => {
        let totalURL = window.location;

        return totalURL.protocol + "//" + totalURL.host;
    }
}

export default new RedirectService();
