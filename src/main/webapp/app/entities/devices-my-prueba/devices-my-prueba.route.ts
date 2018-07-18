import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';
import { DevicesMyPruebaService } from './devices-my-prueba.service';
import { DevicesMyPruebaComponent } from './devices-my-prueba.component';
import { DevicesMyPruebaDetailComponent } from './devices-my-prueba-detail.component';
import { DevicesMyPruebaUpdateComponent } from './devices-my-prueba-update.component';
import { DevicesMyPruebaDeletePopupComponent } from './devices-my-prueba-delete-dialog.component';
import { IDevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';

@Injectable({ providedIn: 'root' })
export class DevicesMyPruebaResolve implements Resolve<IDevicesMyPrueba> {
    constructor(private service: DevicesMyPruebaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((devices: HttpResponse<DevicesMyPrueba>) => devices.body));
        }
        return of(new DevicesMyPrueba());
    }
}

export const devicesRoute: Routes = [
    {
        path: 'devices-my-prueba',
        component: DevicesMyPruebaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devices-my-prueba/:id/view',
        component: DevicesMyPruebaDetailComponent,
        resolve: {
            devices: DevicesMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devices-my-prueba/new',
        component: DevicesMyPruebaUpdateComponent,
        resolve: {
            devices: DevicesMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devices-my-prueba/:id/edit',
        component: DevicesMyPruebaUpdateComponent,
        resolve: {
            devices: DevicesMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const devicesPopupRoute: Routes = [
    {
        path: 'devices-my-prueba/:id/delete',
        component: DevicesMyPruebaDeletePopupComponent,
        resolve: {
            devices: DevicesMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.devices.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
