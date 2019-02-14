import { Component, OnInit, NgModule } from '@angular/core';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

@Component({
  selector: 'app-trade-sercret',
  templateUrl: './trade-sercret.component.html',
  styleUrls: ['./trade-sercret.component.scss']
})
export class TradeSercretComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}

@NgModule({
  declarations: [TradeSercretComponent],
  imports: [
    SharedMaterialModule
  ],
  exports: [
    TradeSercretComponent
  ]
})
export class TradeSercretModule {

}
