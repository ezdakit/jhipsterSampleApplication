import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationDevicesMyPruebaModule } from './devices-my-prueba/devices-my-prueba.module';
import { JhipsterSampleApplicationTransactionsMyPruebaModule } from './transactions-my-prueba/transactions-my-prueba.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationDevicesMyPruebaModule,
        JhipsterSampleApplicationTransactionsMyPruebaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
