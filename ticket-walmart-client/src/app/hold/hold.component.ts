import { Component, OnInit } from '@angular/core';
import { SeatsService } from '../seats.service';
import { ReqHoldSeat } from '../models/request-hold-model';

@Component({
  selector: 'app-hold',
  templateUrl: './hold.component.html',
  styleUrls: ['./hold.component.css']
})
export class HoldComponent implements OnInit {

  seats: Array<any>;
  constructor(private seatsService: SeatsService) { }

  ngOnInit() {
  }
  holdSeats(reqHoldSeat: ReqHoldSeat) {
    this.seatsService.holdSeats(reqHoldSeat).subscribe(data => {
      this.seats = data;
    });
    }
}
