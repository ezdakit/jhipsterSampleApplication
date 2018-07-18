import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';
import { DevicesMyPruebaService } from './devices-my-prueba.service';

@Component({
    selector: 'jhi-devices-my-prueba-update',
    templateUrl: './devices-my-prueba-update.component.html'
})
export class DevicesMyPruebaUpdateComponent implements OnInit {
    private _devices: IDevicesMyPrueba;
    isSaving: boolean;

    constructor(private devicesService: DevicesMyPruebaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ devices }) => {
            this.devices = devices;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.devices.id !== undefined) {
            this.subscribeToSaveResponse(this.devicesService.update(this.devices));
        } else {
            this.subscribeToSaveResponse(this.devicesService.create(this.devices));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDevicesMyPrueba>>) {
        result.subscribe((res: HttpResponse<IDevicesMyPrueba>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get devices() {
        return this._devices;
    }

    set devices(devices: IDevicesMyPrueba) {
        this._devices = devices;
    }
}
