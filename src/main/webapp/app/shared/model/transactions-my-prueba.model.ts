export interface ITransactionsMyPrueba {
    id?: number;
    trxId?: string;
    trxType?: number;
    deviceId?: number;
}

export class TransactionsMyPrueba implements ITransactionsMyPrueba {
    constructor(public id?: number, public trxId?: string, public trxType?: number, public deviceId?: number) {}
}
