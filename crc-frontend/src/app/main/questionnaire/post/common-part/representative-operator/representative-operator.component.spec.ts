import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepresentativeOperatorComponent } from './representative-operator.component';

describe('RepresentativeOperatorComponent', () => {
  let component: RepresentativeOperatorComponent;
  let fixture: ComponentFixture<RepresentativeOperatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepresentativeOperatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepresentativeOperatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
