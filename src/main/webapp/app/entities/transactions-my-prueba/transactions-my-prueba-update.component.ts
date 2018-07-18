import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';
import { TransactionsMyPruebaService } from './transactions-my-prueba.service';
import { IDevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';
import { DevicesMyPruebaService } from 'app/entities/devices-my-prueba';

@Component({
    selector: 'jhi-transactions-my-prueba-update',
    templateUrl: './transactions-my-prueba-update.component.html'
})
export class TransactionsMyPruebaUpdateComponent implements OnInit {
    private _transactions: ITransactionsMyPrueba;
    isSaving: boolean;

    devices: IDevicesMyPrueba[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private transactionsService: TransactionsMyPruebaService,
        private devicesService: DevicesMyPruebaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ transactions }) => {
            this.transactions = transactions;
        });
        this.devicesService.query().subscribe(
            (res: HttpResponse<IDevicesMyPrueba[]>) => {
                this.devices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.transactions.id !== undefined) {
            this.subscribeToSaveResponse(this.transactionsService.update(this.transactions));
        } else {
            this.subscribeToSaveResponse(this.transactionsService.create(this.transactions));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionsMyPrueba>>) {
        result.subscribe(
            (res: HttpResponse<ITransactionsMyPrueba>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDevicesById(index: number, item: IDevicesMyPrueba) {
        return item.id;
    }
    get transactions() {
        return this._transactions;
    }

    set transactions(transactions: ITransactionsMyPrueba) {
        this._transactions = transactions;
    }
}
