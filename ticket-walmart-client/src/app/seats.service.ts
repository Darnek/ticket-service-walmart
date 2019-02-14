import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReqHoldSeat } from './models/request-hold-model';
import { HoldSeat } from './models/hold-seat-model';

@Injectable({
  providedIn: 'root'
})
export class SeatsService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get('http://localhost:8080/seats');
  }
  getSeatsFromHold(id: number): Observable<any> {
    return this.http.get(`http://localhost:8080/seats/seatsholded/${id}`);
  }
  holdSeats(reqHoldSeat: ReqHoldSeat): Observable<any> {
    return this.http.post('http://localhost:8080/seats/hold/',reqHoldSeat);
  }
  reserveSeats(holdSeat: HoldSeat): Observable<any> {
    return this.http.post('http://localhost:8080/seats/reserve/',holdSeat);
  }
  reserveSeatsList(holdSeat: HoldSeat): Observable<any> {
    return this.http.post('http://localhost:8080/seats/reservelist/',holdSeat);
  }
}
