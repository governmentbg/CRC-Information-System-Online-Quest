import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpuComponent } from './upu.component';

describe('UpuComponent', () => {
  let component: UpuComponent;
  let fixture: ComponentFixture<UpuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
