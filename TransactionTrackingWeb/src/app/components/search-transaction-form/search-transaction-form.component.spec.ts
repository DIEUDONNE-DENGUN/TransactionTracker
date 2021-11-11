import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchTransactionFormComponent } from './search-transaction-form.component';

describe('SearchTransactionFormComponent', () => {
  let component: SearchTransactionFormComponent;
  let fixture: ComponentFixture<SearchTransactionFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchTransactionFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchTransactionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
