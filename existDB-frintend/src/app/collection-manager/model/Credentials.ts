export class Credentials {
    name: string;
    path: string;
    owner: string;
    group: string;
    mode: string;
    date: string;
    mime: string;
    resource: boolean;
    triggerConfigAvailable: boolean;


    constructor(name: string, path: string, owner: string, group: string, mode: string, date: string, mime: string, resource: boolean, triggerConfigAvailable: boolean) {
        this.name = name;
        this.path = path;
        this.owner = owner;
        this.group = group;
        this.mode = mode;
        this.date = date;
        this.mime = mime;
        this.resource = resource;
        this.triggerConfigAvailable = triggerConfigAvailable;
    }
}
