export class Credentials {
    name: string;
    path: string;
    owner: string;
    group: string;
    mode: string;
    date: string;
    writable: boolean;
    resource: boolean;
    triggerConfigAvailable: boolean;

    constructor(name: string, path: string, owner: string, group: string, mode: string, date: string, writable: boolean, resource: boolean,
                triggerConfigAvailable: boolean) {
        this.name = name;
        this.path = path;
        this.owner = owner;
        this.group = group;
        this.mode = mode;
        this.date = date;
        this.writable = writable;
        this.resource = resource;
        this.triggerConfigAvailable = triggerConfigAvailable;
    }
}
