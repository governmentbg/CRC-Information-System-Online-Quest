import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionA6Component } from './section-a6.component';

describe('SectionA6Component', () => {
  let component: SectionA6Component;
  let fixture: ComponentFixture<SectionA6Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionA6Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionA6Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
