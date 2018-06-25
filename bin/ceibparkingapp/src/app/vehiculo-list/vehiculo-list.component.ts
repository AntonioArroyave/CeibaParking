import { Component, OnInit } from '@angular/core';
import { VehiculoService } from '../shared/vehiculo/vehiculo.service';

@Component({
  selector: 'app-vehiculo-list',
  templateUrl: './vehiculo-list.component.html',
  styleUrls: ['./vehiculo-list.component.css']
})
export class VehiculoListComponent implements OnInit {

  vehiculos: Array<any>;
  displayedColumns = ["Placa", "Tipo", "FechaIngreso"];
  constructor(private vehiculoService: VehiculoService) { }

  ngOnInit() {
//    this.vehiculoService.getAll().subscribe(data => {
//    	this.vehiculos= data;
//	});
  }

}
