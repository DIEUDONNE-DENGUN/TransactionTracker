import { Component, Input, OnInit } from '@angular/core';
import { TransactionDTO } from '../../models/TransactionDTO';
import { TransactionUpdateDTO } from '../../models/TransactionUpdateDTO';
import { TransactionService } from 'src/app/services/transaction.service';

declare var $: any;

@Component({
  selector: 'app-transaction-detail',
  templateUrl: './transaction-detail.component.html',
  styleUrls: ['./transaction-detail.component.scss']
})
export class TransactionDetailComponent implements OnInit {
  @Input() transaction: TransactionDTO;
  isSpinnerLoading: boolean;
  transactionUpdate: TransactionUpdateDTO = {};
  hasError: boolean;
  apiError: boolean;
  apiSuccess: boolean;

  constructor(private transactionService: TransactionService) {
  }

  ngOnInit(): void {
  }

  handleInputChange(event) {
    // tslint:disable-next-line:no-unused-expression
    const input = event.target.name;
    const value = event.target.value;
    if (input === 'sender') {
      this.transactionUpdate.sender = value;
      this.transactionUpdate.value = this.transactionUpdate.value || this.transaction.transactionValue;
      this.transactionUpdate.receiver = this.transactionUpdate.receiver || this.transaction.transactionReceiver;
      this.transactionUpdate.confirmed = this.transactionUpdate.confirmed || this.transaction.transactionStatus;
    } else if (input === 'receiver') {
      this.transactionUpdate.receiver = value;
      this.transactionUpdate.sender = this.transactionUpdate.sender || this.transaction.transactionSender;
      this.transactionUpdate.value = this.transactionUpdate.value || this.transaction.transactionValue;
      this.transactionUpdate.confirmed = this.transactionUpdate.confirmed || this.transaction.transactionStatus;
    } else if (input === 'value') {
      this.transactionUpdate.value = value;
      this.transactionUpdate.sender = this.transactionUpdate.sender || this.transaction.transactionSender;
      this.transactionUpdate.receiver = this.transactionUpdate.receiver || this.transaction.transactionReceiver;
      this.transactionUpdate.confirmed = this.transactionUpdate.confirmed || this.transaction.transactionStatus;
    } else {
      this.transactionUpdate.confirmed = event.target.checked;
      this.transactionUpdate.sender = this.transactionUpdate.sender || this.transaction.transactionSender;
      this.transactionUpdate.receiver = this.transactionUpdate.receiver || this.transaction.transactionReceiver;
      this.transactionUpdate.value = this.transactionUpdate.value || this.transaction.transactionValue;
    }
  }

  showUpdateForm() {
    // show modal to display update transaction form
    $('#transaction-modal').modal('show');
  }

  // update transaction details
  updateTransaction() {
    // validate form
    if (!this.validateTransactionForm()) {
      return;
    }
    this.isSpinnerLoading = true;
    // make api request to update transaction
    this.transactionService.updateTransaction(this.transaction.transactionId, this.transactionUpdate).subscribe((response) => {
      this.isSpinnerLoading = false;
      this.apiSuccess = true;
      setTimeout(() => {
        $('#transaction-modal').hide();
        window.location.reload();
      }, 2000);
    }, error => {
      this.apiError = true;
      console.log(error.error);
      this.isSpinnerLoading = false;
    });
  }

  // validate all the inputs of the update form
  validateTransactionForm() {
    if (!this.transactionUpdate) {
      this.hasError = true;
      return false;
    }
    if (this.transactionUpdate.value !== 0 && this.transactionUpdate.sender !== '' && this.transactionUpdate.receiver !== '') {
      this.hasError = false;
      return true;
    }
    this.hasError = true;
    return false;
  }
}
