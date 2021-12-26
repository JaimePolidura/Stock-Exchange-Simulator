class ListedCompaniesService {
    constructor() {
        this.listedCompanies = null;
    }

    setListedCompanies(listedCompanies){
        this.listedCompanies = listedCompanies;
    }

    getListedCompany(ticker){
        return this.listedCompanies.find(listedCompany => listedCompany.ticker === ticker);
    }
}

export default new ListedCompaniesService();