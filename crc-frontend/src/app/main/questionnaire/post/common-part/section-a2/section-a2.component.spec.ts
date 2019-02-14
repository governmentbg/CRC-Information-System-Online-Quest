import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionA2Component } from './section-a2.component';

describe('SectionA2Component', () => {
  let component: SectionA2Component;
  let fixture: ComponentFixture<SectionA2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionA2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionA2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
