import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TransactionCollectionDTO } from '../models/TransactionCollectionDTO';
import { Observable } from 'rxjs';
import { TransactionDTO } from '../models/TransactionDTO';
import { TransactionUpdateDTO } from '../models/TransactionUpdateDTO';

/*
   @Author:Dieudonne Takougang
   @Date: 10/14/2021
   @Description: handle all API requests interaction with the API backend service
 */
@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  baseUrl = environment.apiBaseUrl;
  apiPath = environment.apiPath;

  constructor(private httpClient: HttpClient) {
  }

  // get a collection of active transactions
  public getTransactions(): Observable<TransactionCollectionDTO> {
    const apiEndpoint = `${this.baseUrl}/${this.apiPath}`;
    return this.httpClient.get<TransactionCollectionDTO>(apiEndpoint);
  }

  // get details for a given transaction by its identifier number
  public viewTransactionDetailsById(transactionId: string): Observable<TransactionDTO> {
    const apiEndpoint = `${this.baseUrl}/${this.apiPath}/${transactionId}`;
    return this.httpClient.get<TransactionDTO>(apiEndpoint);
  }

  // update a given transaction details by its identifier
  public updateTransaction(transactionId: string, transaction: TransactionUpdateDTO): Observable<void> {
    const apiEndpoint = `${this.baseUrl}/${this.apiPath}/${transactionId}`;
    return this.httpClient.put<void>(apiEndpoint, transaction);
  }

  // get details for a given transaction by its identifier number
  public searchTransactionByIdOrValue(transaction: any): Observable<TransactionCollectionDTO> {
    const apiEndpoint = `${this.baseUrl}/${this.apiPath}/searches?transactionId=${transaction.id}&value=${transaction.value}`;
    return this.httpClient.get<TransactionCollectionDTO>(apiEndpoint);
  }
}
