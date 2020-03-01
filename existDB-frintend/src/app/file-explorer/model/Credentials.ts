export class Credentials {
    name: string;
    path: string;
    owner: string;
    group: string;
    mode: string;
    date: string;
    writable: boolean;
    resource: boolean;

    constructor(name: string, path: string, owner: string, group: string, mode: string, date: string, writeable: boolean, isResource:
        boolean) {
        this.name = name;
        this.path = path;
        this.owner = owner;
        this.group = group;
        this.mode = mode;
        this.date = date;
        this.writable = writeable;
        this.resource = isResource;
    }
}
