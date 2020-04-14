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
    public serverVersion$: BehaviorSubject<string> = new BehaviorSubject('');
    public username: string;
    public serverIp$: BehaviorSubject<string> = new BehaviorSubject('');

    constructor(
        private authService: AuthService) {
    }

    ngOnInit() {
        this.username = TokenStorageService.getUsername();
        this.serverVersion$ = this.authService.serverVersion;
        this.serverIp$ = this.authService.serverIp;
    }

}
