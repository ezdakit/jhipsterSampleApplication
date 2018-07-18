/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransactionsMyPruebaUpdateComponent } from 'app/entities/transactions-my-prueba/transactions-my-prueba-update.component';
import { TransactionsMyPruebaService } from 'app/entities/transactions-my-prueba/transactions-my-prueba.service';
import { TransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';

describe('Component Tests', () => {
    describe('TransactionsMyPrueba Management Update Component', () => {
        let comp: TransactionsMyPruebaUpdateComponent;
        let fixture: ComponentFixture<TransactionsMyPruebaUpdateComponent>;
        let service: TransactionsMyPruebaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TransactionsMyPruebaUpdateComponent]
            })
                .overrideTemplate(TransactionsMyPruebaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TransactionsMyPruebaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionsMyPruebaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TransactionsMyPrueba(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.transactions = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TransactionsMyPrueba();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.transactions = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
