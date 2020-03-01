import { Component, OnInit } from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {Router} from '@angular/router';
import {Credentials} from '../model/Credentials';

@Component({
  selector: 'app-file-credentials',
  templateUrl: './file-credentials.component.html',
  styleUrls: ['./file-credentials.component.sass']
})
export class FileCredentialsComponent implements OnInit {
  selectedFileCredentials: Credentials = null;
  private isEdit = false;

  constructor(private fileExplorerService: FileExplorerService, private router: Router) { }

  ngOnInit() {
    this.selectedFileCredentials = this.fileExplorerService.getEditedFileCredentials();
    if (this.selectedFileCredentials === null || this.selectedFileCredentials === undefined) {
      this.router.navigateByUrl('/file-explorer');
    }
  }

}
