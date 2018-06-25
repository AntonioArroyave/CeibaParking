import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {VehiculoService} from './shared/vehiculo/vehiculo.service';
import {HttpClient} from '@angular/common/http';
import {MatDialog, MatPaginator, MatSort, MatDialogConfig} from '@angular/material';
import {Vehiculos} from './modelos/vehiculos';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {DataSource} from '@angular/cdk/collections';
import 'rxjs/add/observable/merge';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {AddDialogComponent} from './dialogs/add/add.dialog.component';
import {EditDialogComponent} from './dialogs/edit/edit.dialog.component';
import {DeleteDialogComponent} from './dialogs/delete/delete.dialog.component';
import {FacturarDialogComponent} from './dialogs/facturar/facturar.dialog.component';
import {Factura} from './modelos/factura';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  displayedColumns = ["Placa", "Tipo", "FechaIngreso", 'actions'];
  exampleDatabase: VehiculoService | null;
  dataSource: ExampleDataSource | null;
  id: number;
  placa: string;
  facturaDialog: MatDialog;
  factura : Factura;
  TRM: any;
 
  constructor(public httpClient: HttpClient,
              public dialog: MatDialog,
              public dataService: VehiculoService) {}
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('filter') filter: ElementRef

    ngOnInit() {
    this.loadData();
  }
  
    refresh() {
    this.loadData();
    this.TRM=this.dataService.getTRM();
    console.log(this.TRM);
  }
  
    addNew(vehiculo: Vehiculos) {
    const dialogRef = this.dialog.open(AddDialogComponent, {
      data: {vehiculo: vehiculo }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // After dialog is closed we're doing frontend updates
        // For add we're just pushing a new row inside DataService
        console.log(this.dataService.getDialogData());
        this.exampleDatabase.addVehiculo(this.dataService.getDialogData());
        //this.exampleDatabase.dataChange.value.push(this.dataService.getDialogData());
        //this.refreshTable();
        this.refresh();
      }
    });
  }
  
   startEdit(placa: string, tipoVehiculo: string, FechaIngreso: string) {
    this.placa = placa;
    // index row is used just for debugging proposes and can be removed
    console.log(this.placa);
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: {palca: placa, tipoVehiculo: tipoVehiculo, FechaIngreso: FechaIngreso}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // When using an edit things are little different, firstly we find record inside DataService by id
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === this.id);
        console.log(this.dataService.getDialogData());
        this.exampleDatabase.updateVehiculo(this.dataService.getDialogData());
        // Then you update that record using data from dialogData (values you enetered)
       // this.exampleDatabase.dataChange.value[foundIndex] = this.dataService.getDialogData();
        // And lastly refresh table
        //this.refreshTable();
        this.refresh();
      }
    });
  }
  
    deleteItem(placa: string, tipoVehiculo: string, FechaIngreso: string) {
    this.placa = placa;
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: {placa: placa, tipoVehiculo: tipoVehiculo, FechaIngreso: FechaIngreso}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === this.id);
        // for delete we use splice in order to remove single object from DataService
        //this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
        //this.refreshTable();
        this.factura = this.dataService.deleteVehiculo(this.dataService.getDialogData());
        console.log("Factura: "+ this.factura.fechaEntrada);
        this.mostrarFactura(this.factura);
        this.refresh();
      }
     });

    }
  
    mostrarFactura(factura:Factura): any {
      console.log(this.factura);
     const dialogRef = this.dialog.open(FacturarDialogComponent, {
       data: {placa: this.factura.placa, fechaEntrada: this.factura.fechaEntrada, fechaSalida: this.factura.fechaSalida,
          totalHoras: this.factura.totalHoras, totalPagar: this.factura.totalPagar}
      } );
          dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === this.id);
        // for delete we use splice in order to remove single object from DataService
        //this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
        //this.refreshTable();
        this.refresh();
      }
    });
  }
  
    private refreshTable() {
    // if there's a paginator active we're using it for refresh
    if (this.dataSource._paginator.hasNextPage()) {
      this.dataSource._paginator.nextPage();
      this.dataSource._paginator.previousPage();
      // in case we're on last page this if will tick
    } else if (this.dataSource._paginator.hasPreviousPage()) {
      this.dataSource._paginator.previousPage();
      this.dataSource._paginator.nextPage();
      // in all other cases including active filter we do it like this
    } else {
      this.dataSource.filter = '';
      this.dataSource.filter = this.filter.nativeElement.value;
    }
  }
  
    public loadData() {
    this.exampleDatabase = new VehiculoService(this.httpClient, this.facturaDialog);
    this.dataSource = new ExampleDataSource(this.exampleDatabase, this.paginator, this.sort);
    Observable.fromEvent(this.filter.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        if (!this.dataSource) {
          return;
        }
        this.dataSource.filter = this.filter.nativeElement.value;
      });
  }
}

export class ExampleDataSource extends DataSource<Vehiculos> {
  _filterChange = new BehaviorSubject('');

  get filter(): string {
    return this._filterChange.value;
  }

  set filter(filter: string) {
    this._filterChange.next(filter);
  }

  filteredData: Vehiculos[] = [];
  renderedData: Vehiculos[] = [];

  constructor(public _exampleDatabase: VehiculoService,
              public _paginator: MatPaginator,
              public _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Vehiculos[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page
    ];

    this._exampleDatabase.getAllVehiculos();

    return Observable.merge(...displayDataChanges).map(() => {
      // Filter data
      this.filteredData = this._exampleDatabase.data.slice().filter((vehiculo: Vehiculos) => {
        const searchStr = (vehiculo.placa + vehiculo.TipoVehiculo + vehiculo.FechaIngreso).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
      });

      // Sort filtered data
      const sortedData = this.sortData(this.filteredData.slice());

      // Grab the page's slice of the filtered sorted data.
      const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
      this.renderedData = sortedData.splice(startIndex, this._paginator.pageSize);
      return this.renderedData;
    });
  }
  disconnect() {
  }

  /** Returns a sorted copy of the database data. */
  sortData(data: Vehiculos[]): Vehiculos[] {
    if (!this._sort.active || this._sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'id': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'placa': [propertyA, propertyB] = [a.placa, b.placa]; break;
        case 'tipoVehiculo': [propertyA, propertyB] = [a.TipoVehiculo, b.TipoVehiculo]; break;
        case 'FechaIngreso': [propertyA, propertyB] = [a.FechaIngreso, b.FechaIngreso]; break;
        case 'url': [propertyA, propertyB] = [a.url, b.url]; break;
      }

      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction === 'asc' ? 1 : -1);
    });
  }
}
