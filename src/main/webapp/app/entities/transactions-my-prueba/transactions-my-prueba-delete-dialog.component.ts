import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';
import { TransactionsMyPruebaService } from './transactions-my-prueba.service';

@Component({
    selector: 'jhi-transactions-my-prueba-delete-dialog',
    templateUrl: './transactions-my-prueba-delete-dialog.component.html'
})
export class TransactionsMyPruebaDeleteDialogComponent {
    transactions: ITransactionsMyPrueba;

    constructor(
        private transactionsService: TransactionsMyPruebaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transactionsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'transactionsListModification',
                content: 'Deleted an transactions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transactions-my-prueba-delete-popup',
    template: ''
})
export class TransactionsMyPruebaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ transactions }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TransactionsMyPruebaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.transactions = transactions;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
