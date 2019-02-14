import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionA7Component } from './section-a7.component';

describe('SectionA7Component', () => {
  let component: SectionA7Component;
  let fixture: ComponentFixture<SectionA7Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionA7Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionA7Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
