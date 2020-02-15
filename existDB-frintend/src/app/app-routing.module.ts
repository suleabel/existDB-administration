import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginPageComponent} from './auth-module/login-page/login-page.component';
import {HomePageComponent} from './home-page-module/home-page/home-page.component';
import {RegistrationPageComponent} from './auth-module/registration-page/registration-page.component';
import {DataTableComponent} from './data-table-module/data-table/data-table.component';
import {TargyakListaComponent} from './targyak-module/targyak-lista/targyak-lista.component';
import {UserManagerComponent} from './user-manager-module/user-manager.component';
import {AuthGuardService} from './auth-module/auth-guard.service';
import {AppComponent} from './app.component';
import {UserDetailsComponent} from './user-manager-module/user-details/user-details.component';
import {ErrorComponent} from './errors-module/error/error.component';
import {PmMainComponent} from './pm_functions-module/pm-main/pm-main.component';
import {UserManagerSidenemuComponent} from './user-manager-module/user-manager-sidenemu/user-manager-sidenemu.component';

const routes: Routes = [
  {path: 'login', component: LoginPageComponent},
  {path: 'home', component: HomePageComponent, canActivate: [AuthGuardService]},
  {path: 'register', component: RegistrationPageComponent},
  {path: 'data-table', component: DataTableComponent, canActivate: [AuthGuardService]},
  {path: 'targyak-module', component: TargyakListaComponent, canActivate: [AuthGuardService]},
  {path: 'user-manager-module', component: UserManagerComponent, canActivate: [AuthGuardService]},
  {path: 'user-detail-list', component: UserDetailsComponent, canActivate: [AuthGuardService]},
  {path: 'error', component: ErrorComponent},
  {path: 'pm', component: PmMainComponent, canActivate: [AuthGuardService]},
  {path: 'userManagerTestMenu', component: UserManagerSidenemuComponent, canActivate: [AuthGuardService]},
  {path: '**', redirectTo: 'login'},
  {path: '', component: AppComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}