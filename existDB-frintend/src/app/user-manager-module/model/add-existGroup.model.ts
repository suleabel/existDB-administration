export class AddExistGroupModel {
    groupName: string;
    groupManager: string;
    desc: string;

    constructor(groupName: string, groupManager: string, desc: string) {
        this.groupName = groupName;
        this.groupManager = groupManager;
        this.desc = desc;
    }
}
