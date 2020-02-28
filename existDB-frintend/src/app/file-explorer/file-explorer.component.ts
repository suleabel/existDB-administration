import { Component, OnInit } from '@angular/core';
import {FileExplorerService} from './service/file-explorer.service';

export interface Content {
  name: string;
  isResource: boolean;
}

@Component({
  selector: 'app-file-explorer',
  templateUrl: './file-explorer.component.html',
  styleUrls: ['./file-explorer.component.sass']
})
export class FileExplorerComponent implements OnInit {
  public selectedDirectory = '/db';
  public collections: Content[];
  public displayedColumns: string[] = ['name', 'resource', 'delete', 'view'];

  constructor(private fileExplorerService: FileExplorerService) { }

  ngOnInit() {
    this.loadData(this.selectedDirectory);
  }

  loadData(path: string) {
    this.fileExplorerService.getCollection(path)
        .subscribe(
            res => {
              const backElement = {name: '..', isResource: false};
              this.collections = res;
              this.collections.unshift(backElement);
              // this.collections.push(backElement);
              console.log(this.collections);
            },
            error => {
              console.log(error);
            });
  }

  aClick(col: string) {
    if (col === '..') {
      this.back();
    } else {
      this.selectedDirectory = this.selectedDirectory + '/' + col;
      this.loadData(this.selectedDirectory);
    }
  }
  back() {
    console.log(this.selectedDirectory);
    const separatedString: string[] = this.selectedDirectory.split('/');
    separatedString.length = separatedString.length - 1;
    this.selectedDirectory = separatedString.join('/');
    this.loadData(this.selectedDirectory);
  }

  backToRoot() {
    this.selectedDirectory = '/db';
    this.loadData(this.selectedDirectory);
  }

  delete(resName: string) {
    console.log('selectedDor: ' + this.selectedDirectory + ' selected resource: ' + resName);
  }
  view(resName: string) {
    console.log('selectedDor: ' + this.selectedDirectory + ' selected resource: ' + resName);
  }

}
