import { Component, OnInit } from '@angular/core';
import { SeatsService } from '../seats.service';

@Component({
  selector: 'app-holded-list',
  templateUrl: './holded-list.component.html',
  styleUrls: ['./holded-list.component.css']
})
export class HoldedListComponent implements OnInit {

  seats: Array<any>;
  constructor(private seatsService: SeatsService) { }

  ngOnInit() {
  }
  getSeats(id: number) {
  this.seatsService.getSeatsFromHold(id).subscribe(data => {
    this.seats = data;
  });
  }
}
