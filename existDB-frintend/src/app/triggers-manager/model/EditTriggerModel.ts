import {Credentials} from '../../file-explorer/model/Credentials';

export class EditTriggerModel {
    credentials: Credentials;
    event: string;
    tClass: string;
    name: string;
    value: string;


    constructor(credentials: Credentials, event: string, tClass: string, name: string, value: string) {
        this.credentials = credentials;
        this.event = event;
        this.tClass = tClass;
        this.name = name;
        this.value = value;
    }
}
