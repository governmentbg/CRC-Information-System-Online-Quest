import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdentityDataComponent } from './identity-data.component';

describe('IdentityDataComponent', () => {
  let component: IdentityDataComponent;
  let fixture: ComponentFixture<IdentityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdentityDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdentityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
