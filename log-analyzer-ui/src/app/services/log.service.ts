import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LogAnalysisResult {
  summary: string;
  errors: string[];
  fixes: string[];
}

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private apiUrl = 'http://localhost:8080/logs/analyze';

  constructor(private http: HttpClient) {}

  analyzeLogs(logs: string): Observable<LogAnalysisResult> {
    return this.http.post<LogAnalysisResult>(this.apiUrl, logs, {
      headers: { 'Content-Type': 'text/plain' }
    });
  }

  getResults(): Observable<LogAnalysisResult[]> {
    return this.http.get<LogAnalysisResult[]>('http://localhost:8080/results');
  }
}