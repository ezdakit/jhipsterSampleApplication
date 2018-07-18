import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';
import { TransactionsMyPruebaService } from './transactions-my-prueba.service';
import { TransactionsMyPruebaComponent } from './transactions-my-prueba.component';
import { TransactionsMyPruebaDetailComponent } from './transactions-my-prueba-detail.component';
import { TransactionsMyPruebaUpdateComponent } from './transactions-my-prueba-update.component';
import { TransactionsMyPruebaDeletePopupComponent } from './transactions-my-prueba-delete-dialog.component';
import { ITransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';

@Injectable({ providedIn: 'root' })
export class TransactionsMyPruebaResolve implements Resolve<ITransactionsMyPrueba> {
    constructor(private service: TransactionsMyPruebaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((transactions: HttpResponse<TransactionsMyPrueba>) => transactions.body));
        }
        return of(new TransactionsMyPrueba());
    }
}

export const transactionsRoute: Routes = [
    {
        path: 'transactions-my-prueba',
        component: TransactionsMyPruebaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transactions-my-prueba/:id/view',
        component: TransactionsMyPruebaDetailComponent,
        resolve: {
            transactions: TransactionsMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transactions-my-prueba/new',
        component: TransactionsMyPruebaUpdateComponent,
        resolve: {
            transactions: TransactionsMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transactions-my-prueba/:id/edit',
        component: TransactionsMyPruebaUpdateComponent,
        resolve: {
            transactions: TransactionsMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transactionsPopupRoute: Routes = [
    {
        path: 'transactions-my-prueba/:id/delete',
        component: TransactionsMyPruebaDeletePopupComponent,
        resolve: {
            transactions: TransactionsMyPruebaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
