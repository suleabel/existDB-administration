export class ExistUserModel {
    username: string;
    fullName: string;
    umask: string;
    primaryGroup: string;
    groups: string [];
    desc: string;
    constructor(username: string, fullName: string, umask: string, primaryGroup: string, groups: string[], desc: string) {
        this.username = username;
        this.fullName = fullName;
        this.umask = umask;
        this.primaryGroup = primaryGroup;
        this.groups = groups;
        this.desc = desc;
    }
}
