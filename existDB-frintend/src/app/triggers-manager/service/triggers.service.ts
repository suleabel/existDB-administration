import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class TriggersService {
    private Selectedcollection: string;

    constructor() {
    }


    get selectedCollection(): string {
        return this.Selectedcollection;
    }

    set selectedCollection(value: string) {
        this.Selectedcollection = value;
    }
}
