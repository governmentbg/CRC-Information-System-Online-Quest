import { Component, OnInit, NgModule, Input } from '@angular/core';
import { SharedMaterialModule } from 'app/shared/shared-material.module';
import { Company } from 'app/model/company';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-company-card',
  templateUrl: './company-card.component.html',
  styleUrls: ['./company-card.component.scss']
})
export class CompanyCardComponent implements OnInit {

  @Input() public company: Company;

  constructor() { }

  ngOnInit() {
  }

}

@NgModule({
  declarations: [CompanyCardComponent],
  imports: [
    SharedMaterialModule,
    RouterModule
  ],
  exports: [CompanyCardComponent]
})
export class ComapnyCardModule {}
