export class StoreResourceModel {
    url: string;
    fileName: string;
    content: string;
    mime: string;
    isBinary: boolean;


    constructor(url: string, fileName: string, content: string, mime: string, isBinary: boolean) {
        this.url = url;
        this.fileName = fileName;
        this.content = content;
        this.mime = mime;
        this.isBinary = isBinary;
    }
}
