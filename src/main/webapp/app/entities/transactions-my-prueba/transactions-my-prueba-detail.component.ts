import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';

@Component({
    selector: 'jhi-transactions-my-prueba-detail',
    templateUrl: './transactions-my-prueba-detail.component.html'
})
export class TransactionsMyPruebaDetailComponent implements OnInit {
    transactions: ITransactionsMyPrueba;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ transactions }) => {
            this.transactions = transactions;
        });
    }

    previousState() {
        window.history.back();
    }
}
