/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DevicesMyPruebaDeleteDialogComponent } from 'app/entities/devices-my-prueba/devices-my-prueba-delete-dialog.component';
import { DevicesMyPruebaService } from 'app/entities/devices-my-prueba/devices-my-prueba.service';

describe('Component Tests', () => {
    describe('DevicesMyPrueba Management Delete Component', () => {
        let comp: DevicesMyPruebaDeleteDialogComponent;
        let fixture: ComponentFixture<DevicesMyPruebaDeleteDialogComponent>;
        let service: DevicesMyPruebaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DevicesMyPruebaDeleteDialogComponent]
            })
                .overrideTemplate(DevicesMyPruebaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DevicesMyPruebaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevicesMyPruebaService);
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
