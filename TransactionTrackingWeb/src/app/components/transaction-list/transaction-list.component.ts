import { Component, OnDestroy, OnInit } from '@angular/core';
import { TransactionService } from '../../services/transaction.service';
import { TransactionCollectionDTO } from '../../models/TransactionCollectionDTO';
import { TransactionDTO } from '../../models/TransactionDTO';
import { Subscription } from 'rxjs';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent implements OnInit, OnDestroy {

  searchQuery: any;
  transactionCollectionSubscription: Subscription;
  transactionCollection: TransactionCollectionDTO;
  transactionDetailsSubscription: Subscription;
  transactionDetails: TransactionDTO;
  isSpinnerLoading: boolean;
  hasNoSearchResult: boolean;
  searchResultFlag: boolean;
  webSocketClient: any;

  constructor(private transactionService: TransactionService) {
    // subscribe to websocket streaming service once object is created and ready
    this.subscribeToWebSocketTransactionsUpdates();
  }

  ngOnInit(): void {
    // get the list of active transactions
    this.getTransactionCollection();
  }

  subscribeToWebSocketTransactionsUpdates() {
    const topicEndpoint = '/topic/transactions';
    const webSocketTransactionHost = new SockJS(environment.apiHost + '/transactions/websocket');
    webSocketTransactionHost.withCredentials = true;
    this.webSocketClient = Stomp.over(webSocketTransactionHost);
    console.log(this.webSocketClient);
    // initialize connection to web socket server
    this.webSocketClient.connect({}, (frame) => {
      console.log('Connected to websocket server: ' + frame);
      // subscribe to the transaction update topic
      this.webSocketClient.subscribe(topicEndpoint, (event) => {
        // handle incoming transactions from the websocket server
        this.onTransactionReceived(event);
      });
    }, error => {
      this.handleWebSocketError(error);
    });
  }

  handleWebSocketError(error) {
    console.log(error);
    // retry connection after 5s again
    setTimeout(() => {
      this.subscribeToWebSocketTransactionsUpdates();
    }, 5000);
  }

  disconnectWebSocketConnection() {
    if (this.webSocketClient) {
      this.webSocketClient.disconnect();
    }
    console.log('Disconnected from websocket streaming');
  }

  onTransactionReceived(transactionEvent) {
    // get the new transaction, clone previous transaction collection and add it to it and render
    const transactions = [...this.transactionCollection.transactionResource, JSON.parse(transactionEvent.body)];
    this.transactionCollection = {transactionResource: transactions.reverse()};
  }

  getTransactionCollection() {
    this.isSpinnerLoading = true;
    this.transactionCollectionSubscription = this.transactionService.getTransactions().subscribe((transactions) => {
      this.transactionCollection = transactions;
      this.isSpinnerLoading = false;
    }, error => {
      console.log(error.error);
      this.isSpinnerLoading = false;
    });
  }

  getTransactionById(transactionId: string) {
    console.log('Getting details for a transaction with identifier: ' + transactionId);
    this.isSpinnerLoading = true;
    this.transactionDetails = null;
    this.transactionDetailsSubscription = this.transactionService.viewTransactionDetailsById(transactionId).subscribe((response) => {
      if (response) {
        setTimeout(() => {
          this.isSpinnerLoading = false;
          this.transactionDetails = response;
        }, 2000);
      }
    }, error => {
      console.log(error.error);
      this.isSpinnerLoading = false;
    });
  }

  searchTransactionByIdOrValue($event: unknown) {
    console.log(`Searching for transactions which match a given search criteria : ${$event}`);
    this.isSpinnerLoading = true;
    this.transactionCollectionSubscription = this.transactionService.searchTransactionByIdOrValue($event).subscribe((transactions) => {
      this.transactionCollection = transactions;
      this.isSpinnerLoading = false;
      // @ts-ignore
      this.searchQuery = $event.query;
      this.transactionCollection.transactionResource.length === 0 ? this.hasNoSearchResult = true : this.hasNoSearchResult = false;
      this.transactionCollection.transactionResource.length > 0 ? this.searchResultFlag = true : this.searchResultFlag = false;
    }, error => {
      console.log(error.error);
      this.isSpinnerLoading = false;
    });
  }

  ngOnDestroy() {
    // Unsubscribe when the component is destroyed
    this.transactionCollectionSubscription.unsubscribe();
    this.transactionDetailsSubscription.unsubscribe();
  }
}
