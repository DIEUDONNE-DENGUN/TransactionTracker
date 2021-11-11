import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-search-transaction-form',
  templateUrl: './search-transaction-form.component.html',
  styleUrls: ['./search-transaction-form.component.scss']
})
export class SearchTransactionFormComponent implements OnInit {
  @Output() searchTransaction = new EventEmitter<{}>();
  searchQuery: any;
  searchTransactionId = '';
  searchTransactionValue = 0;
  isSearching: boolean;
  hasError: boolean;

  constructor() {
  }

  ngOnInit(): void {
  }

  handleInputChange(event) {
    const searchValue = event.target.value;
    this.searchQuery = searchValue;
    // check if the  search value is a number or a string
    !isNaN(searchValue) ? this.searchTransactionValue = searchValue : this.searchTransactionId = searchValue;
  }

  handleSearchTransaction() {
    // validate input field
    if (!this.validateSearchTransaction()) {
      this.hasError = true;
      return;
    }
    // send the search query object back to the parent list component
    this.isSearching = true;
    const transaction = {
      id: this.searchTransactionId,
      value: +this.searchTransactionValue,
      query: this.searchQuery
    };
    this.searchTransaction.emit(transaction);
    // @ts-ignore
    setTimeout(() => {
      this.isSearching = false;
    }, 2000);
  }

  validateSearchTransaction() {
    if (this.searchTransactionId === '' && this.searchTransactionValue === 0) {
      return false;
    }
    return true;
  }
}
