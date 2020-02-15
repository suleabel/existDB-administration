import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-checkbox-test',
  templateUrl: './checkbox-test.component.html',
  styleUrls: ['./checkbox-test.component.sass']
})
export class CheckboxTestComponent implements OnInit {
  musicForm: FormGroup;
  musicPreferences = [
    { id: 'user', genre: 'Pop' },
    { id: 'admin', genre: 'Rock' },
    { id: 'pm', genre: 'Techno' }
  ];

  constructor(private fb: FormBuilder) {
    // Create a FormControl for each available music preference, initialize them as unchecked, and put them in an array
    const formControls = this.musicPreferences.map(control => new FormControl(false));
    // Simply add the list of FormControls to the FormGroup as a FormArray, add the selectAllControl separetely
    this.musicForm = this.fb.group({
      musicPreferences: new FormArray(formControls)
    });
  }

  ngOnInit() {
  }


  submit() {
    // Filter out the unselected ids
    const selectedPreferences = this.musicForm.value.musicPreferences
      .map((checked, index) => checked ? this.musicPreferences[index].id : null)
      .filter(value => value !== null);
    // Do something with the result
    console.log(selectedPreferences);
  }
}
