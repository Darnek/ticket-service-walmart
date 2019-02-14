import { Component, OnInit } from '@angular/core';
import { SeatsService } from '../seats.service';

@Component({
  selector: 'app-seats-list',
  templateUrl: './seats-list.component.html',
  styleUrls: ['./seats-list.component.css']
})
export class SeatsListComponent implements OnInit {

  seats: Array<any>;

  constructor(private seatsService: SeatsService) { }

  ngOnInit() {
    this.seatsService.getAll().subscribe(data => {
      this.seats = data;
    });
  }
}
