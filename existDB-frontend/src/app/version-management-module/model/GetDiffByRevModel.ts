export class GetDiffByRevModel {
    revNo: string;
    path: string;

    constructor(revNo: string, path: string) {
        this.revNo = revNo;
        this.path = path;
    }
}
