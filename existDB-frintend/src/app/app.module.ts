import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HomePageComponent} from './home-page-module/home-page/home-page.component';
import {LoginPageComponent} from './auth-module/login-page/login-page.component';
import {TopBarComponent} from './top-bar-module/top-bar.component';
import {RegistrationPageComponent} from './auth-module/registration-page/registration-page.component';
import {HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {
  _MatMenuDirectivesModule, MatButtonModule,
  MatCardModule, MatCheckboxModule,
  MatInputModule, MatListModule, MatMenuModule,
  MatPaginatorModule, MatSidenavModule,
  MatSortModule,
  MatTableModule,
  MatToolbarModule, MatIconModule,
} from '@angular/material';
import { DataTableComponent } from './data-table-module/data-table/data-table.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxEditorModule } from 'ngx-editor';
import { TargyakListaComponent } from './targyak-module/targyak-lista/targyak-lista.component';
import { UserManagerComponent } from './user-manager-module/user-manager.component';
import {httpInterceptorProvider} from './auth-module/auth-interceptor';
import {AuthGuardService} from './auth-module/auth-guard.service';
import { UserListComponent } from './user-manager-module/user-list/user-list.component';
import { AddUserComponent } from './user-manager-module/add-user/add-user.component';
import { UserDetailsComponent } from './user-manager-module/user-details/user-details.component';
import { ErrorComponent } from './errors-module/error/error.component';
import { PmMainComponent } from './pm_functions-module/pm-main/pm-main.component';
import { UserManagerSidenemuComponent } from './user-manager-module/user-manager-sidenemu/user-manager-sidenemu.component';
import { MainNavComponent } from './forComponentTesting/main-nav/main-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { ExistUsersListComponent } from './user-manager-module/exist-users-list/exist-users-list.component';
import { ExistUsersEditDetailsComponent } from './user-manager-module/exist-users-edit-details/exist-users-edit-details.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    LoginPageComponent,
    TopBarComponent,
    RegistrationPageComponent,
    DataTableComponent,
    TargyakListaComponent,
    UserManagerComponent,
    UserListComponent,
    AddUserComponent,
    UserDetailsComponent,
    ErrorComponent,
    PmMainComponent,
    UserManagerSidenemuComponent,
    MainNavComponent,
    ExistUsersListComponent,
    ExistUsersEditDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    MatToolbarModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    BrowserAnimationsModule,
    MatListModule,
    FormsModule,
    NgxEditorModule,
    _MatMenuDirectivesModule,
    MatMenuModule,
    MatButtonModule,
    MatCheckboxModule,
    MatSidenavModule,
    LayoutModule,
    MatIconModule
  ],
  providers: [httpInterceptorProvider, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
