export class EditTriggerModel {
    path: string;
    fName: string;
    event: string;
    tClass: string;
    name: string;
    value: string;
    isOverwrite: string;
    constructor(path: string, fName: string, event: string, tClass: string, name: string, value: string, isOverwrite: string) {
        this.path = path;
        this.fName = fName;
        this.event = event;
        this.tClass = tClass;
        this.name = name;
        this.value = value;
        this.isOverwrite = isOverwrite;
    }
}
