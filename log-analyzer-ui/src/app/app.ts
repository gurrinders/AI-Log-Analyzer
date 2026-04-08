import { Component, signal } from '@angular/core';
import { LogAnalyzer } from './components/log-analyzer/log-analyzer';

@Component({
  selector: 'app-root',
  imports: [LogAnalyzer],
  standalone: true,
  template: `<app-log-analyzer></app-log-analyzer>`
 // templateUrl: './app.html',
 // styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('log-analyzer-ui');
}
