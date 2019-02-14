import { Component, OnInit } from '@angular/core';
import { SeatsService } from '../seats.service';
import { HoldSeat } from '../models/hold-seat-model';

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrls: ['./reserve.component.css']
})
export class ReserveComponent implements OnInit {

  seats: any;
  constructor(private seatsService: SeatsService) { }

  ngOnInit() {
  }
  reserveSeats(holdSeat: HoldSeat) {
    this.seatsService.reserveSeats(holdSeat).subscribe(data => {
      this.seats = data.confirmationCode;
    });
    }
  reserveSeatsList(holdSeat: HoldSeat) {
      this.seatsService.reserveSeatsList(holdSeat).subscribe(data => {
        this.seats = data.confirmationCode;
    });
    }
}
