import {Component, Input, NgModule, OnInit} from '@angular/core';
import {TableModule} from '../table/table.component';
import {MatStepperModule, MatTableModule, MatTreeModule, MatExpansionModule} from '@angular/material';
import {CommonModule} from '@angular/common';
import { TradeSercretModule } from '../trade-sercret/trade-sercret.component';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss']
})
export class GroupComponent implements OnInit {

  @Input() public name: string;

  @Input() public codeBase: string;

  @Input() public id: string;

  @Input() public isLinear: boolean;

  @Input() public tables: Array<any>[];

  constructor() { }

  ngOnInit() { }
} 

@NgModule({
  declarations: [GroupComponent],
  imports: [MatTableModule, TableModule, TradeSercretModule, CommonModule, MatExpansionModule],
  exports: [GroupComponent]
})
export class GroupModule {
}

