import { Component, OnInit } from '@angular/core';
import { AnnoncesComponent } from '../annonces/annonces.component';
@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone:false,
})
export class HomePage implements OnInit {

  constructor() { }

  ngOnInit() {
  }
  deconnexion(){}
  contact(){}
  get_photo(){}
}
