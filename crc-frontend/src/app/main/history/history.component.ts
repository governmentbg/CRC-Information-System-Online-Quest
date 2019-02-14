import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { OperatorService } from 'app/main/user/operator-administration/operator-dialog/operator.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {

  displayedColumns: string[] = [
    'entryNumber',
    'createdOn',
    'bulstat',
    'operator',
    'questionnaire',
    'status',
    'action'
  ];

  dataSource: any[] = [
    { entryNumber: '1234', createdOn: '2019-01-01', bulstat: '12345', questionnaire: 'Годишен', status: 'Чернова' }
  ];

  searchPanelForm: FormGroup;
  currentUserToken: any;
  currentUser: any;
  selectedValue: any;
  userCompanies: any[] = [];
  companyIDs: Number[] = [];

  constructor(
    private _formBuilder: FormBuilder,
    private _operatorService: OperatorService
  ) { }

  ngOnInit() {
    this.currentUserToken = JSON.parse(localStorage.getItem('currentUser'));
    this.currentUser = JSON.parse(localStorage.getItem('user'));

    this.searchPanelForm = this._formBuilder.group({
      entryNumber: [],
      status: [],
      yearFrom: [],
      yearTo: []
    });

    this.getUserCompanies(this.currentUser.companyToServe);
    this.getCompanyIDs(this.currentUser.companyToServe);
  }


  getUserCompanies(companyToServe) {
    companyToServe.forEach((cmp, index) => {
      this._operatorService.getCompanyById(cmp, this.currentUserToken.accessToken).subscribe(company => {
        if (index === 0) {
          this.selectedValue = company.name;
        };
        this.userCompanies.push(company);
      });
    });
  }

  getCompanyIDs(companyToServe) {
    companyToServe.forEach(companyID => {
      this.companyIDs.push(companyID);
    });
  }
}