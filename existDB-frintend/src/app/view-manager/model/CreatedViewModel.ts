export class CreatedViewModel {
    user: string;
    date: string;
    configLocation: string;
    queryName: string;
    viewLocation: string;

    constructor(user: string, date: string, configLocation: string, queryName: string, viewLocation: string) {
        this.user = user;
        this.date = date;
        this.configLocation = configLocation;
        this.queryName = queryName;
        this.viewLocation = viewLocation;
    }
}
