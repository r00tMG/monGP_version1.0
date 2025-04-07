import { Component, OnInit } from '@angular/core';
import { FormControl,FormGroup, Validators } from '@angular/forms';
import { IonSelect,IonList,IonSelectOption,IonItem } from '@ionic/angular/standalone';
@Component({
  selector: 'app-annonces',
  templateUrl: './annonces.page.html',
  styleUrls: ['./annonces.page.scss'],
  standalone:false,
 
})
export class AnnoncesPage implements OnInit {
 a=[];
 constructor() { 
  this.datas();
 }
myGroup=new FormGroup({
  depart: new FormControl('',Validators.required,),
  arrivee: new FormControl('',Validators.required),
  origine: new FormControl('',Validators.required),
  dest: new FormControl('',Validators.required),
});
  ngOnInit() {
  }
datas(){
  if(this.myGroup.value.depart!=''){
    console.log(this.myGroup.value);
  }else console.log("Veuillez remplir ce champ");
 
}
}
