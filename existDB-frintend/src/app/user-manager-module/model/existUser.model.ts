export class ExistUserModel {
    username: string;
    password: string;
    umask: string;
    primaryGroup: string;
    groups: Array<string>;
    fullName: string;
    desc: string;
    constructor(username: string, password: string, umask: string, primaryGroup: string,
                groups: Array<string>, fullName: string, desc: string) {
        this.username = username;
        this.password = password;
        this.umask = umask;
        this.primaryGroup = primaryGroup;
        this.groups = groups;
        this.fullName = fullName;
        this.desc = desc;
    }
}
