export class CreateBackupEntity {
    saveLocation: string;
    isZip: string;
    isIncremental: string;

    constructor(saveLocation: string, isZip: string, isIncremental: string) {
        this.saveLocation = saveLocation;
        this.isZip = isZip;
        this.isIncremental = isIncremental;
    }
}
