import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';
import {VehiculoService} from '../../shared/vehiculo/vehiculo.service';
import {FormControl, Validators} from '@angular/forms';
import {Vehiculos} from '../../modelos/vehiculos';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-add.dialog',
  templateUrl: '../../dialogs/add/add.dialog.html',
  styleUrls: ['../../dialogs/add/add.dialog.css']
})

export class AddDialogComponent {
  vehiculo: any= {};
  sub: Subscription;
  tipos = [
    {value: 'Carro', viewValue: 'Carro'},
    {value: 'Moto', viewValue: 'Moto'},
  ];
  
  constructor(public dialogRef: MatDialogRef<AddDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Vehiculos,
              public dataService: VehiculoService) { }

  formControl = new FormControl('', [
    Validators.required
    // Validators.email,
  ]);

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Required field' :
      this.formControl.hasError('email') ? 'Not a valid email' :
        '';
  }

  submit(ngForm) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  public confirmAdd(): void {
    this.dataService.addVehiculo(this.data);
  }
}
