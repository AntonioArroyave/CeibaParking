import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { VehiculoService } from './shared/vehiculo/vehiculo.service';
import {AddDialogComponent} from './dialogs/add/add.dialog.component';
import {EditDialogComponent} from './dialogs/edit/edit.dialog.component';
import {DeleteDialogComponent} from './dialogs/delete/delete.dialog.component';
import {FacturarDialogComponent} from './dialogs/facturar/facturar.dialog.component';
import { HttpClientModule } from '@angular/common/http'
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { VehiculoListComponent } from './vehiculo-list/vehiculo-list.component';
import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule, MatPaginatorModule, MatProgressSpinnerModule, 
         MatSortModule, MatTableModule, MatIconModule, MatDialogModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule } from '@angular/material';
import { VehiculoEditComponent } from './vehiculo-edit/vehiculo-edit.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';


const appRoutes: Routes = [
  { path: '', redirectTo: '/vehiculo-list', pathMatch: 'full' },
  {
    path: 'vehiculo-list',
    component: VehiculoListComponent
  },
  {
    path: 'vehiculo-add',
    component: VehiculoEditComponent
  },
  {
    path: 'vehiculo-edit/:id',
    component: VehiculoEditComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    VehiculoListComponent,
    VehiculoEditComponent,
    AddDialogComponent,
    EditDialogComponent,
    DeleteDialogComponent,
    FacturarDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatDialogModule,
    MatSelectModule,
    MatNativeDateModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes)
  ],
    entryComponents: [
    AddDialogComponent,
    EditDialogComponent,
    DeleteDialogComponent,
    FacturarDialogComponent
  ],
  providers: [VehiculoService],
  bootstrap: [AppComponent]
})
export class AppModule { }
