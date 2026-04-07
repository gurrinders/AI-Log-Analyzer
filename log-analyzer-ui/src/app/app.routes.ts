import { Routes } from '@angular/router';
import { LogAnalyzer } from './components/log-analyzer/log-analyzer';

export const routes: Routes = [
  { path: '', redirectTo: '/analyze', pathMatch: 'full' },
  { path: 'analyze', component: LogAnalyzer }
];
