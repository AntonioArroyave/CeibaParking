import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';



@Component({
  selector: 'app-delete.dialog',
  templateUrl: '../../dialogs/facturar/facturar.dialog.html',
  styleUrls: ['../../dialogs/facturar/facturar.dialog.css']
})
export class FacturarDialogComponent {

  constructor(public dialogRef: MatDialogRef<FacturarDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  confirmDelete(): void {
  }
}
