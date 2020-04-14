export class ExistGroupModel {
    groupName: string;
    groupManager: string;
    desc: string;
    groupMembers: string[];

    constructor(groupName: string, groupManager: string, desc: string, groupMembers: string[]) {
        this.groupName = groupName;
        this.groupManager = groupManager;
        this.desc = desc;
        this.groupMembers = groupMembers;
    }
}
