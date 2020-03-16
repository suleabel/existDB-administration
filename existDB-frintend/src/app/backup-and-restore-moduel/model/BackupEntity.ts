export class BackupEntity {
    fileName: string;
    nrInSequence: string;
    date: string;
    incremental: string;
    previous: string;
    downloadable: boolean;
    downloadLink: string;


    constructor(fileName: string, nrInSequence: string, date: string, incremental: string, previous: string, downloadable: boolean, downloadLink: string) {
        this.fileName = fileName;
        this.nrInSequence = nrInSequence;
        this.date = date;
        this.incremental = incremental;
        this.previous = previous;
        this.downloadable = downloadable;
        this.downloadLink = downloadLink;
    }
}
