export class FileManagerEntity {
    isFile: boolean;
    name: string;
    size: string;
    humanSize: string;
    modified: string;
    hidden: boolean;
    canRead: boolean;
    canWrite: boolean;

    constructor(isFile: boolean, name: string, size: string, humanSize: string, modified: string, hidden: boolean, canRead: boolean, canWrite: boolean) {
        this.isFile = isFile;
        this.name = name;
        this.size = size;
        this.humanSize = humanSize;
        this.modified = modified;
        this.hidden = hidden;
        this.canRead = canRead;
        this.canWrite = canWrite;
    }
}
