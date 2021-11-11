import { Component, Input, OnInit } from '@angular/core';
import { TransactionDTO } from '../../models/TransactionDTO';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {
  @Input() transaction: TransactionDTO;
  constructor() { }

  ngOnInit(): void {
  }

}
