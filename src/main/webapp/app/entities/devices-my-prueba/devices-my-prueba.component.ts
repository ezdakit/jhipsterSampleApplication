import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';
import { Principal } from 'app/core';
import { DevicesMyPruebaService } from './devices-my-prueba.service';

@Component({
    selector: 'jhi-devices-my-prueba',
    templateUrl: './devices-my-prueba.component.html'
})
export class DevicesMyPruebaComponent implements OnInit, OnDestroy {
    devices: IDevicesMyPrueba[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private devicesService: DevicesMyPruebaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.devicesService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IDevicesMyPrueba[]>) => (this.devices = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.devicesService.query().subscribe(
            (res: HttpResponse<IDevicesMyPrueba[]>) => {
                this.devices = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDevices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDevicesMyPrueba) {
        return item.id;
    }

    registerChangeInDevices() {
        this.eventSubscriber = this.eventManager.subscribe('devicesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
