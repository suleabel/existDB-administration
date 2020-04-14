import {Injectable} from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
    providedIn: 'root'
})
export class TokenStorageService {

    constructor() {
    }
    private roles: Array<string> = [];

    public static saveToken(token: string) {
        window.sessionStorage.removeItem(TOKEN_KEY);
        window.sessionStorage.setItem(TOKEN_KEY, token);
    }

    public static getToken(): string {
        return sessionStorage.getItem(TOKEN_KEY);
    }

    public static saveUsername(username: string) {
        window.sessionStorage.removeItem(USERNAME_KEY);
        window.sessionStorage.setItem(USERNAME_KEY, username);
    }

    public static getUsername(): string {
        return sessionStorage.getItem(USERNAME_KEY);
    }

    public static saveAuthorities(authorities: string[]) {
        window.sessionStorage.removeItem(AUTHORITIES_KEY);
        window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
    }

    signOut() {
        window.sessionStorage.clear();
    }
}
