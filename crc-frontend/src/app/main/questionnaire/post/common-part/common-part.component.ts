import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CommonPartService} from './common-part.service';
import {HttpClient} from '@angular/common/http';


const ELEMENT_DATA: any[] = [
  { name: '1. Общ размер на приходите в (лв., без ДДС)', input: 1.0079 },
  { name: '2. Общ размер на разходите (лв.)', input: 1.0079 },
  { name: '3. Общ финансов резултат на дружеството в (лв., без ДДС). (т.1 - т.2)', input: 1.0079 },
];

const EMPLOYEES_DATA: any[] = [
  { name: '1.1. Общ брой служители заети на пълен работен ден', upu: 0, ppp: 0, npu: 0 },
  { name: '1.2. Общо брой служители заети на непълен работен ден', upu: 0, ppp: 0, npu: 0 },
  { name: '1.3. Временно наети служители', upu: 0, ppp: 0, npu: 0 },
  { name: '1.4. Самостоятелно заети лица', upu: 0, ppp: 0, npu: 0 },
  { name: '1.5. Общо (т.1.1. + т.1.2. + т.1.3. + т.1.4.)', upu: 0, ppp: 0, npu: 0, color: 'rgba(105, 108, 112, 0.2)' },
];



@Component({
  selector: 'app-common-part',
  templateUrl: './common-part.component.html',
  styleUrls: ['./common-part.component.scss']
})
export class CommonPartComponent implements OnInit {

  displayedColumns: string[] = ['name', 'input'];
  displayedTableColumns: any[] = [
    'name',
    'upu',
    'ppp',
    'npu'
  ];

  dataSource = ELEMENT_DATA;
  postPersonDataSource: any[] = [];
  investmentDataSource: any[] = [];
  postNtworkDataSource: any[] = [];

  isLinear = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  postOperatorAddressFormGroup: FormGroup;
  commanPartContactPersonFormGroup: FormGroup;
  financeFormGroup: FormGroup;
    postNetworkFormGroup: FormGroup;
  postSecurityFormGroup: FormGroup;

  dummyJson: any;

  constructor(private _formBuilder: FormBuilder, private httpClient: HttpClient, private commonPartService: CommonPartService) { 
  }

  ngOnInit() {
   

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.postOperatorAddressFormGroup = this._formBuilder.group({

    });
    this.commanPartContactPersonFormGroup = this._formBuilder.group({

    });

    this.financeFormGroup = this._formBuilder.group({

    });
    this.postNetworkFormGroup = this._formBuilder.group({

    });

    this.postSecurityFormGroup = this._formBuilder.group({

    });
  }

}
