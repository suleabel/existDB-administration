export class ReversionsModel {
    revNo: string;
    date: string;
    user: string;

    constructor(revNo: string, date: string, user: string) {
        this.revNo = revNo;
        this.date = date;
        this.user = user;
    }
}
