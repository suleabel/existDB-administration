export class SaveModel {
    url: string;
    fileName: string;
    content: string;


    constructor(url: string, fileName: string, content: string) {
        this.url = url;
        this.fileName = fileName;
        this.content = content;
    }
}
