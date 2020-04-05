import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../auth-module/token-storage.service';
import {AuthService} from '../auth-module/auth.service';
import {BehaviorSubject} from 'rxjs';

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.sass']
})
export class FooterComponent implements OnInit {
    public serverVersion?: BehaviorSubject<string> = new BehaviorSubject('');
    public username: string;
    public serverIp?: BehaviorSubject<string> = new BehaviorSubject('');

    constructor(
        private token: TokenStorageService,
        private authService: AuthService) {
    }

    ngOnInit() {
        this.username = TokenStorageService.getUsername();
        this.authService.getDbVersion().subscribe(data => {
                this.serverVersion.next(data.response);
            },
            error => {
                console.log(error.error);
            });
        this.authService.getServerIp().subscribe(data => {
                this.serverIp.next(data.response.toLowerCase());
            },
            error => {
                console.log(error.error);
            });
    }

}
