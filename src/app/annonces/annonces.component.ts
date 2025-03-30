import { Component } from '@angular/core';
import { FormGroup,FormControl } from '@angular/forms';
@Component({
  selector: 'app-annonces',
  templateUrl: './annonces.component.html',
  styleUrls: ['./annonces.component.scss'],
})
export class AnnoncesComponent {

  constructor() { }
  profileForm = new FormGroup({
    depart: new FormControl(''),
    arrivee: new FormControl(''),
    origine: new FormControl(''),
    dest: new FormControl(''),
  });

}
