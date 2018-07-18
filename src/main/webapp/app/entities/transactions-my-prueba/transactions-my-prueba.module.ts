import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    TransactionsMyPruebaComponent,
    TransactionsMyPruebaDetailComponent,
    TransactionsMyPruebaUpdateComponent,
    TransactionsMyPruebaDeletePopupComponent,
    TransactionsMyPruebaDeleteDialogComponent,
    transactionsRoute,
    transactionsPopupRoute
} from './';

const ENTITY_STATES = [...transactionsRoute, ...transactionsPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TransactionsMyPruebaComponent,
        TransactionsMyPruebaDetailComponent,
        TransactionsMyPruebaUpdateComponent,
        TransactionsMyPruebaDeleteDialogComponent,
        TransactionsMyPruebaDeletePopupComponent
    ],
    entryComponents: [
        TransactionsMyPruebaComponent,
        TransactionsMyPruebaUpdateComponent,
        TransactionsMyPruebaDeleteDialogComponent,
        TransactionsMyPruebaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTransactionsMyPruebaModule {}
