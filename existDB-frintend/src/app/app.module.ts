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
import {CommonModule, HashLocationStrategy, LocationStrategy} from '@angular/common';
import {
    _MatMenuDirectivesModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatPaginatorModule,
    MatSidenavModule,
    MatSortModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    MatTooltipModule,
} from '@angular/material';

import {MatSnackBarModule} from '@angular/material/snack-bar';


import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxEditorModule } from 'ngx-editor';
import { UserManagerComponent } from './user-manager-module/user-manager.component';
import {httpInterceptorProvider} from './auth-module/auth-interceptor';
import {AuthGuardService} from './auth-module/auth-guard.service';
import { ErrorPageComponent } from './error-notification-module/error-page/error-page.component';
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
import { CreateXqueryComponent } from './collection-manager/create-xquery/create-xquery.component';
import { FileCredentialsComponent } from './collection-manager/file-credentials/file-credentials.component';
import { TriggersManagerComponent } from './triggers-manager/triggers-manager.component';
import { CollectionsDialogComponent } from './triggers-manager/collections-dialog/collections-dialog.component';
import { CreateDirDialogComponent } from './collection-manager/create-dir-dialog/create-dir-dialog.component';
import { ResourceViewerDialogComponent } from './collection-manager/resource-viewer-dialog/resource-viewer-dialog.component';
import { ConfirmDialogComponent } from './error-notification-module/confirm-dialog/confirm-dialog.component';
import { XmlFileViewerComponent } from './triggers-manager/xml-file-viewer/xml-file-viewer.component';
import { AddTriggerComponent } from './triggers-manager/add-trigger/add-trigger.component';
import { VersionManagementModuleComponent } from './version-management-module/version-management-module.component';
import { BrowseSaveLocationComponent } from './xml-to-xsd/browse-save-location/browse-save-location.component';
import { ViewHistoryComponent } from './version-management-module/view-history/view-history.component';
import { CollectionManagerComponent } from './collection-manager/collection-manager.component';
import {FileManagerComponent} from './file-manager/file-manager.component';
import { FileViewerDialogComponent } from './file-manager/file-viewer-dialog/file-viewer-dialog.component';
import {BackupAndRestoreModuleComponent} from './backup-and-restore-moduel/backup-and-restore-module.component';
import { CreateNewResourceComponent } from './collection-manager/create-new-resource/create-new-resource.component';
import { InformationDialogComponent } from './backup-and-restore-moduel/information-dialog/information-dialog.component';
import { BrowseXqueryFileComponent } from './triggers-manager/browse-xquery-file/browse-xquery-file.component';
import { EvalResultViewerComponent } from './collection-manager/eval-result-viewer/eval-result-viewer.component';
import { ErrorDialogComponent } from './error-notification-module/error-dialog/error-dialog.component';
import { FooterComponent } from './footer/footer.component';
import {TypeaheadModule} from 'ngx-bootstrap';
import { ViewChangesDialogComponent } from './version-management-module/view-changes-dialog/view-changes-dialog.component';
import { MakeDirDialogComponent } from './file-manager/make-dir-dialog/make-dir-dialog.component';
import { CreateNewFileComponent } from './file-manager/create-new-file/create-new-file.component';
import { ViewManagerComponent } from './view-manager/view-manager.component';
import { QueryResultDialogComponent } from './error-notification-module/query-result-dialog/query-result-dialog.component';
@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    LoginPageComponent,
    TopBarComponent,
    UserManagerComponent,
    ErrorPageComponent,
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
      CreateXqueryComponent,
      FileCredentialsComponent,
      TriggersManagerComponent,
      CollectionsDialogComponent,
      CreateDirDialogComponent,
      ResourceViewerDialogComponent,
      ConfirmDialogComponent,
      XmlFileViewerComponent,
      AddTriggerComponent,
      VersionManagementModuleComponent,
      BrowseSaveLocationComponent,
      ViewHistoryComponent,
      CollectionManagerComponent,
      FileManagerComponent,
      FileViewerDialogComponent,
      BackupAndRestoreModuleComponent,
      CreateNewResourceComponent,
      InformationDialogComponent,
      BrowseXqueryFileComponent,
      EvalResultViewerComponent,
      ErrorDialogComponent,
      FooterComponent,
      ViewChangesDialogComponent,
      MakeDirDialogComponent,
      CreateNewFileComponent,
      ViewManagerComponent,
      QueryResultDialogComponent,
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
        MatSnackBarModule,
        MatProgressSpinnerModule,
        MatTabsModule,
        TypeaheadModule,
        MatTooltipModule
    ],
  providers: [
      httpInterceptorProvider,
      AuthGuardService,
      {provide: LocationStrategy, useClass: HashLocationStrategy}
      ],
  bootstrap: [AppComponent],
    entryComponents: [
        CollectionsDialogComponent,
        CreateDirDialogComponent,
        ResourceViewerDialogComponent,
        ConfirmDialogComponent,
        XmlFileViewerComponent,
        AddTriggerComponent,
        DialogPanelComponent,
        BrowseSaveLocationComponent,
        ExistAddUserComponent,
        ViewHistoryComponent,
        FileViewerDialogComponent,
        CreateNewResourceComponent,
        CreateXqueryComponent,
        FileCredentialsComponent,
        ExistAddGroupComponent,
        InformationDialogComponent,
        BrowseXqueryFileComponent,
        EvalResultViewerComponent,
        ErrorDialogComponent,
        ViewChangesDialogComponent,
        MakeDirDialogComponent,
        CreateNewFileComponent,
        QueryResultDialogComponent
    ]
})
export class AppModule {
}
