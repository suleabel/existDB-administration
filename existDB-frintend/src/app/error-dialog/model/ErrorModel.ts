export class ErrorModel {
    timestamp: string;
    status: number;
    exceptionType: string;
    subtype: string;
    location: string;
    message: string;
    url: string;
    constructor(timestamp: string, status: number, exceptionType: string, subtype: string, location: string, message: string, url: string) {
        this.timestamp = timestamp;
        this.status = status;
        this.exceptionType = exceptionType;
        this.subtype = subtype;
        this.location = location;
        this.message = message;
        this.url = url;
    }
}
