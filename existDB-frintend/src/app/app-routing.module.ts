import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginPageComponent} from './auth-module/login-page/login-page.component';
import {HomePageComponent} from './home-page-module/home-page/home-page.component';
import {UserManagerComponent} from './user-manager-module/user-manager.component';
import {AuthGuardService} from './auth-module/auth-guard.service';
import {AppComponent} from './app.component';
import {ErrorComponent} from './errors-module/error/error.component';
import {ExistUsersEditDetailsComponent} from './user-manager-module/exist-users-edit-details/exist-users-edit-details.component';
import {ExistGroupManagerComponent} from './exist-group-manager/exist-group-manager.component';
import {ExistGroupDetailsComponent} from './exist-group-manager/exist-group-details/exist-group-details.component';
import {XmlToXsdComponent} from './xml-to-xsd/xml-to-xsd.component';
import {CreateXqueryComponent} from './collection-manager/create-xquery/create-xquery.component';
import {FileCredentialsComponent} from './collection-manager/file-credentials/file-credentials.component';
import {TriggersManagerComponent} from './triggers-manager/triggers-manager.component';
import {VersionManagementModuleComponent} from './version-management-module/version-management-module.component';
import {CollectionManagerComponent} from './collection-manager/collection-manager.component';
import {FileManagerComponent} from './file-manager/file-manager.component';
import {BackupAndRestoreModuleComponent} from './backup-and-restore-moduel/backup-and-restore-module.component';
import {CreateNewResourceComponent} from './collection-manager/create-new-resource/create-new-resource.component';
import {ViewManagerComponent} from './view-manager/view-manager.component';

const routes: Routes = [
  {path: 'login', component: LoginPageComponent},
  {path: 'home', component: HomePageComponent, canActivate: [AuthGuardService]},
  {path: 'user-manager-module', component: UserManagerComponent, canActivate: [AuthGuardService]},
  {path: 'exist-group-manager', component: ExistGroupManagerComponent, canActivate: [AuthGuardService]},
  {path: 'exist-user-edit-details', component: ExistUsersEditDetailsComponent, canActivate: [AuthGuardService]},
  {path: 'exist-group-edit-details', component: ExistGroupDetailsComponent, canActivate: [AuthGuardService]},
  {path: 'error', component: ErrorComponent},
  {path: 'xml2xsd', component: XmlToXsdComponent, canActivate: [AuthGuardService]},
  {path: 'create-xquery', component: CreateXqueryComponent, canActivate: [AuthGuardService]},
  {path: 'file-credentials', component: FileCredentialsComponent, canActivate: [AuthGuardService]},
  {path: 'trigger-manager', component: TriggersManagerComponent, canActivate: [AuthGuardService]},
  {path: 'version-management' , component: VersionManagementModuleComponent, canActivate: [AuthGuardService]},
  {path: 'collection-manager', component: CollectionManagerComponent, canActivate: [AuthGuardService]},
  {path: 'file_manager-component', component: FileManagerComponent, canActivate: [AuthGuardService]},
  {path: 'backup-and-restore-module', component: BackupAndRestoreModuleComponent, canActivate: [AuthGuardService]},
  {path: 'create-new-resource', component: CreateNewResourceComponent, canActivate: [AuthGuardService]},
  {path: 'view-manager', component: ViewManagerComponent, canActivate: [AuthGuardService]},
  {path: '**', redirectTo: 'login'},
  {path: '', component: AppComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
