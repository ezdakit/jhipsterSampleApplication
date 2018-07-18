import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    DevicesMyPruebaComponent,
    DevicesMyPruebaDetailComponent,
    DevicesMyPruebaUpdateComponent,
    DevicesMyPruebaDeletePopupComponent,
    DevicesMyPruebaDeleteDialogComponent,
    devicesRoute,
    devicesPopupRoute
} from './';

const ENTITY_STATES = [...devicesRoute, ...devicesPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DevicesMyPruebaComponent,
        DevicesMyPruebaDetailComponent,
        DevicesMyPruebaUpdateComponent,
        DevicesMyPruebaDeleteDialogComponent,
        DevicesMyPruebaDeletePopupComponent
    ],
    entryComponents: [
        DevicesMyPruebaComponent,
        DevicesMyPruebaUpdateComponent,
        DevicesMyPruebaDeleteDialogComponent,
        DevicesMyPruebaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDevicesMyPruebaModule {}
