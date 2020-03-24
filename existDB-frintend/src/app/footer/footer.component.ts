import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../auth-module/token-storage.service';

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.sass']
})
export class FooterComponent implements OnInit {
    info: {
        username: string,
        serverIp: string
    };

    constructor(
        private token: TokenStorageService) {
    }

    ngOnInit() {
        this.info = {
            username: this.token.getUsername(),
            serverIp: this.token.getServerIp()
        };
    }

}
