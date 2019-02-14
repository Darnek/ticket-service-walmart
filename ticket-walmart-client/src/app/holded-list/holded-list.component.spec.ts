import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HoldedListComponent } from './holded-list.component';

describe('HoldedListComponent', () => {
  let component: HoldedListComponent;
  let fixture: ComponentFixture<HoldedListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HoldedListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HoldedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
