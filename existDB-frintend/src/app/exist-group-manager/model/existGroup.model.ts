export class ExistGroupModel {
    name: string;
    manager: string;
    desc: string;
    members: string[];


    constructor(name: string, manager: string, desc: string, members: string[]) {
        this.name = name;
        this.manager = manager;
        this.desc = desc;
        this.members = members;
    }
}
