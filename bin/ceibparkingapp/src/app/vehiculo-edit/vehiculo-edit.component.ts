import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { VehiculoService } from '../shared/vehiculo/vehiculo.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-vehiculo-edit',
  templateUrl: './vehiculo-edit.component.html',
  styleUrls: ['./vehiculo-edit.component.css']
})
export class VehiculoEditComponent implements OnInit, OnDestroy {
	vehiculo: any= {};
	sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
	      private vehiculoService: VehiculoService) {
  }

  ngOnInit() {
     this.sub = this.route.params.subscribe(
//       params => {
//      const placa = params['placa'];
//      if (placa) {
//        this.vehiculoService.get(placa).subscribe((vehiculo: any) => {
//          if (vehiculo) {
//            this.vehiculo = vehiculo;
//            this.vehiculo.href = vehiculo._links.self.href;
//          } else {
//            console.log(`Vehiculo con placa'${placa}' no encontrado, retornando a la lista`);
//            this.gotoList();
//          }
//        });
//      }
//    }
     );
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/vehiculo-list']);
  }

//  save(form: NgForm) {
//    this.vehiculoService.addVehiculo(form).subscribe(result => {
//      this.gotoList();
//    }, error => console.error(error));
//  }
//
//  remove(href) {
//    this.vehiculoService.remove(href).subscribe(result => {
//      this.gotoList();
//    }, error => console.error(error));
//  }  
}
