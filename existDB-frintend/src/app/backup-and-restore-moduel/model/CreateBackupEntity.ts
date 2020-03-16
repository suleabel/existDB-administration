export class CreateBackupEntity {
    isZip: boolean;
    isIncremental: boolean;
    constructor(isZip: boolean, isIncremental: boolean) {
        this.isZip = isZip;
        this.isIncremental = isIncremental;
    }
}
