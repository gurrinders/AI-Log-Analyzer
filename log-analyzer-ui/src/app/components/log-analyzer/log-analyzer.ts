import { Component, NgZone, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LogService, LogAnalysisResult } from '../../services/log.service';

@Component({
  selector: 'app-log-analyzer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './log-analyzer.html',
  styleUrl: './log-analyzer.css',
})
export class LogAnalyzer {
  logs: string = '';
  result?: LogAnalysisResult;
  results: LogAnalysisResult[] = [];
  loading = false;

  constructor(private logService: LogService, private ngZone: NgZone, private cdr: ChangeDetectorRef) {}

  analyze() {
    console.log('Starting analysis...');
    this.loading = true;

    this.logService.analyzeLogs(this.logs).subscribe({
      next: (res) => {
        console.log('Response received:', res);
        this.result = res;
        this.loading = false;
        this.cdr.markForCheck();
        console.log('Result set, loading:', this.loading);
      },
      error: (err) => {
        console.log('Error from API:', err);
        alert('Error analyzing logs: ' + err.message);
        this.loading = false;
        this.cdr.markForCheck();
      }
    });
  }

  ngOnInit() {
    // setInterval(() => {
    //   this.logService.getResults().subscribe(res => {
    //     this.results = res;
    //   });
    // }, 3000);
  }
}