/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransactionsMyPruebaDeleteDialogComponent } from 'app/entities/transactions-my-prueba/transactions-my-prueba-delete-dialog.component';
import { TransactionsMyPruebaService } from 'app/entities/transactions-my-prueba/transactions-my-prueba.service';

describe('Component Tests', () => {
    describe('TransactionsMyPrueba Management Delete Component', () => {
        let comp: TransactionsMyPruebaDeleteDialogComponent;
        let fixture: ComponentFixture<TransactionsMyPruebaDeleteDialogComponent>;
        let service: TransactionsMyPruebaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TransactionsMyPruebaDeleteDialogComponent]
            })
                .overrideTemplate(TransactionsMyPruebaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TransactionsMyPruebaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionsMyPruebaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
