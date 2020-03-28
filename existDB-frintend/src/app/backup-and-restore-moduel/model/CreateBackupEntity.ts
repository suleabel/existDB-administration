export class CreateBackupEntity {
    saveLocation: string;
    isZip: boolean;
    isIncremental: boolean;

    constructor(saveLocation: string, isZip: boolean, isIncremental: boolean) {
        this.saveLocation = saveLocation;
        this.isZip = isZip;
        this.isIncremental = isIncremental;
    }
}
