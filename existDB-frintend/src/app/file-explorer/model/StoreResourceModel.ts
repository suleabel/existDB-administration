export class StoreResourceModel {
    url: string;
    fileName: string;
    content: string;
    isBinary: boolean;


    constructor(url: string, fileName: string, content: string, isBinary: boolean) {
        this.url = url;
        this.fileName = fileName;
        this.content = content;
        this.isBinary = isBinary;
    }
}
