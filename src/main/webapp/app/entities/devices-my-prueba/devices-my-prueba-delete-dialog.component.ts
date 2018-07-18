import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';
import { DevicesMyPruebaService } from './devices-my-prueba.service';

@Component({
    selector: 'jhi-devices-my-prueba-delete-dialog',
    templateUrl: './devices-my-prueba-delete-dialog.component.html'
})
export class DevicesMyPruebaDeleteDialogComponent {
    devices: IDevicesMyPrueba;

    constructor(
        private devicesService: DevicesMyPruebaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.devicesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'devicesListModification',
                content: 'Deleted an devices'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-devices-my-prueba-delete-popup',
    template: ''
})
export class DevicesMyPruebaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ devices }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DevicesMyPruebaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.devices = devices;
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
