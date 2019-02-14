import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeSercretComponent } from './trade-sercret.component';

describe('TradeSercretComponent', () => {
  let component: TradeSercretComponent;
  let fixture: ComponentFixture<TradeSercretComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TradeSercretComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TradeSercretComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
