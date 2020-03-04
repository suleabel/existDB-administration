import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HomePageComponent} from './home-page-module/home-page/home-page.component';
import {LoginPageComponent} from './auth-module/login-page/login-page.component';
import {TopBarComponent} from './top-bar-module/top-bar.component';
import {HttpClientModule} from '@angular/common/http';
import {HighlightModule} from 'ngx-highlightjs';
import {CommonModule} from '@angular/common';
import {
    _MatMenuDirectivesModule, MatButtonModule,
    MatCardModule, MatCheckboxModule,
    MatInputModule, MatListModule, MatMenuModule,
    MatPaginatorModule, MatSidenavModule,
    MatSortModule,
    MatTableModule,
    MatToolbarModule, MatIconModule, MatSelectModule, MatDialogModule,
} from '@angular/material';

import {MatSnackBarModule} from '@angular/material/snack-bar';


import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxEditorModule } from 'ngx-editor';
import { TargyakListaComponent } from './targyak-module/targyak-lista/targyak-lista.component';
import { UserManagerComponent } from './user-manager-module/user-manager.component';
import {httpInterceptorProvider} from './auth-module/auth-interceptor';
import {AuthGuardService} from './auth-module/auth-guard.service';
import { ErrorComponent } from './errors-module/error/error.component';
import { LayoutModule } from '@angular/cdk/layout';
import { ExistUsersListComponent } from './user-manager-module/exist-users-list/exist-users-list.component';
import { ExistUsersEditDetailsComponent } from './user-manager-module/exist-users-edit-details/exist-users-edit-details.component';
import { ExistGroupListComponent } from './exist-group-manager/exist-group-list/exist-group-list.component';
import { ExistAddUserComponent } from './user-manager-module/exist-add-user/exist-add-user.component';
import { ExistGroupManagerComponent } from './exist-group-manager/exist-group-manager.component';
import { ExistAddGroupComponent } from './exist-group-manager/exist-add-group/exist-add-group.component';
import { ExistGroupDetailsComponent } from './exist-group-manager/exist-group-details/exist-group-details.component';
import {XmlToXsdComponent} from './xml-to-xsd/xml-to-xsd.component';
import { DialogPanelComponent } from './xml-to-xsd/dialog-panel/dialog-panel.component';
import { FileExplorerComponent } from './file-explorer/file-explorer.component';
import { CreateFileComponent } from './file-explorer/create-file/create-file.component';
import { FileCredentialsComponent } from './file-explorer/file-credentials/file-credentials.component';
import { TriggersManagerComponent } from './triggers-manager/triggers-manager.component';
import { CollectionsDialogComponent } from './triggers-manager/collections-dialog/collections-dialog.component';
import { CreateDirDialogComponent } from './file-explorer/create-dir-dialog/create-dir-dialog.component';
import { FileViewerDialogComponent } from './file-explorer/file-viewer-dialog/file-viewer-dialog.component';
import { ConfirmDialogComponent } from './error-dialog/confirm-dialog.component';
import { XmlFileViewerComponent } from './triggers-manager/xml-file-viewer/xml-file-viewer.component';
import { AddTriggerComponent } from './triggers-manager/add-trigger/add-trigger.component';
@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    LoginPageComponent,
    TopBarComponent,
    TargyakListaComponent,
    UserManagerComponent,
    ErrorComponent,
    ExistUsersListComponent,
    ExistUsersEditDetailsComponent,
    ExistGroupListComponent,
    ExistGroupListComponent,
    ExistAddUserComponent,
    ExistGroupManagerComponent,
    ExistAddGroupComponent,
    ExistGroupDetailsComponent,
      XmlToXsdComponent,
      DialogPanelComponent,
      FileExplorerComponent,
      CreateFileComponent,
      FileCredentialsComponent,
      TriggersManagerComponent,
      CollectionsDialogComponent,
      CreateDirDialogComponent,
      FileViewerDialogComponent,
      ConfirmDialogComponent,
      XmlFileViewerComponent,
      AddTriggerComponent,
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
        MatIconModule,
        MatSelectModule,
        HighlightModule,
        MatDialogModule,
        MatSnackBarModule
    ],
  providers: [httpInterceptorProvider, AuthGuardService],
  bootstrap: [AppComponent],
    entryComponents: [
        CollectionsDialogComponent,
        CreateDirDialogComponent,
        FileViewerDialogComponent,
        ConfirmDialogComponent,
        XmlFileViewerComponent,
        AddTriggerComponent]
})
export class AppModule {
}
