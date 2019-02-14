import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PppComponent } from './ppp.component';

describe('PppComponent', () => {
  let component: PppComponent;
  let fixture: ComponentFixture<PppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PppComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
