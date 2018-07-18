import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';

@Component({
    selector: 'jhi-devices-my-prueba-detail',
    templateUrl: './devices-my-prueba-detail.component.html'
})
export class DevicesMyPruebaDetailComponent implements OnInit {
    devices: IDevicesMyPrueba;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ devices }) => {
            this.devices = devices;
        });
    }

    previousState() {
        window.history.back();
    }
}
