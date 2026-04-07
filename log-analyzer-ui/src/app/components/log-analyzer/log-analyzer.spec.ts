import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogAnalyzer } from './log-analyzer';

describe('LogAnalyzer', () => {
  let component: LogAnalyzer;
  let fixture: ComponentFixture<LogAnalyzer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LogAnalyzer],
    }).compileComponents();

    fixture = TestBed.createComponent(LogAnalyzer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
