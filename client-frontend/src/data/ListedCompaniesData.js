import backendService from "../services/BackendService";

class ListedCompaniesData {
    constructor() {
        this.listedCompanies = [];
    }

    setListedCompanies(listedCompanies){
        this.listedCompanies = listedCompanies;
    }

    getListedCompany(ticker){
        return this.listedCompanies.length > 0 ?
            this.listedCompanies.find(listedCompany => listedCompany.ticker === ticker) :
            {name: 'Loading...', ticker: 'Loading...'};
    }

    getCompanyNameFromTicker(ticker){
        return this.getListedCompany(ticker).name;
    }
}

export default new ListedCompaniesData();