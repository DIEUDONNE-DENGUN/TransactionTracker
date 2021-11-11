export interface TransactionUpdateDTO {
  confirmed?: boolean;
  receiver?: string;
  sender?: string;
  value?: number;
}
